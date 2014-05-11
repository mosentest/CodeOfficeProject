package mu.codeoffice.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mu.codeoffice.entity.Case;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;
import mu.codeoffice.metamodel.Case_;

import org.springframework.data.jpa.domain.Specification;

public class CaseSpecifications {

	public static Specification<Case> countAll(final Date date, final Long project, final Long version, final Long component, final Long label, 
			final Long assignee, final Long reporter,  
			final CaseStatus status, final CaseType type, final CasePriority priority) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, component, label, assignee, reporter, status, type, priority);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				predicates.forEach(predicate -> builder.and(predicate));
				return builder.conjunction();
			}
		};
	}

	public static Specification<Case> countUnresolved(final Date date, final Long project, final Long version, final Long component, final Long label, 
			final Long assignee, final Long reporter, 
			final CaseStatus status, final CaseType type, final CasePriority priority) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, component, label, assignee, reporter, status, type, priority);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				predicates.add(builder.and(
						builder.or(
								builder.equal(root.get(Case_.status), CaseStatus.CLO),
								builder.equal(root.get(Case_.status), CaseStatus.RES))
						)
				);
				predicates.forEach(predicate -> builder.and(predicate));
				return builder.conjunction();
			}
		};
	}
	
	private static List<Predicate> toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder, 
			Date date, Long project, Long version, Long component, Long label, 
			Long assignee, Long reporter, 
			CaseStatus status, CaseType type, CasePriority priority) {
		List<Predicate> predicates = new ArrayList<>();
		if (version != null) {
			predicates.add(builder.equal(root.join(Case_.versions, JoinType.LEFT).get("id"), version));
		}
		if (component != null) {
			predicates.add(builder.equal(root.join(Case_.components, JoinType.LEFT).get("id"), version));
		}
		if (label != null) {
			predicates.add(builder.equal(root.join(Case_.labels, JoinType.LEFT).get("id"), version));
		}
		if (project != null) {
			predicates.add(builder.equal(root.get(Case_.project).get("id"), project));
		}
		if (assignee != null) {
			predicates.add(builder.equal(root.get(Case_.assignee), assignee));
		}
		if (reporter != null) {
			predicates.add(builder.equal(root.get(Case_.reporter), reporter));
		}
		if (status != null) {
			predicates.add(builder.equal(root.get(Case_.status), status));
		}
		if (type != null) {
			predicates.add(builder.equal(root.get(Case_.type), type));
		}
		if (priority != null) {
			predicates.add(builder.equal(root.get(Case_.priority), priority));
		}
		if (date != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Case_.create), date));
		}
		return predicates;
	}
	
}
