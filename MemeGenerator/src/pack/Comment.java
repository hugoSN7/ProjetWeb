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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Comment {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	private int idComment;
	
	private String content;
	
	@ManyToOne
	private User writer;
	
	
	private String pseudo;
	
	@ManyToOne
	private Picture meme;
	

	public Comment(String string, User writer2, Picture meme2) {
		writer = writer2;
		meme = meme2;
		content = string;
		pseudo = writer2.getPseudo();
	}

	public int getIdComment() {
		return idComment;
	}

	public void setIdComment(int idComment) {
		this.idComment = idComment;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public User getWriter() {
		return writer;
	}

	public void setWriter(User writer) {
		this.writer = writer;
	}

	public Picture getMeme() {
		return meme;
	}

	public void setMeme(Picture meme) {
		this.meme = meme;
	}
	

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}


	
	

}
