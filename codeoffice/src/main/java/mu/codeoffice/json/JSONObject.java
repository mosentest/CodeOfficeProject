package mu.codeoffice.json;

public interface JSONObject<T> {

	public T toObject(JSONObject<T> json);
	
	public JSONObject<T> toJSONObject(T object);
	
}
