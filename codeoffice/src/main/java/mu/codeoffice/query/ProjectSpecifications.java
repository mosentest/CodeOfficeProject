package mu.codeoffice.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.Project_;

import org.springframework.data.jpa.domain.Specification;

public class ProjectSpecifications {

	public static Specification<Project> project(String code, Enterprise enterprise, EnterpriseUser user) {
		return new Specification<Project>() {

			@Override
			public Predicate toPredicate(Root<Project> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				return builder.and(
						builder.equal(root.get(Project_.code), code),
						builder.equal(root.get(Project_.enterprise), enterprise));
			}
			
		};
	}
	
}
