package mu.codeoffice.entity;

import mu.codeoffice.json.JSONObject;

public interface JSONSerializable<T> {

	public JSONObject<T> toJSONObject();
	
}
