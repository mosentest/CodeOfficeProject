package mu.codeoffice.service;

import javax.annotation.Resource;

import mu.codeoffice.repository.VersionRepository;

import org.springframework.stereotype.Service;

@Service
public class VersionService {

	@Resource
	private VersionRepository versionRepository;
	
}
