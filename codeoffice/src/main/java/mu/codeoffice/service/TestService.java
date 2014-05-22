package mu.codeoffice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Issue;
import mu.codeoffice.entity.Label;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectCategory;
import mu.codeoffice.entity.ProjectRole;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.entity.Version;
import mu.codeoffice.enums.CaseResolution;
import mu.codeoffice.enums.IssuePriority;
import mu.codeoffice.enums.IssueStatus;
import mu.codeoffice.enums.IssueType;
import mu.codeoffice.repository.ComponentRepository;
import mu.codeoffice.repository.EnterpriseUserRepository;
import mu.codeoffice.repository.IssueRepository;
import mu.codeoffice.repository.LabelRepository;
import mu.codeoffice.repository.ProjectCategoryRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.repository.RoleGroupRepository;
import mu.codeoffice.repository.RoleRepository;
import mu.codeoffice.repository.VersionRepository;
import mu.codeoffice.utility.DateUtil;

import org.springframework.stereotype.Service;

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
	private RoleRepository roleRepository;
	
	@Resource
	private RoleGroupRepository roleGroupRepository;
	
	@Resource
	private LabelRepository labelRepository;
	
	@Resource
	private EnterpriseUserRepository enterpriseUserRepository;
	
	@Resource
	private IssueRepository caseRepository;

	@Transactional
	public void test() {
		List<Component> components = componentRepository.findAll();
		for (Component component : components) {
			component.getIssues().size();
			component.setTotalIssues(component.getIssues().size());
			componentRepository.save(component);
		}
	}
	
	@Transactional
	public void createLabels() {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);
		Random random = new Random();
		
		List<Project> projects = projectRepository.findAll();
		for (Project project : projects) {
			project.setLabels(new ArrayList<>());
			Label l1 = new Label();
			l1.setEnterprise(enterprise);
			l1.setLabel("Java");
			l1.setProject(project);
			l1.setIssues(new ArrayList<>());
			Label l2 = new Label();
			l2.setEnterprise(enterprise);
			l2.setLabel("Spring");
			l2.setProject(project);
			l2.setIssues(new ArrayList<>());
			Label l3 = new Label();
			l3.setEnterprise(enterprise);
			l3.setLabel("Spring Data JPA");
			l3.setProject(project);
			l3.setIssues(new ArrayList<>());
			Label l4 = new Label();
			l4.setEnterprise(enterprise);
			l4.setLabel("Spring Security");
			l4.setProject(project);
			l4.setIssues(new ArrayList<>());
			Label l5 = new Label();
			l5.setEnterprise(enterprise);
			l5.setLabel("Mysql");
			l5.setProject(project);
			l5.setIssues(new ArrayList<>());
			Label l6 = new Label();
			l6.setEnterprise(enterprise);
			l6.setLabel("EJB");
			l6.setProject(project);
			l6.setIssues(new ArrayList<>());

			labelRepository.save(l1);
			labelRepository.save(l2);
			labelRepository.save(l3);
			labelRepository.save(l4);
			labelRepository.save(l5);
			labelRepository.save(l6);
			project.getLabels().add(l1);
			project.getLabels().add(l2);
			project.getLabels().add(l3);
			project.getLabels().add(l4);
			project.getLabels().add(l5);
			project.getLabels().add(l6);
			projectRepository.save(project);
			List<Issue> cases = caseRepository.getIssues(project.getId());
			for (Issue c : cases) {
				c.setLabels(new ArrayList<>());
				if (random.nextInt(6) == 0) {
					c.getLabels().add(l1);
					l1.getIssues().add(c);
					l1.setTotalIssues(l1.gettotalIssues() + 1);
				}
				if (random.nextInt(6) == 0) {
					c.getLabels().add(l2);
					l2.getIssues().add(c);
					l2.setTotalIssues(l2.gettotalIssues() + 1);
				}
				if (random.nextInt(6) == 0) {
					c.getLabels().add(l3);
					l3.getIssues().add(c);
					l3.setTotalIssues(l3.gettotalIssues() + 1);
				}
				if (random.nextInt(6) == 0) {
					c.getLabels().add(l4);
					l4.getIssues().add(c);
					l4.setTotalIssues(l4.gettotalIssues() + 1);
				}
				if (random.nextInt(6) == 0) {
					c.getLabels().add(l5);
					l5.getIssues().add(c);
					l5.setTotalIssues(l5.gettotalIssues() + 1);
				}
				if (random.nextInt(6) == 0) {
					c.getLabels().add(l6);
					l6.getIssues().add(c);
					l6.setTotalIssues(l6.gettotalIssues() + 1);
				}
				caseRepository.save(c);
			}
			labelRepository.save(l1);
			labelRepository.save(l2);
			labelRepository.save(l3);
			labelRepository.save(l4);
			labelRepository.save(l5);
			labelRepository.save(l6);
		}
	}
	
	@Transactional
	public void clearCase() {
		List<RoleGroup> roleGroups = roleGroupRepository.findAll();
		for (RoleGroup roleGroup : roleGroups) {
			roleGroup.getUsers().size();
			Iterator<EnterpriseUser> it = roleGroup.getUsers().iterator();
			while (it.hasNext()) {
				if (it.next().getId() == 1l) {
					it.remove();
				}
			}
			roleGroupRepository.save(roleGroup);
		}
		List<Version> versions = versionRepository.findAll();
		for (Version version : versions) {
			version.getRelatedIssues().size();
			version.getRelatedIssues().size();
			version.setReleaseIssues(new ArrayList<>());
			version.setRelatedIssues(new ArrayList<>());
			versionRepository.save(version);
		}
		List<Issue> cases = caseRepository.findAll();
		for (Issue c : cases) {
			c.setVersions(new ArrayList<>());
			c.setReleaseVersion(null);
			caseRepository.save(c);
			caseRepository.delete(c);
		}
		System.out.println("removed");
	}
	
	@Transactional
	public void createUser() {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);

		EnterpriseUser u1 = new EnterpriseUser();
		u1.setEnterprise(enterprise);
		u1.setAccount("account1");
		u1.setFirstName("first1");
		u1.setLastName("last1");
		u1.setEmail("account1@codeoffice.com");
		u1.setPassword("e10adc3949ba59abbe56e057f20f883e");
		u1.setCreate(new Date());
		u1.setLogin(new Date());
		u1.setProfilePath("male.jpg");
		u1.setGender(true);
		EnterpriseUser u2 = new EnterpriseUser();
		u2.setEnterprise(enterprise);
		u2.setAccount("account2");
		u2.setFirstName("first2");
		u2.setLastName("last2");
		u2.setEmail("account2@codeoffice.com");
		u2.setPassword("e10adc3949ba59abbe56e057f20f883e");
		u2.setCreate(new Date());
		u2.setLogin(new Date());
		u2.setProfilePath("female.jpg");
		u2.setGender(false);
		EnterpriseUser u3 = new EnterpriseUser();
		u3.setEnterprise(enterprise);
		u3.setAccount("account3");
		u3.setFirstName("first3");
		u3.setLastName("last3");
		u3.setEmail("account3@codeoffice.com");
		u3.setPassword("e10adc3949ba59abbe56e057f20f883e");
		u3.setCreate(new Date());
		u3.setLogin(new Date());
		u3.setProfilePath("male.jpg");
		u3.setGender(true);
		EnterpriseUser u4 = new EnterpriseUser();
		u4.setEnterprise(enterprise);
		u4.setAccount("account4");
		u4.setFirstName("first4");
		u4.setLastName("last4");
		u4.setEmail("account4@codeoffice.com");
		u4.setPassword("e10adc3949ba59abbe56e057f20f883e");
		u4.setCreate(new Date());
		u4.setLogin(new Date());
		u4.setProfilePath("female.jpg");
		u4.setGender(false);
		EnterpriseUser u5 = new EnterpriseUser();
		u5.setEnterprise(enterprise);
		u5.setAccount("account5");
		u5.setFirstName("first5");
		u5.setLastName("last5");
		u5.setEmail("account5@codeoffice.com");
		u5.setPassword("e10adc3949ba59abbe56e057f20f883e");
		u5.setCreate(new Date());
		u5.setLogin(new Date());
		u5.setProfilePath("male.jpg");
		u5.setGender(true);
		EnterpriseUser u6 = new EnterpriseUser();
		u6.setEnterprise(enterprise);
		u6.setAccount("account6");
		u6.setFirstName("first6");
		u6.setLastName("last6");
		u6.setEmail("account6@codeoffice.com");
		u6.setPassword("e10adc3949ba59abbe56e057f20f883e");
		u6.setCreate(new Date());
		u6.setLogin(new Date());
		u6.setProfilePath("female.jpg");
		u6.setGender(false);

		enterpriseUserRepository.save(u1);
		enterpriseUserRepository.save(u2);
		enterpriseUserRepository.save(u3);
		enterpriseUserRepository.save(u4);
		enterpriseUserRepository.save(u5);
		enterpriseUserRepository.save(u6);
	}
	
	@Transactional
	public void createCases() {
		List<Project> projects = projectRepository.findAll();
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);
		List<EnterpriseUser> user = enterpriseUserRepository.findAll();
		Random random = new Random();
		for (Project project : projects) {
			List<Issue> cases = new ArrayList<>();
			int noCase = 20 + random.nextInt(30);
			for (int i = 0; i < noCase; i++) {
				project.getRoleGroups().size();
				project.getRoleGroups().forEach(roleGroup -> roleGroup.getUsers().size());
				project.getVersions().size();
				project.getVersions().forEach(version -> version.getRelatedIssues().size());
				project.getVersions().forEach(version -> version.getReleaseIssues().size());
				project.getComponents().size();
				Issue c = new Issue();
				c.setProject(project);
				c.setEnterprise(enterprise);
				c.setSummary("Case summary " + i + " for project" + project.getCode());
				c.setDescription("Case description number " + i + " for project" + project.getName());
				c.setCreate(DateUtil.getRandomDateInRange("2014-05-01", "2014-05-10"));
				c.setUpdate(DateUtil.addRandomDate(c.getCreate(), DateUtil.DAY_ * 6));
				if (c.getUpdate().after(new Date())) {
					c.setUpdate(new Date());
				}
				EnterpriseUser assignee = user.get(random.nextInt(user.size()));
				c.setAssignee(assignee);
				EnterpriseUser reporter = user.get(random.nextInt(user.size()));
				c.setReporter(reporter);
				for (RoleGroup roleGroup : roleGroupRepository.getProjectRoleGroups(enterprise, project.getCode())) {
					roleGroup.getUsers().size();
					if (!roleGroup.getUsers().contains(assignee)) {
						roleGroup.getUsers().add(assignee);
					}
					if (!roleGroup.getUsers().contains(reporter)) {
						roleGroup.getUsers().add(reporter);
					}
					roleGroupRepository.save(roleGroup);
				}
				if (i < 10) {
					c.setCode(project.getCode() + "-" + "0000" + i);
				} else {
					c.setCode(project.getCode() + "-" + "000" + i);
				}
				
				IssueStatus status = IssueStatus.values()[random.nextInt(IssueStatus.values().length)];	
				
				c.setType(IssueType.values()[random.nextInt(IssueType.values().length)]);	
				c.setPriority(IssuePriority.values()[random.nextInt(IssuePriority.values().length)]);
				c.setStatus(status);
				if (status == IssueStatus.CLO || status == IssueStatus.RES) {
					c.setResolution(CaseResolution.values()[random.nextInt(CaseResolution.values().length)]);
					c.setClose(DateUtil.addRandomDate(c.getUpdate(), DateUtil.DAY_ * 3));
					if (c.getClose().after(new Date())) {
						c.setClose(new Date());
					}
				}
				
				List<Component> cc = new ArrayList<>();
				List<Version> cv = new ArrayList<>();
				int nocc = random.nextInt(project.getComponents().size());
				int nocv = random.nextInt(project.getVersions().size());
				for (int j = 0; j < nocc; j++) {
					Component t = project.getComponents().get(j);
					if (t.getIssues() == null) {
						t.setIssues(Arrays.asList(c));
					} else {
						t.getIssues().add(c);
					}
					cc.add(t);
				}
				for (int j = 0; j < nocv; j++) {
					Version v = project.getVersions().get(j);
					if (v.getRelatedIssues() == null) {
						v.setRelatedIssues(Arrays.asList(c));
					} else {
						v.getRelatedIssues().add(c);
					}
					cv.add(v);
				}
				c.setComponents(cc);
				c.setVersions(cv);
				if (random.nextBoolean()) {
					c.setReleaseVersion(project.getVersions().get(random.nextInt(project.getVersions().size())));
					if (c.getReleaseVersion().getReleaseIssues() == null) {
						c.getReleaseVersion().setReleaseIssues(Arrays.asList(c));
					} else {
						c.getReleaseVersion().getReleaseIssues().add(c);
					}
				}
				caseRepository.save(c);
				if (c.getReleaseVersion() != null) {
					versionRepository.save(c.getReleaseVersion());
				}
				cases.add(c);
			}
			project.setTotalIssues(noCase);
			projectRepository.save(project);
			
		}
	}
	
	@Transactional
	public void createRoles() {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);
		
		ProjectRole r1 = new ProjectRole();
		r1.setEnterprise(enterprise);
		r1.setName("Developer");
		r1.setValue(2 + 1);
		r1.setDescription("System developer");
		ProjectRole r2 = new ProjectRole();
		r2.setEnterprise(enterprise);
		r2.setName("Designer");
		r2.setValue(8 + 4 + 2 + 1);
		r2.setDescription("Front-end/Back-end designer");
		ProjectRole r3 = new ProjectRole();
		r3.setEnterprise(enterprise);
		r3.setName("Project manager");
		r3.setValue(64 + 32 + 16 + 8 + 4 + 2 + 1);
		r3.setDescription("Project manager");
		ProjectRole r4 = new ProjectRole();
		r4.setEnterprise(enterprise);
		r4.setName("Manager");
		r4.setValue(128 + 64 + 32 + 16 + 8 + 4 + 2 + 1);
		r4.setDescription("Enterprise manager");
		ProjectRole r5 = new ProjectRole();
		r5.setEnterprise(enterprise);
		r5.setName("Analyst");
		r5.setValue(64 + 16  + 4 + 1);
		r5.setDescription("Project analyst");
		ProjectRole r6 = new ProjectRole();
		r6.setEnterprise(enterprise);
		r6.setName("Administrator");
		r6.setValue(256 + 128 + 64 + 32 + 16 + 8 + 4 + 2 + 1);
		r6.setDescription("System Administrator");

		roleRepository.save(r1);
		roleRepository.save(r2);
		roleRepository.save(r3);
		roleRepository.save(r4);
		roleRepository.save(r5);
		roleRepository.save(r6);
	}
	
	@Transactional
	public void createProjectCategory() {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);
		
		ProjectCategory c1 = new ProjectCategory();
		c1.setEnterprise(enterprise);
		c1.setName("CodeOffice Project");
		ProjectCategory c2 = new ProjectCategory();
		c2.setEnterprise(enterprise);
		c2.setName("CodeOffice Database");
		ProjectCategory c3 = new ProjectCategory();
		c3.setEnterprise(enterprise);
		c3.setName("CodeOffice Plugin");
		ProjectCategory c4 = new ProjectCategory();
		c4.setEnterprise(enterprise);
		c4.setName("CodeOffice Form");
		ProjectCategory c5 = new ProjectCategory();
		c5.setEnterprise(enterprise);
		c5.setName("CodeOffice Communication");
		ProjectCategory c6 = new ProjectCategory();
		c6.setEnterprise(enterprise);
		c6.setName("CodeOffice Web Service");

		projectCategoryRepository.save(c1);
		projectCategoryRepository.save(c2);
		projectCategoryRepository.save(c3);
		projectCategoryRepository.save(c4);
		projectCategoryRepository.save(c5);
		projectCategoryRepository.save(c6);
	}
	
	@Transactional
	public void createProject() {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);
		
		EnterpriseUser user = enterpriseUserRepository.findOne(1l);
		
		List<ProjectCategory> categories = projectCategoryRepository.findAll();
		Random random = new Random();
		
		for (int i = 0; i < 20; i++) {
			Project p = new Project();
			p.setEnterprise(enterprise);
			p.setCategory(categories.get(random.nextInt(categories.size())));
			p.setCreate(DateUtil.getRandomDateInRange("2014-05-01", "2014-05-10"));
			p.setCompleted(random.nextInt(10) < 2);
			p.setUpdate(DateUtil.addRandomDate(p.getCreate(), DateUtil.DAY_ * 5));
			p.setEnd(p.isCompleted() ?  DateUtil.getRandomDateInRange("2014-05-01", "2014-05-10"): DateUtil.getRandomDateInRange("2014-05-10", "2014-05-20"));
			p.setRemoved(random.nextInt(10) == 0);
			p.setName("Codeoffice project " + (i + 1));
			p.setDescription("Code office project");
			p.setLead(user);
			if (i < 10) {
				p.setCode("COP00" + i);
			} else {
				p.setCode("COP0" + i);
			}
			projectRepository.save(p);
			
			List<Version> versions = new ArrayList<>();
			List<Component> components = new ArrayList<>();
			List<RoleGroup> roleGroups = new ArrayList<>();
			
			for (int j = 0; j < 5; j++) {
				Version v = new Version();
				v.setEnterprise(enterprise);
				v.setProject(p);
				v.setStart(DateUtil.addRandomDate(p.getCreate(), DateUtil.DAY_ * 2));
				v.setStarted(random.nextBoolean());
				v.setRelease(DateUtil.addRandomDate(v.getStart(), DateUtil.WEEK_ * 1));
				v.setDelay(v.getRelease().before(new Date()) ? DateUtil.addRandomDate(v.getRelease(), DateUtil.WEEK_ * 1) : null);
				v.setCode("VER00" + j);
				v.setDescription("Description for " + v.getCode());
				versionRepository.save(v);
				versions.add(v);
			}
			
			for (int j = 0; j < 5; j++) {
				Component c = new Component();
				c.setEnterprise(enterprise);
				c.setProject(p);
				c.setCode("COM00" + j);
				c.setDescription("Component code 005");
				c.setName("Component code 005");
				c.setLead(user);
				c.setDefaultAssignee(user);
				c.setDefaultReporter(user);
				components.add(c);
				componentRepository.save(c);
			}
			
			List<ProjectRole> roles = roleRepository.findAll();
			
			for (ProjectRole role : roles) {
				RoleGroup g = new RoleGroup();
				g.setEnterprise(enterprise);
				g.setProject(p);
				g.setRole(role);
				g.setUsers(Arrays.asList(user));
				roleGroupRepository.save(g);
			}
			
			p.setRoleGroups(roleGroups);
			p.setVersions(versions);
			p.setComponents(components);
			projectRepository.save(p);
		}
	}
	
}
