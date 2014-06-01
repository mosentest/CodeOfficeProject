package mu.codeoffice.dto;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.User;

public class ComponentDTO implements DataTransferObject<Component> {

	private Long id;
	
	private String code;
	
	private String name;
	
	private String description;
	
	private String project;
	
	private User lead;
	
	private User defaultReporter;
	
	private User defaultAssignee;
	
	private String[] componentCode;
	
	public ComponentDTO() {}

	@Override
	public Component toObject(DataTransferObject<Component> dto) {
		return null;
	}

	@Override
	public DataTransferObject<Component> toDTO(Component object) {
		return null;
	}

	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		return false;
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

	public User getLead() {
		return lead;
	}

	public void setLead(User lead) {
		this.lead = lead;
	}

	public User getDefaultReporter() {
		return defaultReporter;
	}

	public void setDefaultReporter(User defaultReporter) {
		this.defaultReporter = defaultReporter;
	}

	public User getDefaultAssignee() {
		return defaultAssignee;
	}

	public void setDefaultAssignee(User defaultAssignee) {
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
