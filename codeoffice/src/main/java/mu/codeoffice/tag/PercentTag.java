package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class PercentTag extends SimpleTagSupport {
	
	private int number;
	
	private int total;
	
	private String background = "white";
	
	private String color = "#3c78b5";
	
	private int width;
	
	private boolean showPercentage = true;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		float percentage = total == 0 ? 0f : (float) number / total;
		if (width == 0) {
			buffer.append("<div class=\"percent imglink\" style=\"background-color: " + background + "; height: 16px;\">");
			buffer.append(String.format("<div style=\"float: left; background-color: %s; width: %.1f%%; height: 16px;\"></div>", color, percentage * 90));
		} else {
			buffer.append(String.format("<div class=\"percent\" style=\"background-color: %s; width: %dpx;\">", background, width));
			buffer.append(String.format("<div style=\"float: left; background-color: %s; width: %.1fpx; height: 16px;\"></div>", color, percentage * width));
		}
		if (showPercentage) {
			buffer.append(String.format("<div style=\"float: left; margin-left: 10px; font-size: 14px; color: %s;\">%.1f%%</div>", color, percentage * 100));
		}
		buffer.append("<div class=\"clearfix\"></div></div>");
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

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public boolean isShowPercentage() {
		return showPercentage;
	}

	public void setShowPercentage(boolean showPercentage) {
		this.showPercentage = showPercentage;
	}
}
