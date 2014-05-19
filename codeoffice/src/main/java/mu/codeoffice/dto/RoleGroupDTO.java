package mu.codeoffice.dto;

import mu.codeoffice.entity.RoleGroup;

public class RoleGroupDTO implements DataTransferObject<RoleGroup> {

	private Long id;
	
	private Long role;
	
	private Long[] users;
	
	public RoleGroupDTO() {}
	
	@Override
	public RoleGroup toObject(DataTransferObject<RoleGroup> dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DataTransferObject<RoleGroup> toDTO(RoleGroup object) {
		// TODO Auto-generated method stub
		return null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getRole() {
		return role;
	}

	public void setRole(Long role) {
		this.role = role;
	}

	public Long[] getUsers() {
		return users;
	}

	public void setUsers(Long[] users) {
		this.users = users;
	}

}
