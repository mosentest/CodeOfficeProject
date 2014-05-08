package mu.codeoffice.utility;

import java.util.List;
public class Page<T> {
	
	/**
	 * Current page 
	 */
	protected int currentPage;
	/**
	 * List of items, stores generic typed data
	 */
	protected List<T> items;
	
	protected int totalPages;
	
	public Page(int count, int page, int size) {
		if (count <= 0) return;
		init(count, page, size);
	}
	
	private void init(int count, int page, int size) {
		currentPage = page + 1;
		if (count <= 0) 
			currentPage = 1;
		else {
			totalPages = count / size;
			if (count % size != 0) totalPages++;
			if (currentPage > totalPages) currentPage = totalPages;	
			if (currentPage < 1) currentPage = 1;
		}
	}
	
	public int getCurrentPage() {
		return currentPage;
	}
	
	public int getTotalPages() {
		return totalPages;
	}
	
}
