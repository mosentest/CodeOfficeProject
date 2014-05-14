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

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class CaseSpecifications {

	public static Specification<Case> all(Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public static Specification<Case> inProgress(Long project, Long assignee, Long reporter) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, null, project, null, null, null, null, assignee, reporter, null, null, null);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				predicates.add(builder.isTrue(root.get(Case_.inProgress)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public static Specification<Case> closed(Long project, Long assignee, Long reporter) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, null, project, null, null, null, null, assignee, reporter, null, null, null);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				predicates.add(builder.isNotNull(root.get(Case_.close)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Case> assigned(Long project, Long assignee, Long reporter) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, null, project, null, null, null, null, assignee, reporter, null, null, null);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				predicates.add(builder.notEqual(root.get(Case_.status), CaseStatus.CLO));
				predicates.add(builder.notEqual(root.get(Case_.status), CaseStatus.RES));
				predicates.add(builder.isFalse(root.get(Case_.inProgress)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Case> unresolved(Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter,  
			CaseStatus status, CaseType type, CasePriority priority) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority);
				predicates.add(builder.isFalse(root.get(Case_.removed)));
				predicates.add(builder.notEqual(root.get(Case_.status), CaseStatus.CLO));
				predicates.add(builder.notEqual(root.get(Case_.status), CaseStatus.RES));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Case> resolved(Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter,  
			CaseType type, CasePriority priority) {
		return new Specification<Case>() {
			@Override
			public Predicate toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, releaseVersion, component, label, assignee, reporter, null, type, priority);
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
	
	public static Pageable pageSpecification(int pageIndex, int size, Sort sort) {
		return new PageRequest(pageIndex, size, sort);
	}
	
	public static Sort sort(boolean ascending, String column) {
		return new Sort(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, column);
	}
	
	private static List<Predicate> toPredicate(Root<Case> root, CriteriaQuery<?> query, CriteriaBuilder builder, 
			Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
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
		if (releaseVersion != null) {
			predicates.add(builder.equal(root.get(Case_.releaseVersion).<Long>get("id"), releaseVersion));
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
