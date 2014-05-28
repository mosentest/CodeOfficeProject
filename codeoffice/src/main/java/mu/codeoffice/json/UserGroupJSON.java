package mu.codeoffice.json;

import mu.codeoffice.entity.UserGroup;

public class UserGroupJSON implements JSONObject<UserGroup> {

	private Long id;
	
	private String name;
	
	@Override
	public UserGroup toObject() {
		UserGroup userGroup = new UserGroup();
		userGroup.setId(id);
		userGroup.setName(name);
		return userGroup;
	}

	@Override
	public UserGroupJSON toJSONObject(UserGroup object) {
		return object.toJSONObject();
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

}
