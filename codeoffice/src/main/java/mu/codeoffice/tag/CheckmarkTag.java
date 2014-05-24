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
			buffer.append("<img src=\"assets/img/icon-checkmark.png\"/>");
		} else if (!value && !checkmarkOnly) {
			buffer.append("<img src=\"assets/img/icon-cross.png\"/>");
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
