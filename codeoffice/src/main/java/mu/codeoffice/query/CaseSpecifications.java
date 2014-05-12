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
import mu.codeoffice.entity.Case_;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;

import org.springframework.data.jpa.domain.Specification;

public class CaseSpecifications {

	public static Specification<Case> countAll(Date date, Long project, Long version, Long component, Long label, 
			Long assignee, Long reporter,  
			CaseStatus status, CaseType type, CasePriority priority) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, component, label, assignee, reporter, status, type, priority);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Case> countUnresolved(Date date, Long project, Long version, Long component, Long label, 
			Long assignee, Long reporter,  
			CaseStatus status, CaseType type, CasePriority priority) {
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
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	private static List<Predicate> toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder, 
			Date date, Long project, Long version, Long component, Long label, 
			Long assignee, Long reporter, 
			CaseStatus status, CaseType type, CasePriority priority) {
		List<Predicate> predicates = new ArrayList<>();
		if (version != null) {
			predicates.add(builder.equal(root.join(Case_.versions, JoinType.LEFT).<Long>get("id"), version));
		}
		if (component != null) {
			predicates.add(builder.equal(root.join(Case_.components, JoinType.LEFT).<Long>get("id"), component));
		}
		if (label != null) {
			predicates.add(builder.equal(root.join(Case_.labels, JoinType.LEFT).<Long>get("id"), label));
		}
		if (project != null) {
			predicates.add(builder.equal(root.get(Case_.project).<Long>get("id"), project));
		}
		if (assignee != null) {
			predicates.add(builder.equal(root.get(Case_.assignee).<Long>get("id"), assignee));
		}
		if (reporter != null) {
			predicates.add(builder.equal(root.get(Case_.reporter).<Long>get("id"), reporter));
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
