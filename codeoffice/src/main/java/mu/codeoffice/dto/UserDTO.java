package mu.codeoffice.dto;

import mu.codeoffice.entity.User;

public class UserDTO implements DataTransferObject<User> {

	@Override
	public User toObject(DataTransferObject<User> dto) {
		return null;
	}

	@Override
	public UserDTO toDTO(User object) {
		return null;
	}

}
