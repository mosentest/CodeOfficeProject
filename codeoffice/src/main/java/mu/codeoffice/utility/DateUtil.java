package mu.codeoffice.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtil {

	private static final SimpleDateFormat dateFormat;
	
	private static final SimpleDateFormat datetimeFormat;
	
	private static final Random random;
	
	public static final int MINUTE_ = 60;
	
	public static final int HOUR_ = 3600;
	
	public static final int DAY_ = 86400;
	
	public static final int WEEK_ = 604800;
	
	public static final int MONTH_ = 2592000;
	
	public static final int YEAR_ = 31536000;
	
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
	
	static {
		dateFormat = new SimpleDateFormat("yy-MM-dd");
		datetimeFormat = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		random = new Random();
	}
	
	public static String getTimeString(Date date, String format) {
		Date current = new Date();
		SimpleDateFormat dateFormat = null;
		if (date == null) {
			return "None";
		}
		long diff = (current.getTime() - date.getTime()) / DIFF_M;
		if (format == null) {
			dateFormat = datetimeFormat;
		} else {
			dateFormat = new SimpleDateFormat(format);
		}
		String stringFormat = "%d %s ago";
		
		if (diff >= MONTH_DIFF * 4) {
			return dateFormat.format(date);
		} else if (diff >= MONTH_DIFF * 2) {
			return String.format(stringFormat, diff / MONTH_DIFF, MONTHS);
		} else if (diff >= MONTH_DIFF) {
			return String.format(stringFormat, 1, MONTH);
		} else if (diff >= WEEK_DIFF * 2) {
			return String.format(stringFormat, diff / WEEK_DIFF, WEEKS);
		} else if (diff >= WEEK_DIFF) {
			return String.format(stringFormat, 1, WEEK);
		} else if (diff >= DAY_DIFF * 2) {
			return String.format(stringFormat, diff / DAY_DIFF, DAYS);
		} else if (diff >= DAY_DIFF) {
			return String.format(stringFormat, 1, DAY);
		} else if (diff >= HOUR_DIFF * 2) {
			return String.format(stringFormat, diff / HOUR_DIFF, HOURS);
		} else if (diff >= HOUR_DIFF) {
			return String.format(stringFormat, 1, HOUR);
		} else if (diff >= MINUTE_DIFF * 2) {
			return String.format(stringFormat, diff / MINUTE_DIFF, MINUTES);
		} else if (diff >= MINUTE_DIFF) {
			return String.format(stringFormat, 1, MINUTE);
		} else {
			return RECENT;
		}
	}
	
	public static String getDateFormat(Date date) {
		return dateFormat.format(date);
	}
	
	public static boolean isSameDay(Date d1, Date d2) {
		if (d1 == null || d2 == null) {
			return false;
		}
		Calendar c1 = getCalendar(d1);
		Calendar c2 = getCalendar(d2);
		return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR) &&
				c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR);
	}
	
	public static Date getRandomDateInRange(String start, String end) {
		try {
			Date d1 = dateFormat.parse(start);
			Date d2 = dateFormat.parse(end);
			return new Date(d1.getTime() + random.nextInt((int) (d2.getTime() - d1.getTime())));
		} catch (Exception e) {
			return null;
		}
	}
	
	public static Date addRandomDate(Date date, int seconds) {
		return new Date(date.getTime() + random.nextInt(seconds * 1000));
	}
	
	private static Calendar getCalendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	
	
}

