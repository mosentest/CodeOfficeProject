package mu.codeoffice.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.ServiceResponse;
import mu.codeoffice.dto.EnterpriseUserDTO;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Submenu;
import mu.codeoffice.repository.EnterpriseUserRepository;
import mu.codeoffice.repository.SubmenuRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EnterpriseUserService {
	
	private static final Logger logger = Logger.getLogger(EnterpriseUserService.class);

	@Resource
	private EnterpriseUserRepository enterpriseUserRepository;
	
	@Resource
	private SubmenuRepository submenuRepository;
	
	@Transactional
	public EnterpriseUser login(String account, String password) {
		EnterpriseUser user = null;
		try {
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes(), 0, password.length());
			String passwordhash = new BigInteger(1, messageDigest.digest()).toString(16);
			user = enterpriseUserRepository.login(account, account, passwordhash);
			if (user != null) {
				user.setLogin(new Date());
				enterpriseUserRepository.save(user);
			}
		} catch (Exception e) {
			logger.debug(e);
		}
		return user;
	}
	
	@Transactional
	public List<Submenu> getSubmenuSettings(EnterpriseAuthentication auth) {
		return submenuRepository.getSubmenus(auth.getEnterprise(), auth.getEnterpriseUser().getId());
	}

	@Transactional
	public ServiceResponse logout(EnterpriseUser user) {
		user.setLogin(new Date());
		enterpriseUserRepository.save(user);
		return ServiceResponse.SUCCESS;
	}
	
	@Transactional
	public EnterpriseUser register(EnterpriseUserDTO enterpriseUserDTO) {
		return null;
	}
	
}
