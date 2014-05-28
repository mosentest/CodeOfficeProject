package mu.codeoffice.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.User;
import mu.codeoffice.entity.User_;
import mu.codeoffice.utility.StringUtil;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
	
	public static Specification<User> availableForGroup(final Enterprise enterprise, String userGroup, String searchString, Long[] id) {
		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				List<Predicate> predicates = new ArrayList<>();
				predicates.add(builder.equal(root.get(User_.enterprise), enterprise));
				predicates.add(builder.notEqual(root.join(User_.userGroups, JoinType.LEFT).<String>get("name"), userGroup));
				if (id != null && id.length != 0) {
					predicates.add(builder.not(root.get(User_.id).in((Object[]) id)));
				}
				predicates.add(
					builder.or(
						builder.or(
								builder.like(root.get(User_.firstName), "%" + searchString + "%"), 
								builder.like(root.get(User_.lastName), "%" + searchString + "%")), 
						builder.like(root.get(User_.email), "%" + searchString + "%")));

				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
	}

	public static Specification<User> generic(final Enterprise enterprise, Long userGroup, Long globalPermission, Long projectPermission) {
		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				if (globalPermission != null) {
					predicates.add(builder.equal(root.join(User_.globalPermissions, JoinType.LEFT).<String>get("id"), globalPermission));
				}
				if (projectPermission != null) {
					predicates.add(builder.equal(root.join(User_.projectPermissions, JoinType.LEFT).<String>get("id"), projectPermission));
				}
				if (userGroup != null) {
					predicates.add(builder.equal(root.join(User_.userGroups, JoinType.LEFT).<String>get("id"), userGroup));
				}
				predicates.add(builder.equal(root.get(User_.enterprise), enterprise));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
	}
	
	public static Specification<User> search(final Enterprise enterprise, final String account, final String name, final Long groupFilter) {
		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtil.isEmptyString(account)) {
					predicates.add(builder.or(
							builder.like(root.get(User_.account), "%" + account + "%"),
							builder.like(root.get(User_.email), "%" + account + "%")));
				}
				if (!StringUtil.isEmptyString(name)) {
					predicates.add(builder.or(
							builder.like(root.get(User_.firstName), "%" + name + "%"),
							builder.like(root.get(User_.lastName), "%" + name + "%")));
				}
				if (groupFilter != null) {
					predicates.add(builder.equal(root.join(User_.userGroups, JoinType.LEFT).<String>get("id"), groupFilter));
				}
				predicates.add(builder.equal(root.get(User_.enterprise), enterprise));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
	}
}
