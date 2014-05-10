package mu.codeoffice.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import mu.codeoffice.entity.Component;
import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectCategory;
import mu.codeoffice.entity.Role;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.entity.Version;
import mu.codeoffice.repository.ComponentRepository;
import mu.codeoffice.repository.EnterpriseUserRepository;
import mu.codeoffice.repository.ProjectCategoryRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.repository.RoleGroupRepository;
import mu.codeoffice.repository.RoleRepository;
import mu.codeoffice.repository.VersionRepository;
import mu.codeoffice.repository.VisibilityRepository;
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
	private VisibilityRepository visibilityRepository;
	
	@Resource
	private RoleRepository roleRepository;
	
	@Resource
	private RoleGroupRepository roleGroupRepository;
	
	@Resource
	private EnterpriseUserRepository enterpriseUserRepository;
	
	@Transactional
	public void createRoles() {
		Enterprise enterprise = new Enterprise();
		enterprise.setId(1l);
		
		Role r1 = new Role();
		r1.setEnterprise(enterprise);
		r1.setName("Developer");
		r1.setValue(1);
		r1.setDescription("System developer");
		Role r2 = new Role();
		r2.setEnterprise(enterprise);
		r2.setName("Designer");
		r2.setValue(2);
		r2.setDescription("Front-end/Back-end designer");
		Role r3 = new Role();
		r3.setEnterprise(enterprise);
		r3.setName("Project manager");
		r3.setValue(4);
		r3.setDescription("Project manager");
		Role r4 = new Role();
		r4.setEnterprise(enterprise);
		r4.setName("Manager");
		r4.setValue(8);
		r4.setDescription("Enterprise manager");
		Role r5 = new Role();
		r5.setEnterprise(enterprise);
		r5.setName("Analyst");
		r5.setValue(16);
		r5.setDescription("Project analyst");
		Role r6 = new Role();
		r6.setEnterprise(enterprise);
		r6.setName("Administrator");
		r6.setValue(32);
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
			p.setUpdate(DateUtil.addRandomDate(p.getCreate(), DateUtil.DAY * 5));
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
				v.setStart(DateUtil.addRandomDate(p.getCreate(), DateUtil.DAY * 2));
				v.setStarted(random.nextBoolean());
				v.setRelease(DateUtil.addRandomDate(v.getStart(), DateUtil.WEEK * 1));
				v.setDelay(v.getRelease().before(new Date()) ? DateUtil.addRandomDate(v.getRelease(), DateUtil.WEEK * 1) : null);
				v.setCode("VER00" + j);
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
			
			List<Role> roles = roleRepository.findAll();
			
			for (Role role : roles) {
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
