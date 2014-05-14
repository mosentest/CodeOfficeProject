package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mu.codeoffice.entity.EnterpriseUser;

public class UserTag extends SimpleTagSupport {

	private EnterpriseUser user;
	
	private boolean showImage = true;
	
	private boolean showLink = true;
	
	private boolean showSpace = true;
	
	private int width = 36;
	
	private int height = 36;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		
		if (user == null) {
			out.println("None");
			return;
		}
		
		StringBuilder buffer = new StringBuilder();
		if (showImage) {
			buffer.append("<span class=\"imglink\">");
		}
		if (showImage) {
			String imagePath = user.getProfilePath();
			if (imagePath.equals("male.jpg") || imagePath.equals("female.jpg")) {
				imagePath = "img/" + imagePath;
			}
			buffer.append(String.format("<span><img src=\"%s\" width=\"%d\" height=\"%d\"></span>", imagePath, width, height));
		}
		if (showSpace) {
			buffer.append("<span class=\"minorspace\">&nbsp;</span>");
		}
		if (showLink) {
			buffer.append(String.format("<a href=\"enterprise/user/%s\">%s</a>", user.getNameLink(), user.getFullName()));
		} else {
			buffer.append(String.format("<span>%s</span>", user.getFullName()));
		}
		if (showImage) {
			buffer.append("</span>");
		}
		
		out.println(buffer.toString());
	}

	public EnterpriseUser getUser() {
		return user;
	}

	public void setUser(EnterpriseUser user) {
		this.user = user;
	}

	public boolean isShowImage() {
		return showImage;
	}

	public void setShowImage(boolean showImage) {
		this.showImage = showImage;
	}

	public boolean isShowLink() {
		return showLink;
	}

	public void setShowLink(boolean showLink) {
		this.showLink = showLink;
	}

	public boolean isShowSpace() {
		return showSpace;
	}

	public void setShowSpace(boolean showSpace) {
		this.showSpace = showSpace;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
