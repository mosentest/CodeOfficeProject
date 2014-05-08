package mu.codeoffice.tag;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class DateTag extends SimpleTagSupport {
	
	private Date date;
	private String format = "yy-MM-dd HH:mm:ss";
	
	private static final int DIFF_M = 60000;

	private static final String MONTH = "month";
	private static final String MONTHS = "months";
	private static final String DAY = "day";
	private static final String DAYS = "days";
	private static final String WEEK = "week";
	private static final String WEEKS = "weeks";
	private static final String HOUR = "hour";
	private static final String HOURS = "hours";
	private static final String MINUTE = "minute";
	private static final String MINUTES = "minutes";
	private static final String RECENT = "recently";
	
	private static final int MINUTE_DIFF = 1;
	private static final int HOUR_DIFF = 60;
	private static final int DAY_DIFF = 1440;
	private static final int WEEK_DIFF = 10080;
	private static final int MONTH_DIFF = 43200;
	
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		Date current = new Date();
		long diff = (current.getTime() - date.getTime()) / DIFF_M;
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String stringFormat = "%d %s ago";
		
		if (diff >= MONTH_DIFF * 4) {
			out.println(dateFormat.format(date));
		} else if (diff >= MONTH_DIFF * 2) {
			out.println(String.format(stringFormat, diff / MONTH_DIFF, MONTHS));
		} else if (diff >= MONTH_DIFF) {
			out.println(String.format(stringFormat, 1, MONTH));
		} else if (diff >= WEEK_DIFF * 2) {
			out.println(String.format(stringFormat, diff / WEEK_DIFF, WEEKS));
		} else if (diff >= WEEK_DIFF) {
			out.println(String.format(stringFormat, 1, WEEK));
		} else if (diff >= DAY_DIFF * 2) {
			out.println(String.format(stringFormat, diff / DAY_DIFF, DAYS));
		} else if (diff >= DAY_DIFF) {
			out.println(String.format(stringFormat, 1, DAY));
		} else if (diff >= HOUR_DIFF * 2) {
			out.println(String.format(stringFormat, diff / HOUR_DIFF, HOURS));
		} else if (diff >= HOUR_DIFF) {
			out.println(String.format(stringFormat, 1, HOUR));
		} else if (diff >= MINUTE_DIFF * 2) {
			out.println(String.format(stringFormat, diff / MINUTE_DIFF, MINUTES));
		} else if (diff >= MINUTE_DIFF) {
			out.println(String.format(stringFormat, 1, MINUTE));
		} else {
			out.println(RECENT);
		}
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

}
