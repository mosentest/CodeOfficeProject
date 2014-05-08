package mu.codeoffice.tag;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mu.codeoffice.utility.Page;

public class PageTag extends SimpleTagSupport {

	private String link;
	private Integer limit = 20;
	private Page<?> page;
	private Boolean hideOnSingle = true;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		StringBuilder builder = new StringBuilder();
		builder.append("<div class=\"page_holder\">");
		if (page.getTotalPages() > 1 || (page.getTotalPages() <=1 && !hideOnSingle)) {
			if (page.getTotalPages() <= limit) {
				for (int i = 1; i <= page.getTotalPages(); i++) {
					builder.append(String.format("<div class=\"page_item%s\">", i == page.getCurrentPage() ? " current_page" : ""));
					if (link == null) {
						builder.append(String.format("<a href=\"javascript:void(0);\">%d</a>", i));
					} else {
						builder.append(String.format("<a href=\"%s\">%d</a></div>", link + "/" + (i - 1), i));
					}	
				}
			} else {
				List<Integer> pageBuilder = new ArrayList<Integer>();
				int offset = page.getCurrentPage() / 5;
				if (page.getCurrentPage() != 1) {
					pageBuilder.add(1);
				}
				//building pages before current page
				for (int i = 1; i <= offset; i++) {
					if (i == offset) {
						pageBuilder.add(0);
					}
					pageBuilder.add(i * 5);
				}
				//
				//building pages around current page
				for (int i = offset * 5 + 1; i % 5 != 0; i++) {
					pageBuilder.add(i);
					if (i == page.getTotalPages()) {
						break;
					}
				}
				//building pages after current page
				if (pageBuilder.get(pageBuilder.size() - 1) != page.getTotalPages()) {
					for (int i = offset + 1; i <= page.getTotalPages() / 5; i++) {
						pageBuilder.add(i * 5);
					}
					if (page.getTotalPages() % 5 == 1) {
						pageBuilder.add(page.getTotalPages());
					} else if (page.getTotalPages() % 5 > 1) {
						pageBuilder.add(0);
						pageBuilder.add(page.getTotalPages());
					}
				}
				
				
				//printing result
				for (int i : pageBuilder) {
					if (i == 0) {
						builder.append("<div class=\"page_item\"><a href=\"javascript:void(0);\">...</a></div>");
					} else {
						builder.append(String.format("<div class=\"page_item%s\">", i == page.getCurrentPage() ? " current_page" : ""));
						if (link == null) {
							builder.append(String.format("<a href=\"javascript:void(0);\">%d</a>", i));
						} else {
							builder.append(String.format("<a href=\"%s\">%d</a></div>", link + "/" + (i - 1), i));
						}
					}
				}
			}
		}
		
		builder.append("</div>");
		out.println(builder.toString());
	}

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public Boolean getHideOnSingle() {
		return hideOnSingle;
	}

	public void setHideOnSingle(Boolean hideOnSingle) {
		this.hideOnSingle = hideOnSingle;
	}
	
}
