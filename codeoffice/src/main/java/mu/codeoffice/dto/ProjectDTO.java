package mu.codeoffice.dto;

import mu.codeoffice.entity.Project;

public class ProjectDTO implements DataTransferObject<Project> {

	@Override
	public Project buildObject(DataTransferObject<Project> dto) {
		return null;
	}

	@Override
	public DataTransferObject<Project> buildDTO(Project object) {
		return null;
	}

}
