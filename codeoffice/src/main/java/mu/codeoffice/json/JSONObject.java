package mu.codeoffice.json;

public interface JSONObject<T> {

	public T toObject();
	
	public JSONObject<T> toJSONObject(T object);
	
}
