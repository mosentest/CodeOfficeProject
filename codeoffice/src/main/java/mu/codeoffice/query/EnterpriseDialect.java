package mu.codeoffice.query;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.type.StandardBasicTypes;

public class EnterpriseDialect extends MySQL5Dialect {

	public EnterpriseDialect() {
		super();
		registerFunction("bitwise_and", new MySqlBitwiseAndSQLFunction("bitwise_and", StandardBasicTypes.INTEGER));
		registerFunction("bitwise_or", new MySqlBitwiseOrSQLFunction("bitwise_or", StandardBasicTypes.INTEGER));
	}
	
}
