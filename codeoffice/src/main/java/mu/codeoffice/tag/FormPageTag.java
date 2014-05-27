package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;

public class FormPageTag extends SimpleTagSupport {
	
	private Page<?> page;

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
			buffer.append(String.format("<a class=\"link %s\" href=\"javascript:void(0);\" onclick=\"gotoPage(this, %d)\">%d</a>", page.getNumber() + 1 == i ? "current-page" : "", i - 1, i));
		}
		if (page.getNumber() != totalPages - 1) {
			buffer.append("<a class=\"icon-module icon-module-next\" href=\"javascript:void(0);\" onclick=\"gotoPage(this, " + (page.getNumber() + 1) + ")\" title=\"Next Page\"></a>");
		}
		buffer.append("</div><div class=\"clearfix\"></div>");
		
		buffer.append("</div>");
		out.println(buffer.toString());
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}
}
