package pack;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Picture {
	
	@Id
	private String namePicture;
	
	private String path;
	private Boolean isMeme;
	
	@ManyToMany
	private Collection<Tag> tags = new ArrayList<Tag>();
		
	public String getNamePicture() {
		return namePicture;
	}
	public void setNamePicture(String namePicture) {
		this.namePicture = namePicture;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Collection<Tag> getTags() {
		return tags;
	}
	public void setTags(Collection<Tag> tags) {
		this.tags = tags;
	}
	public Boolean getIsMeme() {
		return isMeme;
	}
	public void setIsMeme(Boolean isMeme) {
		this.isMeme = isMeme;
	}
	public String toString() {
		String str = namePicture;
		str += "\n\t path : " + path;
		str += "\n\t isMeme : " + isMeme;
		return str;
	}
}
