package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ColorTableTag extends SimpleTagSupport {
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		buffer.append("<table class=\"color-table\">");
		buffer.append("<tr class=\"color-table-title\"><td colspan=\"6\">Primary palette</td></tr>");
		buffer.append("<tr><td><span class=\"color-info-choose color-palette-primary-blue\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-primary-brightblue\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-primary-paleblue\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-primary-green\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-primary-yellow\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-primary-red\"></span></td></tr>");
		
		buffer.append("<tr><td><span class=\"color-info-choose color-palette-primary-charcoal\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-primary-mediumgray\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-primary-lightgray\"></span></td><td colspan=\"3\"></td></tr>");
		
		buffer.append("<tr class=\"color-table-title\"><td colspan=\"6\">Secondary palette</td></tr>");
		buffer.append("<tr><td><span class=\"color-info-choose color-palette-secondary-gray\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-ashgray\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-silver\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-brown\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-orange\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-tan\"></span></td></tr>");

		buffer.append("<tr><td><span class=\"color-info-choose color-palette-secondary-lightbrown\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-lighterblue\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-slate\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-limegreen\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-emerald\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-violet\"></span></td></tr>");

		buffer.append("<tr><td><span class=\"color-info-choose color-palette-secondary-mauve\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-brightpink\"></span></td>");
		buffer.append("<td><span class=\"color-info-choose color-palette-secondary-pink\"></span></td><td colspan=\"3\"></td></tr>");
		
		
		buffer.append("</table>");
		out.println(buffer.toString());
	}
}
