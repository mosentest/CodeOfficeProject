package mu.codeoffice.tag;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.jsp.JspException;

import mu.codeoffice.entity.EnterpriseUser;

public class UserRenderer {

	private Writer writer;
	
	public UserRenderer(Writer writer) {
		this.writer = writer;
	}
	
	public void renderUser(EnterpriseUser user, boolean showImage, 
			boolean showLink, boolean showSpace, int width, int height) throws JspException, IOException {
		if (user == null) {
			writer.write("None");
			return;
		}
		
		StringBuilder buffer = new StringBuilder();
		if (showImage) {
			buffer.append("<span class=\"imglink\">");
		}
		if (showImage) {
			String imagePath = user.getProfilePath();
			if (imagePath == null) {
				imagePath = "assets/img/core/default-avatar.png";
			}
			buffer.append(String.format("<span><img src=\"%s\" width=\"%d\" height=\"%d\"></span>", imagePath, width, height));
		}
		if (showSpace) {
			buffer.append("<span class=\"minorspace\">&nbsp;</span>");
		}
		if (showLink) {
			buffer.append(String.format("<a class=\"link\" href=\"enterprise/user/%s\">%s</a>", user.getNameLink(), user.getFullName()));
		} else {
			buffer.append(String.format("<span>%s</span>", user.getFullName()));
		}
		if (showImage) {
			buffer.append("</span>");
		}
		
		writer.write(buffer.toString());
	}
	
}
