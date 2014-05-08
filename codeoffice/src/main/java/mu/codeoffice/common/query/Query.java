package mu.codeoffice.common.query;

public class Query {

	private String column;
	private String operator;
	private Object value;

	public Query(String column, Operator operator, Object value) {
		this.column = column;
		switch (operator) {
			case LT:
				this.operator = "<";
				break;
			case LE:
				this.operator = "<=";
				break;
			case EQ:
				this.operator = "=";
				break;
			case GE:
				this.operator = ">=";
				break;
			case GT:
				this.operator = ">";
				break;
			case NE:
				this.operator = "<>";
				break;
			case FQ:
				this.operator = "LIKE";
		}
		this.value = value;
	}

	public String getColumn() {
		return column;
	}

	public Object getValue() {
		return value;
	}
	
	public String getOperator() {
		return operator;
	}

}
