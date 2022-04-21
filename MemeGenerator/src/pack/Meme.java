package classes;

import java.util.ArrayList;
import java.util.List;

public class Meme {

    private Image image;
    private User user;
    private int likenb;
    private List<Comment> listComment;

    public Meme(String nomImage, String imagePath, String pseudo){
        this.image = new Image(nomImage, imagePath);
        this.user = new User(pseudo);
        this.likenb = 0;
        this.listComment= new ArrayList<Comment>();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

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
    
    public void addComment(Comment comment){
        this.listComment.add(comment);
    }

    public void removeComment(Comment comment){
        this.listComment.remove(comment);
    }
}
