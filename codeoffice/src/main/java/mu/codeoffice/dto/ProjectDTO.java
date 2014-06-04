package mu.codeoffice.dto;

import mu.codeoffice.entity.Project;

public class ProjectDTO implements DataTransferObject<Project> {

	@Override
	public Project toObject(DataTransferObject<Project> dto) {
		return null;
	}

	@Override
	public ProjectDTO toDTO(Project object) {
		return null;
	}

}
