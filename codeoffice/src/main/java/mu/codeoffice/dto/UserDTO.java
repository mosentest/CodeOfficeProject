package mu.codeoffice.dto;

import mu.codeoffice.entity.User;

public class UserDTO implements DataTransferObject<User> {

	@Override
	public User toObject(DataTransferObject<User> dto) {
		return null;
	}

	@Override
	public DataTransferObject<User> toDTO(User object) {
		return null;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
	}

}
