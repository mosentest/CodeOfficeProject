package mu.codeoffice.tag;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class SortableColumnTag extends SimpleTagSupport {
	
	private static final String SEPARATOR = ",";
	
	private String columnName;
	
	private String sortColumn;

	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		String params = (String) getJspContext().getAttribute("params", PageContext.PAGE_SCOPE);
		String url = (String) getJspContext().getAttribute("url", PageContext.PAGE_SCOPE);
		String sort = null;
		boolean descending = false;
		Object o = getJspContext().getAttribute("sort", PageContext.REQUEST_SCOPE);
		if (o != null) {
			sort = (String) o;
		}
		o = getJspContext().getAttribute("descending", PageContext.REQUEST_SCOPE);
		if (o != null) {
			descending = true;
		}
		
		boolean appendOrder = !descending && sortColumn.equals(sort);
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<a class=\"sortable-column-header " + (sortColumn.equals(sort) ? "sortable-column-header-current" : "") 
				+ " imglink\" href=\"" + getLink(url, params, appendOrder) + "\"><span>" + columnName);
		if (appendOrder) {
			buffer.append("</span><img src=\"assets/img/forms/icon-ascending.png\"/></a>");
		} else {
			buffer.append("<img src=\"assets/img/forms/icon-descending.png\"/></a>");
		}
		out.println(buffer.toString());
	}
	
	private String getLink(String url, String params, boolean appendOrder) {
		StringBuilder buffer = new StringBuilder();
		buffer.append(url);
		buffer.append("?");
		if (params != null) {
			String[] paramArray = params.split(SEPARATOR);
			for (String param : paramArray) {
				if (param.trim().length() == 0) {
					continue;
				}
				buffer.append("&");
				buffer.append(param.trim());
			}
		}
		if (appendOrder) {
			buffer.append("&descending=true");
		}
		buffer.append("&sort=" + sortColumn);
		return buffer.toString();
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getSortColumn() {
		return sortColumn;
	}

	public void setSortColumn(String sortColumn) {
		this.sortColumn = sortColumn;
	}

}
