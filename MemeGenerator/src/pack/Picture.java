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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Picture {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	private int idPicture;
	
	private String namePicture;
	
	private String path;
	private Boolean isMeme;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Pictures_Tags",
            joinColumns = {@JoinColumn(name = "idPicture")},
            inverseJoinColumns = {@JoinColumn(name = "mot")}
    )
	private Collection<Tag> tags;
	
	@ManyToOne
	private User Owner;
		
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
	public int getIdPicture() {
		return idPicture;
	}
	public void setIdPicture(int idPicture) {
		this.idPicture = idPicture;
	}
	public String toString() {
		String str = namePicture;
		str += "\n\t path : " + path;
		str += "\n\t isMeme : " + isMeme;
		if (tags != null) {
			for (Tag t : tags) {
				str += "\n\t tag : " + t.getMot();
			}
		}
		return str;
	}
  
	public User getOwner() {
		return Owner;
	}
	public void setOwner(User owner) {
		Owner = owner;
	}
	
	@OneToMany(mappedBy="meme")
    private Collection<Comment> comments;
	
	public Collection<Comment> getComments() {
		return comments;
	}
	public void setComments(Collection<Comment> comments) {
		this.comments = comments;
	}
	
}
