package mu.codeoffice.mapper;

import mu.codeoffice.entity.User;

public interface CommonMapper {

	public User login(String account, String password);
	
	public User logout(long id);
	
	public int update(User user);
	
}
