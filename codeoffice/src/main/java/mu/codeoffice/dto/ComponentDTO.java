package mu.codeoffice.dto;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.EnterpriseUser;

public class ComponentDTO implements DataTransferObject<Component> {

	private Long id;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private String project;
	
	private EnterpriseUser lead;
	
	private EnterpriseUser defaultReporter;
	
	private EnterpriseUser defaultAssignee;
	
	private String[] componentCode;
	
	public ComponentDTO() {}

	@Override
	public Component buildObject(DataTransferObject<Component> dto) {
		return null;
	}

	@Override
	public DataTransferObject<Component> buildDTO(Component object) {
		return null;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public EnterpriseUser getLead() {
		return lead;
	}

	public void setLead(EnterpriseUser lead) {
		this.lead = lead;
	}

	public EnterpriseUser getDefaultReporter() {
		return defaultReporter;
	}

	public void setDefaultReporter(EnterpriseUser defaultReporter) {
		this.defaultReporter = defaultReporter;
	}

	public EnterpriseUser getDefaultAssignee() {
		return defaultAssignee;
	}

	public void setDefaultAssignee(EnterpriseUser defaultAssignee) {
		this.defaultAssignee = defaultAssignee;
	}

	public String[] getComponentCode() {
		return componentCode;
	}

	public void setComponentCode(String[] componentCode) {
		this.componentCode = componentCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

}
