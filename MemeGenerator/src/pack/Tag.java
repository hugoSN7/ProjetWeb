package pack;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Tag {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	int idTag;
	private String mot;
	
	@ManyToMany(mappedBy = "tags")
	private Collection<Picture> pictures = new ArrayList<Picture>();

	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	public Collection<Picture> getPictures() {
		return pictures;
	}

	public void setPictures(Collection<Picture> pictures) {
		this.pictures = pictures;
	}

	public int getId() {
		return idTag;
	}

	public void setId(int id) {
		this.idTag = id;
	}
	
	public String toString() {
		String str = mot;
		return str;
	}
	
}
