package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class InfoTag extends SimpleTagSupport {
	
	public static final String DEFAULT_ARGUMENT_SEPARATOR = ",";
	
	private MessageSource messageSource;
	
	private static final String WARNING = "warning";
	
	private static final String INFO = "info";
	
	private String arguments;
	
	private String type = INFO;
	
	private String message;
	
	private String separator = DEFAULT_ARGUMENT_SEPARATOR;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		if (messageSource == null) {
			ServletContext servletContext = ((PageContext) this.getJspContext()).getServletContext();
			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			messageSource = (MessageSource) applicationContext.getBean("messageSource");
		}

		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"info-element imglink\">");
		if (WARNING.equals(type)) {
			buffer.append("<img src=\"img/warning.png\"/>");
		} else if (INFO.equals(type)) {
			buffer.append("<img src=\"img/info.png\"/>");
		}
		Object[] arguments = null;
		if (this.arguments != null) {
			arguments = this.arguments.split(separator);
		}
		if (message != null) {
			buffer.append("<span class=\"text\">" + messageSource.getMessage(message, arguments, LocaleContextHolder.getLocale()) + "</span>");
		}
		buffer.append("</div>");
		out.println(buffer.toString());
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getArguments() {
		return arguments;
	}

	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getSeparator() {
		return separator;
	}

	public void setSeparator(String separator) {
		this.separator = separator;
	}
}
