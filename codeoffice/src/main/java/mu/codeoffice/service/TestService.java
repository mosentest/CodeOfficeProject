package mu.codeoffice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.Resource;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.entity.settings.AdvancedGlobalSettings;
import mu.codeoffice.entity.settings.Announcement;
import mu.codeoffice.entity.settings.AttachmentSettings;
import mu.codeoffice.entity.settings.GeneralProjectSettings;
import mu.codeoffice.entity.settings.GlobalPermissionSettings;
import mu.codeoffice.entity.settings.GlobalSettings;
import mu.codeoffice.entity.settings.InternationalizationSettings;
import mu.codeoffice.entity.settings.IssueLinkType;
import mu.codeoffice.entity.settings.IssueType;
import mu.codeoffice.entity.settings.IssueTypeScheme;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectPermissionSettings;
import mu.codeoffice.entity.settings.ProjectRole;
import mu.codeoffice.entity.settings.TimeTrackingSettings;
import mu.codeoffice.repository.ComponentRepository;
import mu.codeoffice.repository.EnterpriseRepository;
import mu.codeoffice.repository.IssueRepository;
import mu.codeoffice.repository.LabelRepository;
import mu.codeoffice.repository.ProjectCategoryRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.repository.UserRepository;
import mu.codeoffice.repository.VersionRepository;
import mu.codeoffice.repository.settings.AdvancedGlobalSettingsRepository;
import mu.codeoffice.repository.settings.AnnouncementRepository;
import mu.codeoffice.repository.settings.AttachmentSettingsRepository;
import mu.codeoffice.repository.settings.GeneralProjectSettingsRepository;
import mu.codeoffice.repository.settings.GlobalPermissionSettingsRepository;
import mu.codeoffice.repository.settings.GlobalSettingsRepository;
import mu.codeoffice.repository.settings.InternationalizationSettingsRepository;
import mu.codeoffice.repository.settings.IssueLinkTypeRepository;
import mu.codeoffice.repository.settings.IssueTypeRepository;
import mu.codeoffice.repository.settings.IssueTypeSchemeRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSchemeRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSettingsRepository;
import mu.codeoffice.repository.settings.ProjectRoleRepository;
import mu.codeoffice.repository.settings.TimeTrackingSettingsRepository;
import mu.codeoffice.repository.settings.UserGroupRepository;
import mu.codeoffice.security.GlobalPermission;
import mu.codeoffice.security.ProjectPermission;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

	@Resource
	private ProjectCategoryRepository projectCategoryRepository;
	
	@Resource
	private ProjectRepository projectRepository;

	@Resource
	private VersionRepository versionRepository;

	@Resource
	private ComponentRepository componentRepository;
	
	@Resource
	private LabelRepository labelRepository;
	
	@Resource
	private UserRepository userRepository;
	
	@Resource
	private IssueRepository caseRepository;
	
	@Resource
	private EnterpriseRepository enterpriseRepository;
	
	@Resource
	private AnnouncementRepository announcementBannerRepository;
	
	@Resource
	private AdvancedGlobalSettingsRepository globalAdvancedSettingsRepository;
	
	@Resource
	private GlobalPermissionSettingsRepository globalPermissionRepository;
	
	@Resource
	private AttachmentSettingsRepository attachmentSettingsRepository;
	
	@Resource
	private GeneralProjectSettingsRepository generalProjectSettingsRepository;
	
	@Resource
	private GlobalSettingsRepository globalSettingsRepository;
	
	@Resource
	private InternationalizationSettingsRepository internationalizationSettingsRepository;

	@Resource
	private ProjectRoleRepository projectRoleRepository;

	@Resource
	private ProjectPermissionSchemeRepository projectPermissionSchemeRepository;

	@Resource
	private ProjectPermissionSettingsRepository projectPermissionSettingsRepository;
	
	@Resource
	private TimeTrackingSettingsRepository timeTrackingSettingsRepository;
	
	@Resource
	private UserGroupRepository userGroupRepository;

	@Resource
	private IssueLinkTypeRepository issueLinkTypeRepository;

	@Resource
	private IssueTypeRepository issueTypeRepository;
	
	@Resource
	private IssueTypeSchemeRepository issueTypeSchemeRepository;
	
	@Resource(name = "applicationProperties")
	private Properties properties;
	
	@Transactional
	public void _1_CreateEnterprise() {
		Enterprise e1 = new Enterprise();
		e1.setCode("ZIM");
		e1.setDescription("Zimu Enterprise");
		e1.setName("Zimu Enterprise");

		Enterprise e2 = new Enterprise();
		e2.setCode("UMI");
		e2.setDescription("Enterprise 2");
		e2.setName("Enterprise 2");

		Enterprise e3 = new Enterprise();
		e3.setCode("MIZ");
		e3.setDescription("Enterprise 3");
		e3.setName("Enterprise 3");

		enterpriseRepository.save(e1);
		enterpriseRepository.save(e2);
		enterpriseRepository.save(e3);
	}

	@Transactional
	public void _2_CreateEnterpriseSettings() {
		List<Enterprise> enterprises = enterpriseRepository.findAll();
		for (Enterprise enterprise : enterprises) {
			Announcement announcementBanner = new Announcement();
			announcementBanner.setEnterprise(enterprise);
			
			AttachmentSettings attachmentSettings = new AttachmentSettings();
			attachmentSettings.setEnterprise(enterprise);
			attachmentSettings.setDefaultSettings(properties);
			
			GeneralProjectSettings generalProjectSettings = new GeneralProjectSettings();
			generalProjectSettings.setEnterprise(enterprise);
			
			AdvancedGlobalSettings globalAdvancedSettings = new AdvancedGlobalSettings();
			globalAdvancedSettings.setEnterprise(enterprise);
			globalAdvancedSettings.setDefaultSettings(properties);
			
			GlobalSettings globalSettings = new GlobalSettings();
			globalSettings.setEnterprise(enterprise);
			globalSettings.setDefaultSettings(properties);
			
			InternationalizationSettings internationalizationSettings = new InternationalizationSettings();
			internationalizationSettings.setEnterprise(enterprise);
			internationalizationSettings.setDefaultSettings(properties);
			
			TimeTrackingSettings timeTrackingSettings = new TimeTrackingSettings();
			timeTrackingSettings.setEnterprise(enterprise);
			timeTrackingSettings.setDefaultSettings(properties);

			announcementBannerRepository.save(announcementBanner);
			attachmentSettingsRepository.save(attachmentSettings);
			generalProjectSettingsRepository.save(generalProjectSettings);
			globalAdvancedSettingsRepository.save(globalAdvancedSettings);
			globalSettingsRepository.save(globalSettings);
			internationalizationSettingsRepository.save(internationalizationSettings);
			timeTrackingSettingsRepository.save(timeTrackingSettings);
			
			for (GlobalPermission permission : GlobalPermission.values()) {
				GlobalPermissionSettings globalPermissionSettings = new GlobalPermissionSettings();
				globalPermissionSettings.setEnterprise(enterprise);
				globalPermissionSettings.setGlobalPermission(permission);
				globalPermissionRepository.save(globalPermissionSettings);
			}
		}
	}
	
	@Transactional
	public void _3_CreateUsers() {
		List<Enterprise> enterprises = enterpriseRepository.findAll();
		int j = 0;
		for (Enterprise enterprise : enterprises) {
			UserGroup group = new UserGroup();
			group.setEnterprise(enterprise);
			group.setName("users");
			group.setDescription("All enterprise users.");
			group.setDefaultGroup(true);
			userGroupRepository.save(group);
			group.setUsers(new ArrayList<>());
			
			User main = new User();
			main.setEnterprise(enterprise);
			main.setAccount("admin" + j);
			main.setAddress("address");
			main.setCreate(new Date());
			main.setEmail("admin" + j + "@admin.com");
			main.setFirstName("Admin");
			main.setLastName("Admin");
			main.setProfilePath("male.jpg");
			main.setLogin(new Date());
			main.setPhone("");
			main.setGlobalPermissionValue(-1);
			main.setPassword("e10adc3949ba59abbe56e057f20f883e");
			userRepository.save(main);
			group.getUsers().add(main);
			
			for (int i = 1; i <= 6; i++, j++) {
				User u = new User();
				u.setEnterprise(enterprise);
				u.setAccount("user" + i);
				u.setAddress("address" + i);
				u.setCreate(new Date());
				u.setEmail(j + "_" + i + "@" + i + ".com");
				u.setFirstName("First" + i);
				u.setLastName("Last" + i);
				u.setProfilePath(i % 2 == 0 ? "male.jpg" : "female.jpg");
				u.setLogin(new Date());
				u.setPhone("");
				u.setGlobalPermissionValue(1);
				u.setPassword("e10adc3949ba59abbe56e057f20f883e");
				userRepository.save(u);
				group.getUsers().add(u);
			}
			userGroupRepository.save(group);
		}
	}
	
	@Transactional
	public void _4_AddMoreUsersToDefaultEnterprise() {
		Enterprise enterprise = enterpriseRepository.getOne(1l);
		UserGroup group = userGroupRepository.getOne(6l);
		for (int i = 30000000; i < 30000100; i++) {
			User u = new User();
			u.setEnterprise(enterprise);
			u.setAccount(Integer.toHexString(i));
			u.setAddress("address" + Integer.toHexString(i));
			u.setCreate(new Date());
			u.setEmail(Integer.toHexString(i) + "@" + i + ".com");
			u.setFirstName("First" + Integer.toHexString(i));
			u.setLastName("Last" + Integer.toHexString(i));
			u.setProfilePath(i % 2 == 0 ? "male.jpg" : "femaile.jpg");
			u.setLogin(new Date());
			u.setPhone("");
			u.setGlobalPermissionValue(1);
			u.setPassword("e10adc3949ba59abbe56e057f20f883e");
			u.setUserGroups(Arrays.asList(group));
			userRepository.save(u);
		}
	}
	
	@Transactional
	public void _5_AddProjectPermissionScheme() {
		Enterprise enterprise = enterpriseRepository.getOne(1l);
		User user = userRepository.getOne(30l);
		
		ProjectPermissionScheme s1 = new ProjectPermissionScheme();
		s1.setCreator(user);
		s1.setEnterprise(enterprise);
		s1.setName("Scheme 1");
		projectPermissionSchemeRepository.save(s1);
		
		ProjectPermissionScheme s2 = new ProjectPermissionScheme();
		s2.setCreator(user);
		s2.setEnterprise(enterprise);
		s2.setName("Scheme 2");
		projectPermissionSchemeRepository.save(s2);
		
		ProjectRole r1 = new ProjectRole();
		r1.setName("Administrator");
		r1.setEnterprise(enterprise);
		r1.setDescription("Description project role 1");
		projectRoleRepository.save(r1);
		
		ProjectRole r2 = new ProjectRole();
		r2.setName("Developer");
		r2.setEnterprise(enterprise);
		r2.setDescription("Description project role 2");
		projectRoleRepository.save(r2);

		for (ProjectPermission permission : ProjectPermission.values()) {
			ProjectPermissionSettings projectPermissionSettings = new ProjectPermissionSettings();
			projectPermissionSettings.setEnterprise(enterprise);
			projectPermissionSettings.setProjectPermissionScheme(s1);
			projectPermissionSettings.setProjectPermission(permission);
			projectPermissionSettingsRepository.save(projectPermissionSettings);
		}

		for (ProjectPermission permission : ProjectPermission.values()) {
			ProjectPermissionSettings projectPermissionSettings = new ProjectPermissionSettings();
			projectPermissionSettings.setEnterprise(enterprise);
			projectPermissionSettings.setProjectPermissionScheme(s2);
			projectPermissionSettings.setProjectPermission(permission);
			projectPermissionSettingsRepository.save(projectPermissionSettings);
		}
	}
	
	@Transactional
	public void _6_CreateIssueTypeAndLinks() {
		Enterprise enterprise = enterpriseRepository.getOne(1l);

		IssueLinkType l1 = new IssueLinkType();
		l1.setEnterprise(enterprise);
		l1.setName("Block");
		l1.setInwardLink("blocks");
		l1.setOutwardLink("blocked by");
		IssueLinkType l2 = new IssueLinkType();
		l2.setEnterprise(enterprise);
		l2.setName("Epic");
		l2.setInwardLink("belongs to Epic");
		l2.setOutwardLink("is the Epic for");
		IssueLinkType l3 = new IssueLinkType();
		l3.setEnterprise(enterprise);
		l3.setName("Cause");
		l3.setInwardLink("causes");
		l3.setOutwardLink("caused by");
		IssueLinkType l4 = new IssueLinkType();
		l4.setEnterprise(enterprise);
		l4.setName("Duplicate");
		l4.setInwardLink("duplicates");
		l4.setOutwardLink("is duplicated by");
		IssueLinkType l5 = new IssueLinkType();
		l5.setEnterprise(enterprise);
		l5.setName("Design");
		l5.setInwardLink("has design on");
		l5.setOutwardLink("is design for");
		IssueLinkType l6 = new IssueLinkType();
		l6.setEnterprise(enterprise);
		l6.setName("Depent");
		l6.setInwardLink("depends on");
		l6.setOutwardLink("is depended of");

		issueLinkTypeRepository.save(l1);
		issueLinkTypeRepository.save(l2);
		issueLinkTypeRepository.save(l3);
		issueLinkTypeRepository.save(l4);
		issueLinkTypeRepository.save(l5);
		issueLinkTypeRepository.save(l6);

		IssueType t1 = new IssueType();
		t1.setEnterprise(enterprise);
		t1.setDescription("description 1");
		t1.setIcon("blank");
		t1.setName("Blank");
		IssueType t2 = new IssueType();
		t2.setEnterprise(enterprise);
		t2.setDescription("description 2");
		t2.setIcon("defect");
		t2.setName("Defect");
		IssueType t3 = new IssueType();
		t3.setEnterprise(enterprise);
		t3.setDescription("description 3");
		t3.setIcon("documentation");
		t3.setName("Documentation");
		IssueType t4 = new IssueType();
		t4.setEnterprise(enterprise);
		t4.setDescription("description 4");
		t4.setIcon("newfeature");
		t4.setName("New Feature");
		IssueType t5 = new IssueType();
		t5.setEnterprise(enterprise);
		t5.setDescription("description 5");
		t5.setIcon("generic");
		t5.setName("Generic");
		IssueType t6 = new IssueType();
		t6.setEnterprise(enterprise);
		t6.setDescription("description 6");
		t6.setIcon("improvement");
		t6.setName("Improvement");
		IssueType t7 = new IssueType();
		t7.setEnterprise(enterprise);
		t7.setDescription("description 7");
		t7.setIcon("bug");
		t7.setName("Bug");
		IssueType t8 = new IssueType();
		t8.setEnterprise(enterprise);
		t8.setDescription("description 8");
		t8.setIcon("epic");
		t8.setName("Epic");
		IssueType t9 = new IssueType();
		t9.setEnterprise(enterprise);
		t9.setDescription("description 9");
		t9.setIcon("subtask");
		t9.setName("Subtask");
		t9.setStandard(true);
		IssueType t10 = new IssueType();
		t10.setEnterprise(enterprise);
		t10.setDescription("description 10");
		t10.setIcon("requirement");
		t10.setName("Requirement");
		t10.setStandard(true);

		issueTypeRepository.save(t1);
		issueTypeRepository.save(t2);
		issueTypeRepository.save(t3);
		issueTypeRepository.save(t4);
		issueTypeRepository.save(t5);
		issueTypeRepository.save(t6);
		issueTypeRepository.save(t7);
		issueTypeRepository.save(t8);
		issueTypeRepository.save(t9);
		issueTypeRepository.save(t10);

		IssueTypeScheme s1 = new IssueTypeScheme();
		s1.setEnterprise(enterprise);
		s1.setName("Default Scheme");
		s1.setIssueTypes(Arrays.asList(t1, t2, t3, t4, t5, t6, t7, t8));
		IssueTypeScheme s2 = new IssueTypeScheme();
		s2.setEnterprise(enterprise);
		s2.setName("Template Scheme");
		s2.setIssueTypes(Arrays.asList(t1, t2, t3, t4, t5, t6));
		
		issueTypeSchemeRepository.save(s1);
		issueTypeSchemeRepository.save(s2);
	}
	
}
