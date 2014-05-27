package mu.codeoffice.query;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class GenericSpecifications {

	public static Pageable pageSpecification(Integer pageIndex, Integer pageSize, Sort sort) {
		if (pageIndex == null) {
			pageIndex = 0;
		} 
		if (pageSize == null) {
			pageSize = 0;
		}
		return new PageRequest(pageIndex, pageSize, sort);
	}
	
	public static Sort sort(boolean ascending, String column) {
		return new Sort(ascending ? Sort.Direction.ASC : Sort.Direction.DESC, column);
	}
	
}
