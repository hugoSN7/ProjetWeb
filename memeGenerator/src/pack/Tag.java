package pack;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tag {
    
	@Id
    private String mot;
	
	@ManyToOne
	@JsonIgnore
	Meme meme;
	
	@ManyToOne
	@JsonIgnore
	Image image;

	/**
    public Tag(String mot){
        this.mot = mot;
    }
    */

    public String getMot() {
        return mot;
    }

    public Meme getMeme() {
		return meme;
	}

	public void setMeme(Meme meme) {
		this.meme = meme;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setMot(String mot) {
        this.mot = mot;
    }

}
