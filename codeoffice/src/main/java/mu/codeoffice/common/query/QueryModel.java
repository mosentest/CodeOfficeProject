package mu.codeoffice.common.query;

import java.util.ArrayList;
import java.util.List;

public class QueryModel {

	private List<Query> queries;
	
	public QueryModel(Query... queries) {
		this.queries = new ArrayList<Query>();
		for (Query e : queries)
			this.queries.add(e);
	}

	public List<Query> getQueries() {
		return queries;
	}
	
	public void prefer(Query query) {
		queries.add(0, query);
	}
	
	public void append(Query query) {
		queries.add(query);
	}
}
