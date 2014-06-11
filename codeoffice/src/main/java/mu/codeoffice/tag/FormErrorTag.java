package mu.codeoffice.tag;

import java.io.IOException;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class FormErrorTag extends SimpleTagSupport {
	
	private List<String> errors;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringBuilder buffer = new StringBuilder();
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
