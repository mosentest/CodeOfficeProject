package mu.codeoffice.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import mu.codeoffice.data.Summary;

public class JsSummaryTag extends SimpleTagSupport {
	
	private List<Summary> summary;
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();

		StringBuilder buffer = new StringBuilder();
		if (summary == null) {
			out.println("");
			return;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MMM");
		for (int i = 0; i < summary.size(); i++) {
			Summary s = summary.get(i);
			buffer.append(String.format("['%s','", dateFormat.format(s.getDate())));
			if (s.isVersionStart()) {
				buffer.append(s.getVersionCode());
				buffer.append(" - start");
			} else if (s.isVersionRelease()) {
				buffer.append(s.getVersionCode());
				buffer.append(" - releasde");
			} else if (s.isVersionDelay()) {
				buffer.append(s.getVersionCode());
				buffer.append(" - delayed");
			}
			buffer.append(String.format("',%d,%d]", s.getNoCount(), s.getNoResolved()));
			if (i != summary.size() - 1) {
				buffer.append(",");
			}
		}
		out.println(buffer.toString());
	}

	public List<Summary> getSummary() {
		return summary;
	}

	public void setSummary(List<Summary> summary) {
		this.summary = summary;
	}
}
