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
public class Tag {
	
	@Id
	private String mot;
	
	@ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Collection<Picture> pictures;

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
	
	public String toString() {
		String str = mot;
		if (pictures != null) {
			for (Picture p : pictures) {
				str += "\n\t picture : " + p.toString() + "\n";
			}
		}
		return str;
	}
	
}