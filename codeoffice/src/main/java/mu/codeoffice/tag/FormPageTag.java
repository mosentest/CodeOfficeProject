package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;

public class FormPageTag extends SimpleTagSupport {
	
	private static final String SEPARATOR = ",";
	
	private Page<?> page;
	
	private String url;
	
	private String params;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		
		buffer.append("<div class=\"form-page-holder\">");
		buffer.append("<div class=\"form-page-description\">");
		buffer.append("<span>Displaying items </span>");
		buffer.append("<span class=\"info-number\">" + (page.getNumber() * page.getSize() + 1) + "</span>");
		buffer.append("<span> to </span>");
		int upperLimit = (page.getNumber() + 1) * page.getSize();
		if (upperLimit > page.getTotalElements()) {
			upperLimit = (int) page.getTotalElements();
		}
		buffer.append("<span class=\"info-number\">" + upperLimit + "</span>");
		buffer.append("<span> of </span>");
		buffer.append("<span class=\"info-number\">" + page.getTotalElements() + "</span>");
		buffer.append("</div>");
		
		buffer.append("<div class=\"form-page-link\">");
		int totalPages = page.getTotalPages();
		for (int i = 1; i <= totalPages; i++) {
			buffer.append(String.format("<a class=\"link %s\" href=\"%s\">%d</a>", page.getNumber() + 1 == i ? "current-page" : "", getLink(i - 1), i));
		}
		if (page.getNumber() != totalPages - 1) {
			buffer.append("<a class=\"icon-module icon-module-next\" href=\"" + getLink(page.getNumber() + 1) + "\" title=\"Next Page\"></a>");
		}
		buffer.append("</div><div class=\"clearfix\"></div>");
		
		buffer.append("</div>");
		out.println(buffer.toString());
	}
	
	private String getLink(int pageIndex) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(url);
		buffer.append("?pageIndex=");
		buffer.append(pageIndex);
		if (params != null) {
			String[] params = this.params.split(SEPARATOR);
			for (String param : params) {
				if (param.trim().length() == 0) {
					continue;
				}
				buffer.append("&");
				buffer.append(param.trim());
			}
		}
		return buffer.toString();
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
