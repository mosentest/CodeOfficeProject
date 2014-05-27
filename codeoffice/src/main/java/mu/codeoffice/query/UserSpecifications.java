package mu.codeoffice.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mu.codeoffice.entity.Enterprise;
import mu.codeoffice.entity.EnterpriseUser;
import mu.codeoffice.entity.EnterpriseUser_;
import mu.codeoffice.utility.StringUtil;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecifications {
	
	public static Specification<EnterpriseUser> availableForGroup(final Enterprise enterprise, String userGroup, String searchString, Long[] id) {
		return new Specification<EnterpriseUser>() {

			@Override
			public Predicate toPredicate(Root<EnterpriseUser> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				return builder.and(
						builder.notEqual(root.join(EnterpriseUser_.userGroups, JoinType.LEFT).<String>get("name"), userGroup),
						builder.equal(root.get(EnterpriseUser_.enterprise), enterprise),
						builder.not(root.get(EnterpriseUser_.id).in((Object[]) id)),
						builder.or(
								builder.or(
										builder.like(root.get(EnterpriseUser_.firstName), "%" + searchString + "%"), 
										builder.like(root.get(EnterpriseUser_.lastName), "%" + searchString + "%")), 
								builder.like(root.get(EnterpriseUser_.email), "%" + searchString + "%")));
			}
			
		};
	}

	public static Specification<EnterpriseUser> generic(final Enterprise enterprise, Long userGroup, Long globalPermission, Long projectPermission) {
		return new Specification<EnterpriseUser>() {

			@Override
			public Predicate toPredicate(Root<EnterpriseUser> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				if (globalPermission != null) {
					predicates.add(builder.equal(root.join(EnterpriseUser_.globalPermissions, JoinType.LEFT).<String>get("id"), globalPermission));
				}
				if (projectPermission != null) {
					predicates.add(builder.equal(root.join(EnterpriseUser_.projectPermissions, JoinType.LEFT).<String>get("id"), projectPermission));
				}
				if (userGroup != null) {
					predicates.add(builder.equal(root.join(EnterpriseUser_.userGroups, JoinType.LEFT).<String>get("id"), userGroup));
				}
				predicates.add(builder.equal(root.get(EnterpriseUser_.enterprise), enterprise));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
	}
	
	public static Specification<EnterpriseUser> search(final Enterprise enterprise, final String account, final String name, final Long groupFilter) {
		return new Specification<EnterpriseUser>() {

			@Override
			public Predicate toPredicate(Root<EnterpriseUser> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = new ArrayList<>();
				if (!StringUtil.isEmptyString(account)) {
					predicates.add(builder.or(
							builder.like(root.get(EnterpriseUser_.account), "%" + account + "%"),
							builder.like(root.get(EnterpriseUser_.email), "%" + account + "%")));
				}
				if (!StringUtil.isEmptyString(name)) {
					predicates.add(builder.or(
							builder.like(root.get(EnterpriseUser_.firstName), "%" + name + "%"),
							builder.like(root.get(EnterpriseUser_.lastName), "%" + name + "%")));
				}
				if (groupFilter != null) {
					predicates.add(builder.equal(root.join(EnterpriseUser_.userGroups, JoinType.LEFT).<String>get("id"), groupFilter));
				}
				predicates.add(builder.equal(root.get(EnterpriseUser_.enterprise), enterprise));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
	}
}
