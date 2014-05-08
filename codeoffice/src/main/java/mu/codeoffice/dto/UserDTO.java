package mu.codeoffice.dto;

import java.util.Date;

import mu.codeoffice.entity.User;

public class UserDTO {

	private String account;

	private String email;

	private String password;
	
	private boolean gender;
	
	private String confirmPassword;

	private Date create;
	
	private Date login;

	private String profilePath;
	
	public User buildUser() {
		User user = new User();
		user.setAccount(account);
		user.setEmail(email);
		user.setPassword(password);
		user.setGender(gender);
		user.setCreate(new Date());
		user.setProfilePath(gender ? "male.jpg" : "femail.jpg");
		return user;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getLogin() {
		return login;
	}

	public void setLogin(Date login) {
		this.login = login;
	}

	public String getProfilePath() {
		return profilePath;
	}

	public void setProfilePath(String profilePath) {
		this.profilePath = profilePath;
	}
	
}
