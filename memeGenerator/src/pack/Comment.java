package pack;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	int id;
    private List<Comment> listComment;
    private String message;
    
    @ManyToOne
	@JsonIgnore
	Meme meme;

    /**
    public Comment(String pseudo){
        this.listComment = new ArrayList<Comment>();
        this.user = new User(pseudo);
    }
    */

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

	public Meme getMeme() {
		return meme;
	}

	public void setMeme(Meme meme) {
		this.meme = meme;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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