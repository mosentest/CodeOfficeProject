package mu.codeoffice.utility;

import java.beans.PropertyEditorSupport;

import mu.codeoffice.security.ProjectPermission;

public class ProjectPermissionEditor extends PropertyEditorSupport {

	@Override
	public void setAsText(String value) {
		if (value == null) {
			setValue(null);
			return;
		}
		setValue(ProjectPermission.valueOf(value));
	}

	@Override
	public String getAsText() {
		if (getValue() == null) {
			return "";
		}
		return getValue().toString();
	}
}
