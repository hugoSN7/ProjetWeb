package pack;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Image {

	@Id
	private String nom;
    private String imagePath;
    @OneToMany(mappedBy="Image", fetch = FetchType.EAGER)
    private List<Tag> listTag;
    
    @OneToOne(mappedBy = "image")
    Brouillon brouillon;

	@ManyToOne
	@JsonIgnore
	User user;

    public List<Tag> getListTag() {
        return listTag;
    }

    public void setListTag(List<Tag> listTag) {
        this.listTag = listTag;
    }

    /**
    public void addTag(Tag tag){
        this.listTag.add(tag);
    }

    public void removeTag(Tag tag){
        this.listTag.remove(tag);
    }
    */

    public String getNom() {
        return nom;
    }

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setNom(String nom) {
        this.nom = nom;
    }

    public String getImage() {
        return imagePath;
    }

    public void setImage(String image) {
        this.imagePath = image;
    }
    
    
    public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public Brouillon getBrouillon() {
		return brouillon;
	}

	public void setBrouillon(Brouillon brouillon) {
		this.brouillon = brouillon;
	}
}
