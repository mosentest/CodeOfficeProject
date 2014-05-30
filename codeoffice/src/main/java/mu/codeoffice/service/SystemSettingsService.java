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
import mu.codeoffice.enums.AnnouncementLevel;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.settings.AdvancedGlobalSettingsRepository;
import mu.codeoffice.repository.settings.AnnouncementRepository;
import mu.codeoffice.repository.settings.GlobalPermissionSettingsRepository;
import mu.codeoffice.repository.settings.GlobalSettingsRepository;
import mu.codeoffice.repository.settings.InternationalizationSettingsRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.GlobalPermission;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
	private UserGroupRepository userGroupRepository;
	
	@Resource
	private UserRepository userRepository;

	@Transactional
	@CacheEvict(value = "globalPermissionsCache", key = "#auth.enterprise.id")
	public void reset(EnterpriseAuthentication auth, GlobalPermission globalPermission) 
			throws InformationException, AuthenticationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		settings.getUserGroups().clear();
		settings.getUsers().clear();
		for (UserGroup userGroup : settings.getUserGroups()) {
			for (User user : userGroup.getUsers()) {
				user.setGlobalPermissionValue(user.getGlobalPermissionValue() & (~globalPermission.getAuthority()));
				userRepository.save(user);
			}
		}
		for (User user : settings.getUsers()) {
			user.setGlobalPermissionValue(user.getGlobalPermissionValue() & (~globalPermission.getAuthority()));
			userRepository.save(user);
		}
		globalPermissionRepository.save(settings);
	}
	
	@Transactional
	@CacheEvict(value = "globalPermissionsCache", key = "#auth.enterprise.id")
	public void removeGroup(EnterpriseAuthentication auth, GlobalPermission globalPermission, String userGroupName) 
			throws InformationException, AuthenticationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (settings.getUserGroups().remove(userGroup)) {
			for (User user : userGroup.getUsers()) {
				if (!globalPermissionRepository.isUserInUsers(auth.getEnterprise(), globalPermission, user)) {
					user.setGlobalPermissionValue(user.getGlobalPermissionValue() & (~globalPermission.getAuthority()));
					userRepository.save(user);
				}
			}
		} else {
			throw new InformationException("Invalid User Group");
		}
		globalPermissionRepository.save(settings);
	}
	
	@Transactional
	@CacheEvict(value = "globalPermissionsCache", key = "#auth.enterprise.id")
	public void addGroup(EnterpriseAuthentication auth, GlobalPermission globalPermission, String userGroupName) 
			throws InformationException, AuthenticationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		UserGroup userGroup = userGroupRepository.getUserGroup(auth.getEnterprise(), userGroupName);
		if (userGroup == null) {
			throw new InformationException("Invalid User Group");
		}
		if (settings.getUserGroups().contains(userGroup)) {
			throw new InformationException("User Group already included.");
		}
		for (User user : userGroup.getUsers()) {
			user.setGlobalPermissionValue(user.getGlobalPermissionValue() | globalPermission.getAuthority());
			userRepository.save(user);
		}
		settings.getUserGroups().add(userGroup);
		globalPermissionRepository.save(settings);
	}
	
	@Transactional
	@CacheEvict(value = "globalPermissionsCache", key = "#auth.enterprise.id")
	public void removeUser(EnterpriseAuthentication auth, GlobalPermission globalPermission, Long id) 
			throws InformationException, AuthenticationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		User user = userRepository.findById(auth.getEnterprise(), id);
		if (settings.getUsers().remove(user)) {
			if (!globalPermissionRepository.isUserInGroup(auth.getEnterprise(), globalPermission, user)) {
				user.setGlobalPermissionValue(user.getGlobalPermissionValue() & (~globalPermission.getAuthority()));
				userRepository.save(user);
			}
		} else {
			throw new InformationException("Invalid User");
		}
		globalPermissionRepository.save(settings);
	}
	
	@Transactional
	@CacheEvict(value = "globalPermissionsCache", key = "#auth.enterprise.id")
	public void addUser(EnterpriseAuthentication auth, GlobalPermission globalPermission, Long id) 
			throws InformationException, AuthenticationException {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		User user = userRepository.findById(auth.getEnterprise(), id);
		if (user == null) {
			throw new InformationException("Invalid User");
		}
		if (settings.getUsers().contains(user)) {
			throw new InformationException("User already included.");
		}
		if (globalPermissionRepository.isUserInGroup(auth.getEnterprise(), globalPermission, user)) {
			throw new InformationException("User already included in group.");
		}
		user.setGlobalPermissionValue(user.getGlobalPermissionValue() | globalPermission.getAuthority());
		userRepository.save(user);
		settings.getUsers().add(user);
		globalPermissionRepository.save(settings);
	}
	
	@Transactional(readOnly = true)
	public List<UserGroup> getGlobalAvailableUserGroups(EnterpriseAuthentication auth, GlobalPermission globalPermission) {
		GlobalPermissionSettings settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise(), globalPermission);
		List<UserGroup> userGroups = userGroupRepository.getUserGroups(auth.getEnterprise());
		userGroups.removeAll(settings.getUserGroups());
		return userGroups;
	}
	
	@Transactional(readOnly = true)
	public Page<User> getGlobalAvailableUsers(EnterpriseAuthentication auth, GlobalPermission globalPermission, String searchString) {
		return userRepository.findNonGlobalAuthorizedUsers(auth.getEnterprise(), "%" + searchString + "%", globalPermission.getAuthority(), new PageRequest(0, 20));
	}
	
	@Transactional(readOnly = true)
	@Cacheable(value = "globalPermissionsCache", key = "#auth.enterprise.id")
	public List<GlobalPermissionSettings> getGlobalPermissionSettings(EnterpriseAuthentication auth) {
		List<GlobalPermissionSettings> settings = globalPermissionRepository.getGlobalPermissionSettings(auth.getEnterprise());
		for (GlobalPermissionSettings setting : settings) {
			setting.getUserGroups().size();
			setting.getUsers().size();
		}
		return settings;
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, Announcement announcement) 
			throws InformationException, AuthenticationException {
		Announcement settings = announcementRepository.getEnterpriseAnnouncement(auth.getEnterprise());
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
	public Announcement getAnnouncement(EnterpriseAuthentication auth) {
		return announcementRepository.getEnterpriseAnnouncement(auth.getEnterprise());
	}

	@Transactional
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
	@CacheEvict(value = "internationalizationSettingsCache", key = "#auth.enterprise.id")
	public void update(EnterpriseAuthentication auth, InternationalizationSettings internationalizationSettings) 
			throws InformationException, AuthenticationException {
		InternationalizationSettings settings = 
				internationalizationSettingsRepository.getEnterpriseInternationalizationSettings(auth.getEnterprise());
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
	@Cacheable(value = "internationalizationSettingsCache", key = "#auth.enterprise.id")
	public InternationalizationSettings getInternationalizationSettings(EnterpriseAuthentication auth) {
		InternationalizationSettings internationalizationSettings = 
				internationalizationSettingsRepository.getEnterpriseInternationalizationSettings(auth.getEnterprise());
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
