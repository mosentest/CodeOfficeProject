package mu.codeoffice.entity.settings;

import java.io.Serializable;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.enums.CommentVisibility;
import mu.codeoffice.enums.EmailVisibility;

@Entity
@Table(name = "settings_global")
public class GlobalSettings implements SettingsEntity, Serializable {
	
	private static final long serialVersionUID = 8189118879112150940L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "enterprise_id")
	private Enterprise enterprise;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "enable_public_mode")
	private boolean enablePublicMode;

	@Column(name = "max_authentication_allowed")
	private int maxAuthenticationAllowed;

	@Column(name = "captcha_on_signup")
	private boolean captchaOnSignUp;
	
	@Column(name = "email_format")
	private String emailFormat;
	
	@Column(name = "introduction")
	private String introduction;

	@Column(name = "enable_vote")
	private boolean enableVote;

	@Column(name = "enable_watch")
	private boolean enableWatch;

	@Column(name = "max_project_nameLength")
	private int maxProjectNameLength;

	@Column(name = "max_project_key_length")
	private int maxProjectKeyLength;

	@Column(name = "enable_unassigned_issue")
	private boolean enableUnassignedIssue;

	@Column(name = "enable_logout_confirmation")
	private boolean enableLogoutConfirmation;

	@Column(name = "email_visibility")
	@Enumerated(EnumType.ORDINAL)
	private EmailVisibility emailVisibility;

	@Column(name = "comment_visibility")
	@Enumerated(EnumType.ORDINAL)
	private CommentVisibility commentVisibility; //'projects, groups' or 'project roles only'

	@Column(name = "enable_contact_administrator")
	private boolean enableContactAdministrator;

	@Column(name = "contact_administrator_message")
	private String contactAdministratorMessage;

	@Column(name = "enable_inline_edit")
	private boolean enableInlineEdit;
	
	public GlobalSettings() {}

	@Override
	public void setDefaultSettings(Properties properties) {
		enablePublicMode = Boolean.parseBoolean(properties.getProperty("settings.global.default.enablePublicMode"));
		maxAuthenticationAllowed = Integer.parseInt(properties.getProperty("settings.global.default.maxAuthenticationAllowed"));
		captchaOnSignUp = Boolean.parseBoolean(properties.getProperty("settings.global.default.captchaOnSignUp"));
		emailFormat = properties.getProperty("settings.global.default.emailFormat");
		introduction = properties.getProperty("settings.global.default.introduction");
		enableVote = Boolean.parseBoolean(properties.getProperty("settings.global.default.enableVote"));
		enableWatch = Boolean.parseBoolean(properties.getProperty("settings.global.default.enableWatch"));
		maxProjectNameLength = Integer.parseInt(properties.getProperty("settings.global.default.maxProjectNameLength"));
		maxProjectKeyLength = Integer.parseInt(properties.getProperty("settings.global.default.maxProjectKeyLength"));
		enableUnassignedIssue = Boolean.parseBoolean(properties.getProperty("settings.global.default.enableUnassignedIssue"));
		enableLogoutConfirmation = Boolean.parseBoolean(properties.getProperty("settings.global.default.enableLogoutConfirmation"));
		enableContactAdministrator = Boolean.parseBoolean(properties.getProperty("settings.global.default.enableContactAdministrator"));
		contactAdministratorMessage = properties.getProperty("settings.global.default.contactAdministratorMessage");
		enableInlineEdit = Boolean.parseBoolean(properties.getProperty("settings.global.default.enableInlineEdit"));
		emailVisibility = EmailVisibility.valueOf(properties.getProperty("settings.global.default.emailVisibility"));
		commentVisibility = CommentVisibility.valueOf(properties.getProperty("settings.global.default.commentVisibility"));
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Enterprise getEnterprise() {
		return enterprise;
	}

	public void setEnterprise(Enterprise enterprise) {
		this.enterprise = enterprise;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isEnablePublicMode() {
		return enablePublicMode;
	}

	public void setEnablePublicMode(boolean enablePublicMode) {
		this.enablePublicMode = enablePublicMode;
	}

	public int getMaxAuthenticationAllowed() {
		return maxAuthenticationAllowed;
	}

	public void setMaxAuthenticationAllowed(int maxAuthenticationAllowed) {
		this.maxAuthenticationAllowed = maxAuthenticationAllowed;
	}

	public boolean isCaptchaOnSignUp() {
		return captchaOnSignUp;
	}

	public void setCaptchaOnSignUp(boolean captchaOnSignUp) {
		this.captchaOnSignUp = captchaOnSignUp;
	}

	public String getEmailFormat() {
		return emailFormat;
	}

	public void setEmailFormat(String emailFormat) {
		this.emailFormat = emailFormat;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public boolean isEnableVote() {
		return enableVote;
	}

	public void setEnableVote(boolean enableVote) {
		this.enableVote = enableVote;
	}

	public boolean isEnableWatch() {
		return enableWatch;
	}

	public void setEnableWatch(boolean enableWatch) {
		this.enableWatch = enableWatch;
	}

	public int getMaxProjectNameLength() {
		return maxProjectNameLength;
	}

	public void setMaxProjectNameLength(int maxProjectNameLength) {
		this.maxProjectNameLength = maxProjectNameLength;
	}

	public int getMaxProjectKeyLength() {
		return maxProjectKeyLength;
	}

	public void setMaxProjectKeyLength(int maxProjectKeyLength) {
		this.maxProjectKeyLength = maxProjectKeyLength;
	}

	public boolean isEnableUnassignedIssue() {
		return enableUnassignedIssue;
	}

	public void setEnableUnassignedIssue(boolean enableUnassignedIssue) {
		this.enableUnassignedIssue = enableUnassignedIssue;
	}

	public boolean isEnableLogoutConfirmation() {
		return enableLogoutConfirmation;
	}

	public void setEnableLogoutConfirmation(boolean enableLogoutConfirmation) {
		this.enableLogoutConfirmation = enableLogoutConfirmation;
	}

	public EmailVisibility getEmailVisibility() {
		return emailVisibility;
	}

	public void setEmailVisibility(EmailVisibility emailVisibility) {
		this.emailVisibility = emailVisibility;
	}

	public CommentVisibility getCommentVisibility() {
		return commentVisibility;
	}

	public void setCommentVisibility(CommentVisibility commentVisibility) {
		this.commentVisibility = commentVisibility;
	}

	public boolean isEnableContactAdministrator() {
		return enableContactAdministrator;
	}

	public void setEnableContactAdministrator(boolean enableContactAdministrator) {
		this.enableContactAdministrator = enableContactAdministrator;
	}

	public String getContactAdministratorMessage() {
		return contactAdministratorMessage;
	}

	public void setContactAdministratorMessage(String contactAdministratorMessage) {
		this.contactAdministratorMessage = contactAdministratorMessage;
	}

	public boolean isEnableInlineEdit() {
		return enableInlineEdit;
	}

	public void setEnableInlineEdit(boolean enableInlineEdit) {
		this.enableInlineEdit = enableInlineEdit;
	}	
	
}
