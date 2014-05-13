package mu.codeoffice.controller;

import mu.codeoffice.service.VersionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enterprise/")
public class VersionController {

	@Autowired
	private VersionService versionService;
	
}
