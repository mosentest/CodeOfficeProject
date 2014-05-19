package mu.codeoffice.utility;

import java.beans.PropertyEditorSupport;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateEditor extends PropertyEditorSupport {
	
	private final SimpleDateFormat[] dateFormats = {
			new SimpleDateFormat("yyyy-MM-dd"),	
			new SimpleDateFormat("dd/MM/yyyy"),	
			new SimpleDateFormat("yyyyMMdd"),	
			new SimpleDateFormat("yyMMdd"),
			new SimpleDateFormat("yyyy/MM/dd")			
	};
	
	private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void setAsText(String value) {
		for (SimpleDateFormat dateFormat : dateFormats) {
			try {
				setValue(dateFormat.parse(value));
				return;
			} catch (ParseException e) {}
		}
		setValue(null);
	}

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return "";
		}
		return dateFormat.format(getValue());
	}

}
