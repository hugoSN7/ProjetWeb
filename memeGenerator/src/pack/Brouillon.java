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
public class Brouillon {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	int id;
	@OneToMany(mappedBy="Image", fetch = FetchType.EAGER)
    private List<Texte> listTexte;
	//manque un OneToOne
	private Image image;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<Texte> getListTexte() {
		return listTexte;
	}
	public void setListTexte(List<Texte> listTexte) {
		this.listTexte = listTexte;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
}