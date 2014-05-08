package mu.codeoffice.common.query;

public class Order {

	public static final String ASCENDING = "ASC";
	public static final String DESCENDING = "DESC";
	
	private final String column;
	private final String order;
	
	public Order(String column, String order) {
		this.column = column;
		this.order = order;
	}

	public String getOrder() {
		return order;
	}

	public String getColumn() {
		return column;
	}
}
