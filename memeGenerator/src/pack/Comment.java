package pack;

import java.util.ArrayList;
import java.util.List;

public class Comment {

    private List<Comment> listComment;
    private Meme meme;
    private User user;

    public Comment(String pseudo){
        this.listComment = new ArrayList<Comment>();
        this.user = new User(pseudo);
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

    public Meme getMeme() {
        return meme;
    }

    public void setMeme(Meme meme) {
        this.meme = meme;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}