package mu.codeoffice.query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.UserGroup;
import mu.codeoffice.entity.UserGroup_;
import mu.codeoffice.utility.StringUtil;

import org.springframework.data.jpa.domain.Specification;

public class UserGroupSpecifications {

	public static Specification<UserGroup> all(final Enterprise enterprise, final String name) {
		return new Specification<UserGroup>() {

			@Override
			public Predicate toPredicate(Root<UserGroup> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				if (!StringUtil.isEmptyString(name)) {
					return builder.and(
							builder.like(root.get(UserGroup_.name), "%" + name + "%"),
							builder.equal(root.get(UserGroup_.enterprise), enterprise));
				} 
				return builder.equal(root.get(UserGroup_.enterprise), enterprise);
			}
			
		};
	}
}
