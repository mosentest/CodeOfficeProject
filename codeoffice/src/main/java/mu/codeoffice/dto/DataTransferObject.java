package mu.codeoffice.dto;

public interface DataTransferObject<T> {

	public T buildObject(DataTransferObject<T> dto);
	
	public DataTransferObject<T> buildDTO(T object);
	
}
