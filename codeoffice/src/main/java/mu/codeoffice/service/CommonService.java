package mu.codeoffice.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.Date;

import mu.codeoffice.common.ServiceResponse;
import mu.codeoffice.entity.User;
import mu.codeoffice.mapper.CommonMapper;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("commonService")
public class CommonService {
	
	private static final Logger logger = Logger.getLogger(CommonService.class);

	private CommonMapper commonMapper;
	
	public User login(String account, String password) {
		try {
			User user = new User();
			MessageDigest messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.update(password.getBytes(), 0, password.length());
			password = new BigInteger(1, messageDigest.digest()).toString(16);
			user = commonMapper.login(account, password);
			if (user == null) {
				return null;
			}
			user.setLogin(new Date());
			commonMapper.update(user);
			return user;
		}
		catch (Exception e) {
			logger.debug(e);
			return null;
		}
	}
	
	public ServiceResponse logout(long id) {
		User user = commonMapper.logout(id);
		if (user == null) {
			return ServiceResponse.NOT_EXIST;
		}
		user.setLogin(new Date());
		return commonMapper.update(user) == 1 ? ServiceResponse.SUCCESS : ServiceResponse.ERROR;
	}
	
}
