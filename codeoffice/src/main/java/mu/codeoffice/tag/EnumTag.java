package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mu.codeoffice.enums.ImageEnum;
import mu.codeoffice.enums.TextEnum;
import mu.codeoffice.enums.TextImageEnum;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class EnumTag extends SimpleTagSupport {
	
	private MessageSource messageSource;

	private TextEnum text;

	private ImageEnum image;

	private TextImageEnum value;
	
	public EnumTag() {
		super();
	}

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		if (messageSource == null) {
			ServletContext servletContext = ((PageContext) this.getJspContext()).getServletContext();
			WebApplicationContext applicationContext = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
			messageSource = (MessageSource) applicationContext.getBean("messageSource");
		}

		StringBuilder buffer = new StringBuilder();
		if (value != null) {
			buffer.append(getImageString());
			buffer.append(getTextString(false));
		} else if (image != null) {
			buffer.append(getImageString());
		} else if (text != null) {
			buffer.append(getTextString(image == null));
		}
		out.println(buffer.toString());
	}
	
	private String getImageString() {
		return "<span class=\"imglink\"><img src=\"img/" + (value == null ? image.getImagePath() : value.getImagePath()) + ".png\"/>";
	}
	
	private String getTextString(boolean textOnly) {
		String text = messageSource.getMessage(value == null ? this.text.getCode() : value.getCode(), null, LocaleContextHolder.getLocale());
		if (textOnly) {
			return "<span >" + text + "</span></span>";
		} else {
			return "<span style=\"margin-left: 5px;\">" + text + "</span></span>";
		}
	}

	public ImageEnum getImage() {
		return image;
	}

	public void setImage(ImageEnum image) {
		this.image = image;
	}

	public TextImageEnum getValue() {
		return value;
	}

	public void setValue(TextImageEnum value) {
		this.value = value;
	}

	public TextEnum getText() {
		return text;
	}

	public void setText(TextEnum text) {
		this.text = text;
	}
}
