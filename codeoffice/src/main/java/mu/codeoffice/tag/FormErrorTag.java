package mu.codeoffice.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.MessageSource;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class FormErrorTag extends SimpleTagSupport {
	
	private List<String> errors;
	
	private MessageSource messageSource;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringBuilder buffer = new StringBuilder();
		if (messageSource == null) {
			ServletContext servletContext = ((PageContext) this.getJspContext()).getServletContext();
			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			messageSource = (MessageSource) applicationContext.getBean("messageSource");
		}
		if (errors == null) {
			return;
		}
		buffer.append("<tr class=\"form-error-row\"><td colspan=\"2\">");
		for (String error : errors) {
			buffer.append("<p>" + error + "</p>");
		}
		buffer.append("</td></tr>");
		out.println(buffer.toString());
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
