package mu.codeoffice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.entity.settings.AdvancedGlobalSettings;
import mu.codeoffice.entity.settings.Announcement;
import mu.codeoffice.entity.settings.GlobalPermissionSettings;
import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.entity.settings.InternationalizationSettings;
import mu.codeoffice.entity.settings.TimeTrackingSettings;
import mu.codeoffice.enums.AnnouncementLevel;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.AdvancedGlobalSettingsRepository;
import mu.codeoffice.repository.settings.AnnouncementRepository;
import mu.codeoffice.repository.settings.GlobalPermissionSettingsRepository;
import mu.codeoffice.repository.settings.GlobalSettingsRepository;
import mu.codeoffice.repository.settings.InternationalizationSettingsRepository;
import mu.codeoffice.repository.settings.TimeTrackingSettingsRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.GlobalPermission;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SystemSettingsService {
	
	private final Map<Long, CacheSettings> cacheManager = new HashMap<>();
	
	@Resource
	private AnnouncementRepository announcementRepository;
	
	@Resource
	private AdvancedGlobalSettingsRepository advancedGlobalSettingsRepository;
	
	@Resource
	private GlobalPermissionSettingsRepository globalPermissionRepository;
	
	@Resource
	private GlobalSettingsRepository globalSettingsRepository;
	
	@Resource
	private InternationalizationSettingsRepository internationalizationSettingsRepository;
	
	@Resource
	private TimeTrackingSettingsRepository timeTrackingSettingsRepository;

	@Resource
	private UserGroupRepository userGroupRepository;
	
	@Resource
	private UserRepository userRepository;

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public void update(EnterpriseAuthentication auth, TimeTrackingSettings timeTrackSettings) throws InformationException {
		if (timeTrackSettings.getWorkDaysPerWeek() < 1 || timeTrackSettings.getWorkDaysPerWeek() > 7) {
			throw new InformationException("Must be between 1 and 7");
		}
		if (timeTrackSettings.getWorkHoursPerDay() < 1 || timeTrackSettings.getWorkHoursPerDay() > 24) {
			throw new InformationException("Must be between 1 and 24");
		}
		TimeTrackingSettings.validateDisplayFormat(timeTrackSettings.getTimeEstimatesDisplayFormat());
		TimeTrackingSettings.validateTimeTrackingUnit(timeTrackSettings.getDefaultTimeTrackingUnit());
		TimeTrackingSettings settings = timeTrackingSettingsRepository.getTimeTrackingSettings(auth.getEnterprise());
		settings.setWorkHoursPerDay(timeTrackSettings.getWorkHoursPerDay());
		settings.setWorkDaysPerWeek(timeTrackSettings.getWorkDaysPerWeek());
		settings.setEnabled(timeTrackSettings.isEnabled());
		settings.setDefaultTimeTrackingUnit(timeTrackSettings.getDefaultTimeTrackingUnit());
		settings.setTimeEstimatesDisplayFormat(timeTrackSettings.getTimeEstimatesDisplayFormat());
		timeTrackingSettingsRepository.save(settings);
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')")
	public void reset(EnterpriseAuthentication auth, GlobalPermission globalPermission) throws InformationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		settings.getUserGroups().clear();
		settings.getUsers().clear();
		globalPermissionRepository.save(settings);
		userRepository.clearGlobalPermission(auth.getEnterprise(), ~globalPermission.getAuthority());
	}
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')")
	public void removeGroup(EnterpriseAuthentication auth, GlobalPermission globalPermission, String userGroupName) throws InformationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (settings.getUserGroups().remove(userGroup)) {
			userRepository.clearGlobalPermission(auth.getEnterprise(), ~globalPermission.getAuthority(), userGroup);
			userRepository.grantGlobalPermission(auth.getEnterprise(), globalPermission.getAuthority(), settings);
		} else {
			throw new InformationException("Invalid User Group");
		}
		globalPermissionRepository.save(settings);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')")
	public void addGroup(EnterpriseAuthentication auth, GlobalPermission globalPermission, String userGroupName) throws InformationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (userGroup == null) {
			throw new InformationException("Invalid User Group");
		}
		if (settings.getUserGroups().contains(userGroup)) {
			throw new InformationException("User Group already included.");
		}
		settings.getUserGroups().add(userGroup);
		userRepository.grantGlobalPermission(auth.getEnterprise(), globalPermission.getAuthority(), userGroup);
		globalPermissionRepository.save(settings);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')")
	public void removeUser(EnterpriseAuthentication auth, GlobalPermission globalPermission, Long userId) throws InformationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		User user = userRepository.getUser(auth.getEnterprise(), userId);
		if (settings.getUsers().remove(user)) {
			if (!globalPermissionRepository.isUserInGroup(auth.getEnterprise(), globalPermission, userId)) {
				user.setGlobalPermissionValue(user.getGlobalPermissionValue() & (~globalPermission.getAuthority()));
				userRepository.save(user);
			}
		} else {
			throw new InformationException("Invalid User");
		}
		globalPermissionRepository.save(settings);
	}
	
	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_SYSTEM_ADMIN')")
	public void addUser(EnterpriseAuthentication auth, GlobalPermission globalPermission, Long userId) throws InformationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		if (globalPermissionRepository.isUserInUsers(auth.getEnterprise(), globalPermission, userId)) {
			throw new InformationException("User already included.");
		}
		User user = userRepository.getUser(auth.getEnterprise(), userId);
		if (user == null) {
			throw new InformationException("Invalid User");
		}
		user.setGlobalPermissionValue(user.getGlobalPermissionValue() | globalPermission.getAuthority());
		userRepository.save(user);
		settings.getUsers().add(user);
		globalPermissionRepository.save(settings);
	}
	
	@Transactional(readOnly = true)
	public List<GlobalPermissionSettings> getGlobalPermissionSettings(EnterpriseAuthentication auth) {
		List<GlobalPermissionSettings> settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise());
		for (GlobalPermissionSettings setting : settings) {
			setting.getUserGroups().size();
			setting.getUsers().size();
		}
		return settings;
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public void update(EnterpriseAuthentication auth, Announcement announcement) 
			throws InformationException, AuthenticationException {
		Announcement settings = announcementRepository.getAnnouncement(auth.getEnterprise());
		if (!settings.getId().equals(announcement.getId())) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		if (announcement.getAnnouncementLevel() == null) {
			announcement.setAnnouncementLevel(AnnouncementLevel.INFO);
		}
		announcement.setEnterprise(auth.getEnterprise());
		announcementRepository.save(announcement);
	}
	
	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public Announcement getAnnouncement(EnterpriseAuthentication auth) {
		return announcementRepository.getAnnouncement(auth.getEnterprise());
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public void update(EnterpriseAuthentication auth, AdvancedGlobalSettings advancedGlobalSettings) 
			throws InformationException, AuthenticationException {
		AdvancedGlobalSettings settings = null;
		CacheSettings cacheSettings = cacheManager.get(auth.getEnterprise().getId());
		if (cacheSettings != null && cacheSettings.getAdvancedGlobalSettings() != null) {
			settings = cacheSettings.getAdvancedGlobalSettings();
		} else {
			settings = advancedGlobalSettingsRepository.getEnterpriseAdvancedGlobalSettings(auth.getEnterprise());
		}
		
		if (settings == null || !settings.getId().equals(advancedGlobalSettings.getId())) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		advancedGlobalSettings.setEnterprise(auth.getEnterprise());
		advancedGlobalSettingsRepository.save(advancedGlobalSettings);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public AdvancedGlobalSettings getAdvancedGlobalSettings(EnterpriseAuthentication auth) {
		CacheSettings cacheSettings = cacheManager.get(auth.getEnterprise().getId());
		if (cacheSettings != null && cacheSettings.getAdvancedGlobalSettings() != null) {
			return cacheSettings.getAdvancedGlobalSettings();
		}
		AdvancedGlobalSettings advancedGlobalSettings = advancedGlobalSettingsRepository.getEnterpriseAdvancedGlobalSettings(auth.getEnterprise());
		if (cacheSettings == null) {
			cacheManager.put(auth.getEnterprise().getId(), new CacheSettings());
		}
		cacheManager.get(auth.getEnterprise().getId()).setAdvancedGlobalSettings(advancedGlobalSettings);
		return advancedGlobalSettings;
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public void update(EnterpriseAuthentication auth, GlobalSettings globalSettings) 
			throws InformationException, AuthenticationException {
		GlobalSettings settings = null;
		CacheSettings cacheSettings = cacheManager.get(auth.getEnterprise().getId());
		if (cacheSettings != null && cacheSettings.getGlobalSettings() != null) {
			settings = cacheSettings.getGlobalSettings();
		} else {
			settings = globalSettingsRepository.getEnterpriseGlobalSettings(auth.getEnterprise());
		}
		if (settings == null || !settings.getId().equals(globalSettings.getId())) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		globalSettings.setEnterprise(auth.getEnterprise());
		globalSettingsRepository.save(globalSettings);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public GlobalSettings getGlobalSettings(EnterpriseAuthentication auth) {
		CacheSettings cacheSettings = cacheManager.get(auth.getEnterprise().getId());
		if (cacheSettings != null && cacheSettings.getGlobalSettings() != null) {
			return cacheSettings.getGlobalSettings();
		}
		GlobalSettings globalSettings = globalSettingsRepository.getEnterpriseGlobalSettings(auth.getEnterprise());
		if (cacheSettings == null) {
			cacheManager.put(auth.getEnterprise().getId(), new CacheSettings());
		}
		cacheManager.get(auth.getEnterprise().getId()).setGlobalSettings(globalSettings);
		return globalSettings;
	}

	@Transactional
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public void update(EnterpriseAuthentication auth, InternationalizationSettings internationalizationSettings) 
			throws InformationException, AuthenticationException {
		InternationalizationSettings settings = 
				internationalizationSettingsRepository.getInternationalizationSettings(auth.getEnterprise());
		if (settings == null || !settings.getId().equals(internationalizationSettings.getId())) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		if (!InternationalizationSettings.isSupportedLocale(internationalizationSettings.getDefaultLocaleString())) {
			throw new InformationException("Locale '" + internationalizationSettings.getDefaultLocaleString() + "' is not supported.");
		}
		if (!InternationalizationSettings.isSupportedTimeZone(internationalizationSettings.getDefaultTimeZoneID())) {
			throw new InformationException("Time Zone '" + internationalizationSettings.getDefaultTimeZoneID() + "' is not supported.");
		}
		internationalizationSettings.setEnterprise(auth.getEnterprise());
		internationalizationSettingsRepository.save(internationalizationSettings);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public InternationalizationSettings getInternationalizationSettings(EnterpriseAuthentication auth) {
		InternationalizationSettings internationalizationSettings = 
				internationalizationSettingsRepository.getInternationalizationSettings(auth.getEnterprise());
		String[] localeString = internationalizationSettings.getDefaultLocaleString().split("_");
		for (Locale locale : InternationalizationSettings.SUPPORTED_LOCALE) {
			if (locale.getCountry().equals(localeString[1]) && locale.getLanguage().equals(localeString[0])) {
				internationalizationSettings.setDefaultLocale(locale);
				break;
			}
		}
		internationalizationSettings.setDefaultTimeZone(TimeZone.getTimeZone(internationalizationSettings.getDefaultTimeZoneID()));
		LocaleContextHolder.setTimeZone(internationalizationSettings.getDefaultTimeZone());
		LocaleContextHolder.setLocale(internationalizationSettings.getDefaultLocale());
		return internationalizationSettings;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasRole('ROLE_GLOBAL_ADMIN')")
	public TimeTrackingSettings getTimeTrackingSettings(EnterpriseAuthentication auth) {
		return timeTrackingSettingsRepository.getTimeTrackingSettings(auth.getEnterprise());
	}
	
	private class CacheSettings {
		
		private GlobalSettings globalSettings;
		
		private AdvancedGlobalSettings advancedGlobalSettings;
		
		public CacheSettings() {}

		public GlobalSettings getGlobalSettings() {
			return globalSettings;
		}

		public void setGlobalSettings(GlobalSettings globalSettings) {
			this.globalSettings = globalSettings;
		}

		public AdvancedGlobalSettings getAdvancedGlobalSettings() {
			return advancedGlobalSettings;
		}

		public void setAdvancedGlobalSettings(AdvancedGlobalSettings advancedGlobalSettings) {
			this.advancedGlobalSettings = advancedGlobalSettings;
		}
		
	}
	
}
