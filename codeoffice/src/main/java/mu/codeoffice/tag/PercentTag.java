package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PercentTag extends SimpleTagSupport {
	
	private int number;
	
	private int total;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		buffer.append("<div class=\"percent\">");
		buffer.append(String.format("<div style=\"float: left; background-color: #3c78b5; width: %.1f%%; height: 16px;\"></div>", (float) number / total * 90));
		buffer.append(String.format("<div style=\"float: left; margin-left: 10px; font-size: 14px; color: #3c78b5\">%.1f%%</div>", (float) number / total * 100));
		buffer.append("</div>");
		out.println(buffer.toString());
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}
}
