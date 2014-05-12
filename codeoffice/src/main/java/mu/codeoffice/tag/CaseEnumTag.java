package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mu.codeoffice.enums.CaseEnum;

public class CaseEnumTag extends SimpleTagSupport {
	
	private CaseEnum value;
	
	private String text;
	
	private boolean imageOnly = true;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		buffer.append("<span class=\"imglink\"><img src=\"img/office/" + value.getImagePath() + ".png\"/>");
		if (!imageOnly) {
			buffer.append("<span style=\"margin-left: 5px;\">" + text + "</span></span>");
		}
		out.println(buffer.toString());
	}

	public CaseEnum getValue() {
		return value;
	}

	public void setValue(CaseEnum value) {
		this.value = value;
	}

	public boolean isImageOnly() {
		return imageOnly;
	}

	public void setImageOnly(boolean imageOnly) {
		this.imageOnly = imageOnly;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		System.out.println("setting text: " + text);
	}
}
