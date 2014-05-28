package mu.codeoffice.utility;

import java.beans.PropertyEditorSupport;

import mu.codeoffice.security.GlobalPermission;

public class GlobalPermissionEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String value) {
		if (value == null) {
			setValue(null);
			return;
		}
		setValue(GlobalPermission.valueOf(value.toUpperCase()));
	}

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return "";
		}
		return getValue().toString();
	}
}
