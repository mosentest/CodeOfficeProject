package mu.codeoffice.service;

import static mu.codeoffice.query.CaseSpecifications.all;
import static mu.codeoffice.query.CaseSpecifications.pageSpecification;
import static mu.codeoffice.query.CaseSpecifications.sort;

import javax.annotation.Resource;

import mu.codeoffice.entity.Case;
import mu.codeoffice.enums.CasePriority;
import mu.codeoffice.enums.CaseStatus;
import mu.codeoffice.enums.CaseType;
import mu.codeoffice.repository.CaseRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CaseService {

	@Resource
	private CaseRepository caseRepository;
	
	@Transactional(readOnly = true)
	public Page<Case> getCasePage(EnterpriseAuthentication auth, Long project, Long version, Long releaseVersion, Long component, Long label, 
			Long assignee, Long reporter, CaseStatus status, CaseType type, CasePriority priority,
			int pageIndex, int size, String column, boolean ascending) {
		Page<Case> page = caseRepository.findAll(all(null, project, null, null, null, null, null, null, null, null, null), 
				pageSpecification(pageIndex, size, sort(ascending, Case.getSortColumn(column))));
		return page;
	}
	
}
