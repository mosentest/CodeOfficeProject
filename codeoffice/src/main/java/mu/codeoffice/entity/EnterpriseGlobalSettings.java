package mu.codeoffice.entity;

public class EnterpriseGlobalSettings {
	
	private Long id;
	
	private Enterprise enterprise;
	
	private String title;
	
	private boolean allowPublic = false;
	
	private boolean allowLogo = false;
	
	private int maxAuthenticationAllowed = 3;
	
	private boolean captchaOnSignUp = true;
	
	private String emailFrom = "'${fullname}'/ '${email}'/ '${email.hostname}";
	
	private String introduction;
	
	private String defaultLanguage;
	
	private String defaultTimeZone;
	
	private boolean allowVote;
	
	private boolean allowWatch;
	
	private int maxProjectNameLength;
	
	private String maxProjectCodeLength;
	
	private boolean allowUnassignedCase;
	
	private boolean allowLogoutConfirmation;
	
	private int emailVisibility;
	
	private int commentVisibility; //'projects, groups' or 'project roles only'
	
	private boolean allowContactAdministrator;
	
	private String contactAdministratorMessage;
	
	private boolean allowInlineEdit;
	
	
	
}
