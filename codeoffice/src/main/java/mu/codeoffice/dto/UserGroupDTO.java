package mu.codeoffice.dto;

import java.util.List;

import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;

public class UserGroupDTO implements DataTransferObject<UserGroup> {
	
	private String name;
	
	private String description;

	private Long[] users;
	
	private Long[] removedUser;
	
	private Long[] newUser;
	
	private List<User> members;
	
	@Override
	public UserGroup toObject(DataTransferObject<UserGroup> dto) {
		return null;
	}

	@Override
	public DataTransferObject<UserGroup> toDTO(UserGroup object) {
		name = object.getName();
		description = object.getDescription();
		members = object.getUsers();
		return this;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long[] getUsers() {
		return users;
	}

	public void setUsers(Long[] users) {
		this.users = users;
	}

	public List<User> getMembers() {
		return members;
	}

	public void setMembers(List<User> members) {
		this.members = members;
	}

	public Long[] getRemovedUser() {
		return removedUser;
	}

	public void setRemovedUser(Long[] removedUser) {
		this.removedUser = removedUser;
	}

	public Long[] getNewUser() {
		return newUser;
	}

	public void setNewUser(Long[] newUser) {
		this.newUser = newUser;
	}

}
