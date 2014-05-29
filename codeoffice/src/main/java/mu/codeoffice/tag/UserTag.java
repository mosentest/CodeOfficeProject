package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mu.codeoffice.entity.User;

public class UserTag extends SimpleTagSupport {

	private User user;
	
	private boolean showImage = true;
	
	private boolean showLink = true;
	
	private boolean showSpace = true;
	
	private int width = 24;
	
	private int height = 24;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		new UserRenderer(out).renderUser(user, showImage, showLink, showSpace, width, height);
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
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
