package mu.codeoffice.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.Notification;
import mu.codeoffice.entity.settings.NotificationScheme;
import mu.codeoffice.repository.settings.NotificationRepository;
import mu.codeoffice.repository.settings.NotificationSchemeRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.utility.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationSchemeService {

	@Resource
	private NotificationRepository notificationRepository;
	
	@Resource
	private NotificationSchemeRepository notificationSchemeRepository;

	@Transactional(readOnly = true)
	public List<NotificationScheme> getNotificationSchemes(EnterpriseAuthentication auth, boolean initialize) {
		List<NotificationScheme> schemes = notificationSchemeRepository.getNotificationSchemes(auth.getEnterprise());
		if (initialize) {
			for (NotificationScheme scheme : schemes) {
				scheme.getProjects().size();
			}
		}
		return schemes;
	}
	
	@Transactional(readOnly = true)
	public NotificationScheme getNotificationScheme(EnterpriseAuthentication auth, String name, boolean initialize) throws InformationException {
		NotificationScheme scheme = notificationSchemeRepository.getNotificationScheme(auth.getEnterprise(), name);
		if (scheme == null) {
			throw new InformationException("Notification Scheme doesn't exist.");
		}
		if (initialize) {
			for (Notification notification : scheme.getNotifications()) {
				notification.getProjectRoles().size();
				notification.getUserGroups().size();
				notification.getUsers().size();
				if (!StringUtil.isEmptyString(notification.getEmailString())) {
					notification.setEmails(new ArrayList<>());
					String[] emails = notification.getEmailString().split(",");
					for (String email : emails) {
						notification.getEmails().add(email);
					}
				}
			}
		}
		return scheme;
	}
	
}
