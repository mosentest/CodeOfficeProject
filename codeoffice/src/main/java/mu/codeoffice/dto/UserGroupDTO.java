package mu.codeoffice.dto;

import mu.codeoffice.entity.UserGroup;

public class UserGroupDTO extends GenericDTO implements DataTransferObject<UserGroup> {
	
	private String name;
	
	private String description;
	
	private Long[] removedUsers;
	
	private Long[] newUsers;
	
	@Override
	public UserGroup toObject(DataTransferObject<UserGroup> dto) {
		return null;
	}

	@Override
	public UserGroupDTO toDTO(UserGroup object) {
		name = object.getName();
		description = object.getDescription();
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

	public Long[] getRemovedUsers() {
		return removedUsers;
	}

	public void setRemovedUsers(Long[] removedUsers) {
		this.removedUsers = removedUsers;
	}

	public Long[] getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(Long[] newUsers) {
		this.newUsers = newUsers;
	}

}
