package pack;

import java.io.Serializable;

public class Associate implements Serializable {

	String userId;
	String imageId;
	int memeId;
	String TagId;
	int commentId;
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String personId) {
		this.userId = personId;
	}
	public String getImageId() {
		return imageId;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public int getMemeId() {
		return memeId;
	}
	public void setMemeId(int memeId) {
		this.memeId = memeId;
	}
	public String getTagId() {
		return TagId;
	}
	public void setTagId(String tagId) {
		TagId = tagId;
	}
	public int getCommentId() {
		return commentId;
	}
	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}
	
}
