package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class ImageLinkTag extends SimpleTagSupport {
	
	private String image;
	
	private String text;
	
	private String link;
	
	private boolean border = true;
	
	private int width;
	
	private String style = "fw-b";
	
	private static final String borderClass = "border";
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		buffer.append(String.format("<a class=\"imglink action-link %s %s\" style=\"%s\" href=\"%s\"><img src=\"assets/img/%s.png\"><span>%s</span></a>", 
				style, border ? borderClass : "", width > 0 ? "width: " + width + "px;" : "", link, image, text));
		out.println(buffer.toString());
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public boolean isBorder() {
		return border;
	}

	public void setBorder(boolean border) {
		this.border = border;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
