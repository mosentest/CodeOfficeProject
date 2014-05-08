package mu.codeoffice.mapper;

import java.util.List;

import mu.codeoffice.entity.Tag;

public interface UtilMapper {

	public List<Tag> getTags(int offset, int size);
	
	public List<Tag> searchTags(String tag, int offset, int size);
	
	public Tag getTag(String tag);
	
	public int createTag(Tag tag);
	
	public boolean doesTagExist(String tag);
	
	public int increaseTagCount(long id);
}
