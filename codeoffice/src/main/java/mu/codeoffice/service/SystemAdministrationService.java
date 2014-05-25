package mu.codeoffice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.Announcement;
import mu.codeoffice.entity.settings.AttachmentSettings;
import mu.codeoffice.entity.settings.GeneralProjectSettings;
import mu.codeoffice.entity.settings.GlobalAdvancedSettings;
import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.entity.settings.InternationalizationSettings;
import mu.codeoffice.entity.settings.TimeTrackingSettings;
import mu.codeoffice.repository.settings.AnnouncementRepository;
import mu.codeoffice.repository.settings.AttachmentSettingsRepository;
import mu.codeoffice.repository.settings.GeneralProjectSettingsRepository;
import mu.codeoffice.repository.settings.GlobalAdvancedSettingsRepository;
import mu.codeoffice.repository.settings.GlobalPermissionSettingsRepository;
import mu.codeoffice.repository.settings.GlobalSettingsRepository;
import mu.codeoffice.repository.settings.InternationalizationSettingsRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSettingsRepository;
import mu.codeoffice.repository.settings.TimeTrackingSettingsRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SystemAdministrationService {
	
	private Map<Enterprise, SettingsHolder> cacheManager = new HashMap<>();
	
	@Resource
	private AnnouncementRepository announcementRepository;
	
	@Resource
	private GlobalAdvancedSettingsRepository globalAdvancedSettingsRepository;
	
	@Resource
	private GlobalPermissionSettingsRepository globalPermissionRepository;
	
	@Resource
	private AttachmentSettingsRepository attachmentSettingsRepository;
	
	@Resource
	private GeneralProjectSettingsRepository generalProjectSettingsRepository;
	
	@Resource
	private GlobalSettingsRepository globalSettingsRepository;
	
	@Resource
	private InternationalizationSettingsRepository internationalizationSettingsRepository;
	
	@Resource
	private ProjectPermissionSettingsRepository projectPermissionRepository;
	
	@Resource
	private TimeTrackingSettingsRepository timeTrackingSettingsRepository;

	@Transactional
	public void update(EnterpriseAuthentication auth, Announcement announcement) {
		announcementRepository.save(announcement);
		cacheSettings(auth.getEnterprise(), "announcement", announcement);
	}
	
	@Transactional(readOnly = true)
	public Announcement getAnnouncement(EnterpriseAuthentication auth) {
		Object settings = getSettings(auth.getEnterprise(), "announcement");
		if (settings != null) {
			return (Announcement) settings;
		}
		Announcement announcement = announcementRepository.getEnterpriseAnnouncement(auth.getEnterprise());
		cacheSettings(auth.getEnterprise(), "announcement", announcement);
		return announcement;
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, AttachmentSettings attachmentSettings) 
			throws InformationException, AuthenticationException {
		attachmentSettingsRepository.save(attachmentSettings);
		cacheSettings(auth.getEnterprise(), "attachmentSettings", attachmentSettings);
	}

	@Transactional(readOnly = true)
	public AttachmentSettings getAttachmentSettings(EnterpriseAuthentication auth) {
		Object settings = getSettings(auth.getEnterprise(), "attachmentSettings");
		if (settings != null) {
			return (AttachmentSettings) settings;
		}
		AttachmentSettings attachmentSettings = attachmentSettingsRepository.getEnterpriseAttachmentSettings(auth.getEnterprise());
		cacheSettings(auth.getEnterprise(), "attachmentSettings", attachmentSettings);
		return attachmentSettings;
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, GeneralProjectSettings generalProjectSettings) 
			throws InformationException, AuthenticationException {
		generalProjectSettingsRepository.save(generalProjectSettings);
		cacheSettings(auth.getEnterprise(), "generalProjectSettings", generalProjectSettings);
	}

	@Transactional(readOnly = true)
	public GeneralProjectSettings getGeneralProjectSettings(EnterpriseAuthentication auth) {
		Object settings = getSettings(auth.getEnterprise(), "generalProjectSettings");
		if (settings != null) {
			return (GeneralProjectSettings) settings;
		}
		GeneralProjectSettings generalProjectSettings = generalProjectSettingsRepository.getEnterpriseGeneralProjectSettings(auth.getEnterprise());
		cacheSettings(auth.getEnterprise(), "generalProjectSettings", generalProjectSettings);
		return generalProjectSettings;
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, GlobalAdvancedSettings globalAdvancedSettings) 
			throws InformationException, AuthenticationException {
		globalAdvancedSettingsRepository.save(globalAdvancedSettings);
		cacheSettings(auth.getEnterprise(), "globalAdvancedSettings", globalAdvancedSettings);
	}

	@Transactional(readOnly = true)
	public GlobalAdvancedSettings getGlobalAdvancedSettings(EnterpriseAuthentication auth) {
		Object settings = getSettings(auth.getEnterprise(), "globalAdvancedSettings");
		if (settings != null) {
			return (GlobalAdvancedSettings) settings;
		}
		GlobalAdvancedSettings globalAdvancedSettings = globalAdvancedSettingsRepository.getEnterpriseGlobalAdvancedSettings(auth.getEnterprise());
		cacheSettings(auth.getEnterprise(), "globalAdvancedSettings", globalAdvancedSettings);
		return globalAdvancedSettings;
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, GlobalSettings globalSettings) 
			throws InformationException, AuthenticationException {
		globalSettingsRepository.save(globalSettings);
		cacheSettings(auth.getEnterprise(), "globalSettings", globalSettings);
	}

	@Transactional(readOnly = true)
	public GlobalSettings getGlobalSettings(EnterpriseAuthentication auth) {
		Object settings = getSettings(auth.getEnterprise(), "globalSettings");
		if (settings != null) {
			return (GlobalSettings) settings;
		}
		GlobalSettings globalSettings = globalSettingsRepository.getEnterpriseGlobalSettings(auth.getEnterprise());
		cacheSettings(auth.getEnterprise(), "globalSettings", globalSettings);
		return globalSettings;
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, InternationalizationSettings internationalizationSettings) 
			throws InformationException, AuthenticationException {
		internationalizationSettingsRepository.save(internationalizationSettings);
		cacheSettings(auth.getEnterprise(), "internationalization", internationalizationSettings);
	}

	@Transactional(readOnly = true)
	public InternationalizationSettings getInternationalizationSettings(EnterpriseAuthentication auth) {
		Object settings = getSettings(auth.getEnterprise(), "internationalization");
		if (settings != null) {
			return (InternationalizationSettings) settings;
		}
		InternationalizationSettings internationalizationSettings = 
				internationalizationSettingsRepository.getEnterpriseInternationalizationSettings(auth.getEnterprise());
		internationalizationSettings.setDefaultLocale(null);
		internationalizationSettings.setDefaultTimeZone(TimeZone.getTimeZone(internationalizationSettings.getDefaultTimeZoneID()));
		LocaleContextHolder.setTimeZone(internationalizationSettings.getDefaultTimeZone());
		LocaleContextHolder.setLocale(internationalizationSettings.getDefaultLocale());
		cacheSettings(auth.getEnterprise(), "internationalization", internationalizationSettings);
		return internationalizationSettings;
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, TimeTrackingSettings timeTrackingSettings) 
			throws InformationException, AuthenticationException {
		timeTrackingSettingsRepository.save(timeTrackingSettings);
		cacheSettings(auth.getEnterprise(), "timeTrackingSettings", timeTrackingSettings);
	}

	@Transactional(readOnly = true)
	public TimeTrackingSettings getTimeTrackingSettings(EnterpriseAuthentication auth) {
		Object settings = getSettings(auth.getEnterprise(), "timeTrackingSettings");
		if (settings != null) {
			return (TimeTrackingSettings) settings;
		}
		TimeTrackingSettings timeTrackingSettings = timeTrackingSettingsRepository.getEnterpriseTimeTrackingSettings(auth.getEnterprise());
		cacheSettings(auth.getEnterprise(), "timeTrackingSettings", timeTrackingSettings);
		return timeTrackingSettings;
	}
	
	public Object getSettings(Enterprise enterprise, String key) {
		if (cacheManager.get(enterprise) == null) {
			return null;
		}
		return cacheManager.get(enterprise).getSettings(key);
	}
	
	private void cacheSettings(Enterprise enterprise, String key, Object settings) {
		if (cacheManager.get(enterprise) == null) {
			cacheManager.put(enterprise, new SettingsHolder());
		}
		cacheManager.get(enterprise).putSettings(key, settings);
	}
	
	public static class SettingsHolder {
		
		private Map<String, Object> settings;
		
		public SettingsHolder() {
			settings = new HashMap<>();
		}
		
		public void putSettings(String key, Object settings) {
			this.settings.put(key, settings);
		}
		
		public Object getSettings(String key) {
			return settings.get(key);
		}
		
	}
	
}
