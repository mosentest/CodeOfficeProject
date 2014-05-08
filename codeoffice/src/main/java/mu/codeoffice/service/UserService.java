package mu.codeoffice.service;

import javax.annotation.Resource;

import mu.codeoffice.common.ServiceResponse;
import mu.codeoffice.dto.UserDTO;
import mu.codeoffice.entity.User;
import mu.codeoffice.repository.UserRepository;

import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

	@Resource
	private UserRepository userRepository;

	public ServiceResponse register(UserDTO userDTO) {
		if (!userRepository.isEmailAvailable(userDTO.getEmail())) {
			return ServiceResponse.ERR_1;
		}
		if (!userRepository.isAccountAvailable(userDTO.getAccount())) {
			return ServiceResponse.ERR_2;
		}
		User user = userDTO.buildUser();
		userRepository.save(user);
		if (user.getId() == 0l) {
			return ServiceResponse.ERROR;
		} else {
			return ServiceResponse.SUCCESS;
		}
	}
	
}
