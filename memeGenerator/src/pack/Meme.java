package pack;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Meme {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	int id;
    private int likenb;
    @OneToMany(mappedBy="Image", fetch = FetchType.EAGER)
    private List<Comment> listComment;
    @OneToMany(mappedBy="Image", fetch = FetchType.EAGER)
    private List<Tag> listTag;
    
    @ManyToOne
	@JsonIgnore
	User user;

    public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
    public Meme(String nomImage, String imagePath, String pseudo){
        this.image = new Image(nomImage, imagePath);
        this.user = new User(pseudo);
        this.likenb = 0;
        this.listComment= new ArrayList<Comment>();
    }
    */

    public int getLikenb() {
        return likenb;
    }

    public void setLikenb(int likenb) {
        this.likenb = likenb;
    }

    public void addLike(){
        this.likenb = this.likenb + 1;
    }

    public List<Comment> getListComment() {
        return listComment;
    }

    public void setListComment(List<Comment> listComment) {
        this.listComment = listComment;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Tag> getListTag() {
		return listTag;
	}

	public void setListTag(List<Tag> listTag) {
		this.listTag = listTag;
	}
    
    
    
    /**
    public void addComment(Comment comment){
        this.listComment.add(comment);
    }

    public void removeComment(Comment comment){
        this.listComment.remove(comment);
    }
    */
}
