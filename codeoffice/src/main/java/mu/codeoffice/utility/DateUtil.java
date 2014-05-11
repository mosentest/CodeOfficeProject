package mu.codeoffice.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

public class DateUtil {

	private static final SimpleDateFormat dateFormat;
	
	@SuppressWarnings("unused")
	private static final SimpleDateFormat datetimeFormat;
	
	private static final Random random;
	
	public static final int MINUTE = 60;
	
	public static final int HOUR = 3600;
	
	public static final int DAY = 86400;
	
	public static final int WEEK = 604800;
	
	public static final int MONTH = 2592000;
	
	public static final int YEAR = 31536000;
	
	static {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		datetimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		random = new Random();
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

