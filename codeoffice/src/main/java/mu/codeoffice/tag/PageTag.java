package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.springframework.data.domain.Page;

public class PageTag extends SimpleTagSupport {
	
	private Page<?> page;
	
	private String url;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		
		buffer.append("<div class=\"page-holder\">");
		buffer.append("<span>Total <span class=\"fc-hb fw-b\">" + page.getTotalElements() + "</span> items</span><span class=\"minorspace\"></span>");
		if (page.getTotalPages() > 1) {
			buffer.append("<span><a href=\"" + url + "\">First</span><span class=\"minorspace\"></span>");
			if (page.isFirstPage()) {
				buffer.append("<span>Previous</span><span class=\"minorspace\"></span>");
			} else {
				buffer.append(String.format("<span><a href=\"%s/%d\">Previous</a></span><span class=\"minorspace\"></span>", url, page.getNumber()));
			}
			buffer.append(String.format("<span>%d / %d</span><span class=\"minorspace\"></span>", (page.getNumber() + 1), page.getTotalPages()));
			if (page.isLastPage()) {
				buffer.append("<span>Next</span>");
			} else {
				buffer.append(String.format("<span><a href=\"%s/%d\">Next</a></span><span class=\"minorspace\"></span>", url, page.getNumber()));
			}
			buffer.append(String.format("<span><a href=\"%s/%d\">Last</a></span><span class=\"minorspace\"></span>", url, page.getTotalPages()));
			buffer.append("<span><span>Goto</span><span class=\"minorspace\"></span><input type=\"text\" id=\"goto\" style=\"width: 36px;\" maxlength=\"3\"/>");
			buffer.append("<span class=\"minorspace\"></span><input class=\"button\" value=\"Go\" style=\"width: 50px;\" onclick=\"submitGoto();\">");
		}
		buffer.append("</div>");
		out.println(buffer.toString());
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
}
