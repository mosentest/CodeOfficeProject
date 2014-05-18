package mu.codeoffice.json;

import mu.codeoffice.entity.Component;

public class ComponentJSON implements JSONObject<Component> {

	private Long id;
	
	private String code;
	
	private String name;
	
	private String defaultReporter;
	
	private String defaultAssignee;
	
	private String lead;
	
	private String description;
	
	public ComponentJSON() {}
	
	@Override
	public Component toObject(JSONObject<Component> json) {
		Component component = new Component();
		ComponentJSON jsonObject = (ComponentJSON) json;
		component.setId(jsonObject.id);
		component.setCode(jsonObject.code);
		component.setDescription(jsonObject.description);
		return component;
	}

	@Override
	public ComponentJSON toJSONObject(Component object) {
		return object.toJSONObject();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDefaultReporter() {
		return defaultReporter;
	}

	public void setDefaultReporter(String defaultReporter) {
		this.defaultReporter = defaultReporter;
	}

	public String getDefaultAssignee() {
		return defaultAssignee;
	}

	public void setDefaultAssignee(String defaultAssignee) {
		this.defaultAssignee = defaultAssignee;
	}

	public String getLead() {
		return lead;
	}

	public void setLead(String lead) {
		this.lead = lead;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
