package mu.codeoffice.query;

import java.util.List;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.Type;

public class MySqlBitwiseOrSQLFunction extends StandardSQLFunction implements SQLFunction {
	
	public MySqlBitwiseOrSQLFunction(String name) {
		super(name);
	}

	public MySqlBitwiseOrSQLFunction(String name, Type typeValue) {
		super(name, typeValue);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String render(Type firstArgumentType, List args, SessionFactoryImplementor factory) throws QueryException {
		if (args.size() != 2) {
			throw new IllegalArgumentException("the function must be passed 2 arguments");
		}
		StringBuffer buffer = new StringBuffer();
		buffer.append(args.get(0)).append(" | ").append(args.get(1));
		return buffer.toString();
	}
}
