package mu.codeoffice.service;

import java.util.Locale;
import java.util.TimeZone;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.AdvancedGlobalSettings;
import mu.codeoffice.entity.settings.Announcement;
import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.entity.settings.InternationalizationSettings;
import mu.codeoffice.repository.settings.AdvancedGlobalSettingsRepository;
import mu.codeoffice.repository.settings.AnnouncementRepository;
import mu.codeoffice.repository.settings.GlobalPermissionSettingsRepository;
import mu.codeoffice.repository.settings.GlobalSettingsRepository;
import mu.codeoffice.repository.settings.InternationalizationSettingsRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSettingsRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SystemSettingsService {
	
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
	private ProjectPermissionSettingsRepository projectPermissionRepository;

	@Transactional
	public void update(EnterpriseAuthentication auth, Announcement announcement) 
			throws InformationException, AuthenticationException {
		Announcement settings = announcementRepository.getEnterpriseAnnouncement(auth.getEnterprise());
		if (!settings.getId().equals(announcement.getId())) {
			throw new EnterpriseAuthenticationException("Access Denied.");
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
		AdvancedGlobalSettings settings = advancedGlobalSettingsRepository.getEnterpriseAdvancedGlobalSettings(auth.getEnterprise());
		if (!settings.getId().equals(advancedGlobalSettings.getId())) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		advancedGlobalSettings.setEnterprise(auth.getEnterprise());
		advancedGlobalSettingsRepository.save(advancedGlobalSettings);
	}

	@Transactional(readOnly = true)
	public AdvancedGlobalSettings getAdvancedGlobalSettings(EnterpriseAuthentication auth) {
		return advancedGlobalSettingsRepository.getEnterpriseAdvancedGlobalSettings(auth.getEnterprise());
	}

	@Transactional
	@CacheEvict(value = "globalSettingsCache", key = "#auth.enterprise.id")
	public void update(EnterpriseAuthentication auth, GlobalSettings globalSettings) 
			throws InformationException, AuthenticationException {
		GlobalSettings settings = globalSettingsRepository.getEnterpriseGlobalSettings(auth.getEnterprise());
		if (!settings.getId().equals(globalSettings.getId())) {
			throw new EnterpriseAuthenticationException("Access Denied.");
		}
		globalSettings.setEnterprise(auth.getEnterprise());
		globalSettingsRepository.save(globalSettings);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "globalSettingsCache", key = "#auth.enterprise.id")
	public GlobalSettings getGlobalSettings(EnterpriseAuthentication auth) {
		return globalSettingsRepository.getEnterpriseGlobalSettings(auth.getEnterprise());
	}

	@Transactional
	public void update(EnterpriseAuthentication auth, InternationalizationSettings internationalizationSettings) 
			throws InformationException, AuthenticationException {
		InternationalizationSettings settings = 
				internationalizationSettingsRepository.getEnterpriseInternationalizationSettings(auth.getEnterprise());
		if (!settings.getId().equals(internationalizationSettings.getId())) {
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
	
}
