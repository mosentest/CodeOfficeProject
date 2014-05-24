package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ToggleTag extends SimpleTagSupport {
	
	private boolean value;
	
	private boolean toggle;
	
	private String path;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		if (value) {
			buffer.append("<span class=\"toggle-on\">");
		} else {
			buffer.append("<span class=\"toggle-off\">");
		}
		if (toggle) {
			buffer.append("<a class=\"toggleable\" href=\"javascript:void(0);\">");
			buffer.append(value ? "Enabled" : "Disabled");
			buffer.append("</a>");
			buffer.append(String.format("<input class=\"hidden\" type=\"checkbox\" name=\"%s\" %s />", 
					path, value ? "checked=checked": ""));
		} else {
			buffer.append(value ? "Enabled" : "Disabled");
		}
		buffer.append("</span>");
		out.println(buffer.toString());
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public boolean isToggle() {
		return toggle;
	}

	public void setToggle(boolean toggle) {
		this.toggle = toggle;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
