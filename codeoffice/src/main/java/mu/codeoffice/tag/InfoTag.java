package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class InfoTag extends SimpleTagSupport {
	
	private static final String WARNING = "warning";
	
	private static final String INFO = "info";
	
	private String type = INFO;
	
	private String message;
	
	private String code;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"info-element imglink\">");
		if (WARNING.equals(type)) {
			buffer.append("<img src=\"img/warning.png\"/>");
		} else if (INFO.equals(type)) {
			buffer.append("<img src=\"img/info.png\"/>");
		}
		if (message != null) {
			buffer.append("<span class=\"text\">" + message + "</span>");
		}
		if (code != null) {
			
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
