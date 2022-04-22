package pack;

import java.util.ArrayList;
import java.util.List;

public class Image {

    private List<Tag> listTag;
    private String nom;
    private String imagePath;

    public Image(String nom, String imagePath){
        this.listTag = new ArrayList<Tag>();
        this.nom = nom;
        this.imagePath = imagePath;
    }

    public List<Tag> getListTag() {
        return listTag;
    }

    public void setListTag(List<Tag> listTag) {
        this.listTag = listTag;
    }

    public void addTag(Tag tag){
        this.listTag.add(tag);
    }

    public void removeTag(Tag tag){
        this.listTag.remove(tag);
    }

    public String getNom() {
        return nom;
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
    
}
