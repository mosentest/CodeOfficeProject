package mu.codeoffice.utility;

import java.util.List;

import mu.codeoffice.entity.AskQuestion;

public class Questions extends Page<AskQuestion> {
	
	public Questions(int count, int page, int size) {
		super(count, page, size);
	}
	
	public void setItems(List<AskQuestion> items) {
		this.items = items;
	}
	
	public List<AskQuestion> getItems() {
		return items;
	}
}
