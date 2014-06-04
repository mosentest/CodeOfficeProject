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
	
	public static Specification<User> search(final Enterprise enterprise, String queryString) {
		return new Specification<User>() {

			@Override
			public Predicate toPredicate(Root<User> root,
					CriteriaQuery<?> query, CriteriaBuilder builder) {
				query.distinct(true);
				return builder.and(
						builder.equal(root.get(User_.enterprise), enterprise),
						builder.or(
								builder.or(
										builder.like(root.get(User_.firstName), "%" + queryString + "%"), 
										builder.like(root.get(User_.lastName), "%" + queryString + "%")), 
								builder.like(root.get(User_.email), "%" + queryString + "%")));
			}
			
		};
	}
	
	public static Specification<User> groupFilter(final Enterprise enterprise, final Long group, final String account, final String name) {
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
				if (group != null) {
					predicates.add(builder.equal(root.join(User_.userGroups, JoinType.LEFT).<String>get("id"), group));
				}
				predicates.add(builder.equal(root.get(User_.enterprise), enterprise));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
			
		};
	}
}
