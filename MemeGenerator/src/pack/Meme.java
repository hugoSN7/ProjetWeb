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
public class Meme {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	private int idMeme;
	
	private String namePicture;
	
	private String path;
	private Boolean isMeme;
	
	@ManyToOne
	private User Owner;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "Memes_Categories",
            joinColumns = {@JoinColumn(name = "idMeme")},
            inverseJoinColumns = {@JoinColumn(name = "mot")}
    )
	private Collection<Category> categories;
		
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
	
	public Collection<Category> getTags() {
		return categories;
	}
	
	public void setTags(Collection<Category> tags) {
		this.categories = tags;
	}
	
	public Boolean getIsMeme() {
		return isMeme;
	}
	
	public void setIsMeme(Boolean isMeme) {
		this.isMeme = isMeme;
	}
	
	public int getIdPicture() {
		return idMeme;
	}
	
	public void setIdPicture(int idPicture) {
		this.idMeme = idPicture;
	}
	
	public String toString() {
		String str = namePicture;
		str += "\n\t path : " + path;
		str += "\n\t isMeme : " + isMeme;
		if (categories != null) {
			for (Category t : categories) {
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
