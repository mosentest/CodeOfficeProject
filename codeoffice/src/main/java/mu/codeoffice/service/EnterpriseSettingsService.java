package mu.codeoffice.service;

import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.settings.AnnouncementBanner;
import mu.codeoffice.entity.settings.AttachmentSettings;
import mu.codeoffice.entity.settings.GeneralProjectSettings;
import mu.codeoffice.entity.settings.GlobalAdvancedSettings;
import mu.codeoffice.entity.settings.GlobalPermission;
import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.entity.settings.InternationalizationSettings;
import mu.codeoffice.entity.settings.ProjectPermission;
import mu.codeoffice.entity.settings.TimeTrackingSettings;
import mu.codeoffice.repository.settings.AnnouncementBannerRepository;
import mu.codeoffice.repository.settings.AttachmentSettingsRepository;
import mu.codeoffice.repository.settings.GeneralProjectSettingsRepository;
import mu.codeoffice.repository.settings.GlobalAdvancedSettingsRepository;
import mu.codeoffice.repository.settings.GlobalPermissionRepository;
import mu.codeoffice.repository.settings.GlobalSettingsRepository;
import mu.codeoffice.repository.settings.InternationalizationSettingsRepository;
import mu.codeoffice.repository.settings.ProjectPermissionRepository;
import mu.codeoffice.repository.settings.TimeTrackingSettingsRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class EnterpriseSettingsService {
	
	private Map<Enterprise, SettingsHolder> cacheManager = new HashMap<>();
	
	@Autowired
	private ServletContext servletContext;
	
	@Resource
	private AnnouncementBannerRepository announcementBannerRepository;
	
	@Resource
	private GlobalAdvancedSettingsRepository globalAdvancedSettingsRepository;
	
	@Resource
	private GlobalPermissionRepository globalPermissionRepository;
	
	@Resource
	private AttachmentSettingsRepository attachmentSettingsRepository;
	
	@Resource
	private GeneralProjectSettingsRepository generalProjectSettingsRepository;
	
	@Resource
	private GlobalSettingsRepository globalSettingsRepository;
	
	@Resource
	private InternationalizationSettingsRepository internationalizationSettingsRepository;
	
	@Resource
	private ProjectPermissionRepository projectPermissionRepository;
	
	@Resource
	private TimeTrackingSettingsRepository timeTrackingSettingsRepository;
	
	public void update(EnterpriseAuthentication auth, AnnouncementBanner announcementBanner) 
			throws InformationException, AuthenticationException {
		announcementBannerRepository.save(announcementBanner);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_ACCOUNCEMENT" , announcementBanner);
	}
	
	public void save(EnterpriseAuthentication auth, AnnouncementBanner announcementBanner) 
			throws InformationException, AuthenticationException {
		announcementBannerRepository.save(announcementBanner);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_ACCOUNCEMENT" , announcementBanner);
	}
	
	public AnnouncementBanner getAnnouncementBanner(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
	}

	public void update(EnterpriseAuthentication auth, AttachmentSettings attachmentSettings) 
			throws InformationException, AuthenticationException {
		attachmentSettingsRepository.save(attachmentSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_ATTACHMENT", attachmentSettings);
	}
	
	public void save(EnterpriseAuthentication auth, AttachmentSettings attachmentSettings) 
			throws InformationException, AuthenticationException {
		attachmentSettingsRepository.save(attachmentSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_ATTACHMENT", attachmentSettings);
	}
	
	public AttachmentSettings getAttachmentSettings(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
	}

	public void update(EnterpriseAuthentication auth, GeneralProjectSettings generalProjectSettings) 
			throws InformationException, AuthenticationException {
		generalProjectSettingsRepository.save(generalProjectSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GENERAL_PROJECT", generalProjectSettings);
	}
	
	public void save(EnterpriseAuthentication auth, GeneralProjectSettings generalProjectSettings) 
			throws InformationException, AuthenticationException {
		generalProjectSettingsRepository.save(generalProjectSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GENERAL_PROJECT", generalProjectSettings);
	}
	
	public GeneralProjectSettings getGeneralProjectSettings(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
	}

	public void update(EnterpriseAuthentication auth, GlobalAdvancedSettings globalAdvancedSettings) 
			throws InformationException, AuthenticationException {
		globalAdvancedSettingsRepository.save(globalAdvancedSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GLOBAL_ADVANCED", globalAdvancedSettings);
	}
	
	public void save(EnterpriseAuthentication auth, GlobalAdvancedSettings globalAdvancedSettings) 
			throws InformationException, AuthenticationException {
		globalAdvancedSettingsRepository.save(globalAdvancedSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GLOBAL_ADVANCED", globalAdvancedSettings);
	}
	
	public GlobalAdvancedSettings getGlobalAdvancedSettings(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
	}

	public void update(EnterpriseAuthentication auth, GlobalPermission globalPermission) 
			throws InformationException, AuthenticationException {
		globalPermissionRepository.save(globalPermission);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GLOBAL_PERMISSION", globalPermission);
	}
	
	public void save(EnterpriseAuthentication auth, GlobalPermission globalPermission) 
			throws InformationException, AuthenticationException {
		globalPermissionRepository.save(globalPermission);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GLOBAL_PERMISSION", globalPermission);
	}
	
	public GlobalPermission getGlobalPermission(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
	}

	public void update(EnterpriseAuthentication auth, GlobalSettings globalSettings) 
			throws InformationException, AuthenticationException {
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GLOBAL", globalSettings);
		globalSettingsRepository.save(globalSettings);
	}
	
	public void save(EnterpriseAuthentication auth, GlobalSettings globalSettings) 
			throws InformationException, AuthenticationException {
		globalSettingsRepository.save(globalSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_GLOBAL", globalSettings);
	}
	
	public GlobalSettings getGlobalSettings(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
	}

	public void update(EnterpriseAuthentication auth, InternationalizationSettings internationalizationSettings) 
			throws InformationException, AuthenticationException {
		internationalizationSettingsRepository.save(internationalizationSettings);
		cacheSettings(auth.getEnterprise(), "internationalization", internationalizationSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_INTERNATIONALIZATION", internationalizationSettings);
	}
	
	public void save(EnterpriseAuthentication auth, InternationalizationSettings internationalizationSettings) 
			throws InformationException, AuthenticationException {
		internationalizationSettingsRepository.save(internationalizationSettings);
		cacheSettings(auth.getEnterprise(), "internationalization", internationalizationSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_INTERNATIONALIZATION", internationalizationSettings);
	}
	
	public InternationalizationSettings getInternationalizationSettings(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		Object settings = getSettings(auth.getEnterprise(), "internationalization");
		if (settings != null) {
			return (InternationalizationSettings) settings;
		}
		InternationalizationSettings internationalizationSettings = internationalizationSettingsRepository.getOne(null);
		internationalizationSettings.setDefaultLocale(null);
		internationalizationSettings.setDefaultTimeZone(TimeZone.getTimeZone(internationalizationSettings.getDefaultTimeZoneID()));
		LocaleContextHolder.setTimeZone(internationalizationSettings.getDefaultTimeZone());
		LocaleContextHolder.setLocale(internationalizationSettings.getDefaultLocale());
		cacheSettings(auth.getEnterprise(), "internationalization", internationalizationSettings);
		return internationalizationSettings;
	}
	
	public void update(EnterpriseAuthentication auth, ProjectPermission projectPermission) 
			throws InformationException, AuthenticationException {
		projectPermissionRepository.save(projectPermission);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_PROJECT_PERMISSION", projectPermission);
	}
	
	public void save(EnterpriseAuthentication auth, ProjectPermission projectPermission) 
			throws InformationException, AuthenticationException {
		projectPermissionRepository.save(projectPermission);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_PROJECT_PERMISSION", projectPermission);
	}
	
	public ProjectPermission getProjectPermission(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
	}
	
	public void update(EnterpriseAuthentication auth, TimeTrackingSettings timeTrackingSettings) 
			throws InformationException, AuthenticationException {
		timeTrackingSettingsRepository.save(timeTrackingSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_TIME_TRACKING", timeTrackingSettings);
	}
	
	public void save(EnterpriseAuthentication auth, TimeTrackingSettings timeTrackingSettings) 
			throws InformationException, AuthenticationException {
		timeTrackingSettingsRepository.save(timeTrackingSettings);
		servletContext.setAttribute("ENT_SETTINGS_" + auth.getEnterprise().getId() + "_TIME_TRACKING", timeTrackingSettings);
	}
	
	public TimeTrackingSettings getTimeTrackingSettings(EnterpriseAuthentication auth) 
			throws InformationException, AuthenticationException {
		return null;
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
