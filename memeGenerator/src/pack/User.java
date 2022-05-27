package pack;

import java.util.*;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	private String pseudo;
    private String password;
    private String email;

    /**
    public User(String pseudo, String password, String email){
        this.listMeme = new ArrayList<Meme>();
        this.listImage = new ArrayList<Image>();
        this.pseudo = pseudo;
        this.password = password;
        this.email = email;
    }
    */

    /**
    public User(String pseudo){
        this.listMeme = new ArrayList<Meme>();
        this.listImage = new ArrayList<Image>();
        this.pseudo = pseudo;
    }
    */

    /**
    public void addMeme(Meme meme){
        this.listMeme.add(meme);
    }

    public void removeComment(Meme meme){
        this.listMeme.remove(meme);
    }
    */

    /**
    public void addImage(Image image){
        this.listImage.add(image);
    }

    public void removeImage(Image image){
        this.listImage.remove(image);
    }
    */

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}