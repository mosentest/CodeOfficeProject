package mu.codeoffice.query;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import mu.codeoffice.entity.Issue;
import mu.codeoffice.entity.Issue_;
import mu.codeoffice.enums.IssuePriority;
import mu.codeoffice.enums.IssueStatus;
import mu.codeoffice.enums.IssueType;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;

public class CaseSpecifications {

	public static Specification<Issue> all(Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, IssueStatus status, IssueType type, IssuePriority priority) {
		return new Specification<Issue>() {
			@Override
			public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority);
				predicates.add(builder.isFalse(root.get(Issue_.removed)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public static Specification<Issue> inProgress(Long project, Long assignee, Long reporter) {
		return new Specification<Issue>() {
			@Override
			public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, null, project, null, null, null, null, assignee, reporter, null, null, null);
				predicates.add(builder.isFalse(root.get(Issue_.removed)));
				predicates.add(builder.isTrue(root.get(Issue_.inProgress)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}
	
	public static Specification<Issue> closed(Long project, Long assignee, Long reporter) {
		return new Specification<Issue>() {
			@Override
			public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, null, project, null, null, null, null, assignee, reporter, null, null, null);
				predicates.add(builder.isFalse(root.get(Issue_.removed)));
				predicates.add(builder.isNotNull(root.get(Issue_.close)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Issue> assigned(Long project, Long assignee, Long reporter) {
		return new Specification<Issue>() {
			@Override
			public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, null, project, null, null, null, null, assignee, reporter, null, null, null);
				predicates.add(builder.isFalse(root.get(Issue_.removed)));
				predicates.add(builder.notEqual(root.get(Issue_.status), IssueStatus.CLO));
				predicates.add(builder.notEqual(root.get(Issue_.status), IssueStatus.RES));
				predicates.add(builder.isFalse(root.get(Issue_.inProgress)));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Issue> unresolved(Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter,  
			IssueStatus status, IssueType type, IssuePriority priority) {
		return new Specification<Issue>() {
			@Override
			public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, releaseVersion, component, label, assignee, reporter, status, type, priority);
				predicates.add(builder.isFalse(root.get(Issue_.removed)));
				predicates.add(builder.notEqual(root.get(Issue_.status), IssueStatus.CLO));
				predicates.add(builder.notEqual(root.get(Issue_.status), IssueStatus.RES));
				return builder.and(predicates.toArray(new Predicate[predicates.size()]));
			}
		};
	}

	public static Specification<Issue> resolved(Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter,  
			IssueType type, IssuePriority priority) {
		return new Specification<Issue>() {
			@Override
			public Predicate toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
				List<Predicate> predicates = CaseSpecifications.toPredicate(root, query, builder, date, project, version, releaseVersion, component, label, assignee, reporter, null, type, priority);
				predicates.add(builder.isFalse(root.get(Issue_.removed)));
				predicates.add(builder.and(
						builder.or(
								builder.equal(root.get(Issue_.status), IssueStatus.CLO),
								builder.equal(root.get(Issue_.status), IssueStatus.RES))
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
	
	private static List<Predicate> toPredicate(Root<Issue> root, CriteriaQuery<?> query, CriteriaBuilder builder, 
			Date date, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, 
			IssueStatus status, IssueType type, IssuePriority priority) {
		List<Predicate> predicates = new ArrayList<>();
		if (version != null) {
			predicates.add(builder.equal(root.join(Issue_.versions, JoinType.LEFT).<Long>get("id"), version));
		}
		if (component != null) {
			predicates.add(builder.equal(root.join(Issue_.components, JoinType.LEFT).<Long>get("id"), component));
		}
		if (label != null) {
			predicates.add(builder.equal(root.join(Issue_.labels, JoinType.LEFT).<Long>get("id"), label));
		}
		if (project != null) {
			predicates.add(builder.equal(root.get(Issue_.project).<Long>get("id"), project));
		}
		if (releaseVersion != null) {
			predicates.add(builder.equal(root.get(Issue_.releaseVersion).<Long>get("id"), releaseVersion));
		}
		if (assignee != null) {
			predicates.add(builder.equal(root.get(Issue_.assignee).<Long>get("id"), assignee));
		}
		if (reporter != null) {
			predicates.add(builder.equal(root.get(Issue_.reporter).<Long>get("id"), reporter));
		}
		if (status != null) {
			predicates.add(builder.equal(root.get(Issue_.status), status));
		}
		if (type != null) {
			predicates.add(builder.equal(root.get(Issue_.type), type));
		}
		if (priority != null) {
			predicates.add(builder.equal(root.get(Issue_.priority), priority));
		}
		if (date != null) {
			predicates.add(builder.lessThanOrEqualTo(root.get(Issue_.create), date));
		}
		return predicates;
	}
	
}
