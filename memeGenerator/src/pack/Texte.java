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
public class Texte {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	int id;
	int x;
	int y;
	String phrase;
	
	@ManyToOne
	@JsonIgnore
	Brouillon brouillon;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getPhrase() {
		return phrase;
	}

	public void setPhrase(String phrase) {
		this.phrase = phrase;
	}

	public Brouillon getBrouillon() {
		return brouillon;
	}

	public void setBrouillon(Brouillon brouillon) {
		this.brouillon = brouillon;
	}
	
}