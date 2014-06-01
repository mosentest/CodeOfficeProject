package mu.codeoffice.dto;

public interface DataTransferObject<T> {

	public T toObject(DataTransferObject<T> dto);
	
	public DataTransferObject<T> toDTO(T object);
	
	public boolean validate();
	
}
