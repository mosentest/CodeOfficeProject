package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class CheckmarkTag extends SimpleTagSupport {
	
	private boolean value;
	
	private boolean checkmarkOnly;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		if (value) {
			buffer.append("<img src=\"img/checkmark.png\" width=\"16\" height=\"16\"/>");
		} else if (!value && !checkmarkOnly) {
			buffer.append("<img src=\"img/cross.png\" width=\"16\" height=\"16\"/>");
		}
		out.println(buffer.toString());
	}

	public boolean isCheckmarkOnly() {
		return checkmarkOnly;
	}

	public void setCheckmarkOnly(boolean checkmarkOnly) {
		this.checkmarkOnly = checkmarkOnly;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}
