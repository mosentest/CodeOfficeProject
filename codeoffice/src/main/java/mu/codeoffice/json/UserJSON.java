package mu.codeoffice.json;

import mu.codeoffice.entity.EnterpriseUser;

public class UserJSON implements JSONObject<EnterpriseUser> {

	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	@Override
	public EnterpriseUser toObject(JSONObject<EnterpriseUser> json) {
		EnterpriseUser user = new EnterpriseUser();
		user.setId(id);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setEmail(email);
		return user;
	}

	@Override
	public UserJSON toJSONObject(EnterpriseUser object) {
		return object.toJSONObject();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
