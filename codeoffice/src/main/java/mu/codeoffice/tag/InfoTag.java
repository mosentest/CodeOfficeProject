package mu.codeoffice.tag;

import java.io.IOException;
import java.io.StringWriter;

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
	
	private static final String ERROR = "error";
	
	private static final String TIP = "tip";
	
	private static final String ALERT = "alert";
	
	private String arguments;
	
	private String type = INFO;
	
	private String title;
	
	private String message;
	
	private String separator = DEFAULT_ARGUMENT_SEPARATOR;
	
	private boolean banner;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringWriter writer = new StringWriter();
		if (messageSource == null) {
			ServletContext servletContext = ((PageContext) this.getJspContext()).getServletContext();
			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			messageSource = (MessageSource) applicationContext.getBean("messageSource");
		}

		StringBuilder buffer = new StringBuilder();
		if (banner) {
			buffer.append("<div class=\"banner-info-element imglink " + getColor() + "\" >");
		} else {
			buffer.append("<div class=\"info-element imglink " + getColor() + "\" >");
		}
		buffer.append(getIcon());
		buffer.append("<span class=\"info-element-title\">" + getTitleMessage() + "</span>");
		if (getJspBody() != null) {
			buffer.append("<span class=\"info-element-content\">");
			getJspBody().invoke(writer);
			buffer.append(writer.getBuffer());
			buffer.append("</span>");
		}
		buffer.append("</div>");
		out.println(buffer.toString());
	}
	
	private String getTitleMessage() {
		if (message != null) {
			return message;
		}
		Object[] arguments = null;
		if (this.arguments != null) {
			arguments = this.arguments.split(separator);
		}
		return messageSource.getMessage(title, arguments, LocaleContextHolder.getLocale());
	}
	
	private String getColor() {
		if (type.equals(INFO)) {
			return "info-element-info";
		} else if (type.equals(WARNING)) {
			return "info-element-warning";
		} else if (type.equals(ERROR)) {
			return "info-element-error";
		} else if (type.equals(TIP)) {
			return "info-element-tip";
		} else if (type.equals(ALERT)) {
			return "info-element-alert";
		}
		return "info-element-info";
	}
	
	private String getIcon() {
		if (type.equals(INFO)) {
			return "<img class=\"info-element-icon\" src=\"assets/img/information/icon-info.png\">";
		} else if (type.equals(WARNING)) {
			return "<img class=\"info-element-icon\" src=\"assets/img/information/icon-warning.png\">";
		} else if (type.equals(ERROR)) {
			return "<img class=\"info-element-icon\" src=\"assets/img/information/icon-error.png\">";
		} else if (type.equals(TIP)) {
			return "<img class=\"info-element-icon\" src=\"assets/img/information/icon-tip.png\">";
		} else if (type.equals(ALERT)) {
			return "<span class=\"info-element-icon iconfont-large iconfont-error\"\"></span>";
		}
		return "<img class=\"info-element-icon\" src=\"assets/img/information/icon-info.png\">";
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isBanner() {
		return banner;
	}

	public void setBanner(boolean banner) {
		this.banner = banner;
	}
}
