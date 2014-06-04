package mu.codeoffice.dto;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import mu.codeoffice.entity.settings.ProjectRole;

public class ProjectRoleDTO implements DataTransferObject<ProjectRole> {

	private Long id;

	@Pattern(regexp = "[a-zA-Z]+(( )?[a-zA-Z])+")
	@Size(max = 20)
	private String name;

	@Size(max = 200)
	private String description;
	
	private Long[] newUsers;
	
	private Long[] removedUsers;
	
	@Override
	public ProjectRole toObject(DataTransferObject<ProjectRole> dto) {
		return null;
	}

	@Override
	public ProjectRoleDTO toDTO(ProjectRole object) {
		id = object.getId();
		name = object.getName();
		description = object.getDescription();
		return this;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long[] getNewUsers() {
		return newUsers;
	}

	public void setNewUsers(Long[] newUsers) {
		this.newUsers = newUsers;
	}

	public Long[] getRemovedUsers() {
		return removedUsers;
	}

	public void setRemovedUsers(Long[] removedUsers) {
		this.removedUsers = removedUsers;
	}

}
