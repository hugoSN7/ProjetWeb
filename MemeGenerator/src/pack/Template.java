package pack;

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

@Entity
public class Template {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	private int idTemplate;
	
	private String namePicture;
	
	private String path;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Templates_Tags",
            joinColumns = {@JoinColumn(name = "idTemplate")},
            inverseJoinColumns = {@JoinColumn(name = "mot")}
    )
	private Collection<Tag> tags;
		
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

	public String toString() {
		String str = namePicture;
		str += "\n\t path : " + path;
		if (tags != null) {
			for (Tag t : tags) {
				str += "\n\t tag : " + t.getMot();
			}
		}
		return str;
	}
}
