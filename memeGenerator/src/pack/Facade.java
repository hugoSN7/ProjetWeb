package pack;

import java.util.Collection;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Singleton
@Path("/")
public class Facade {

	@PersistenceContext
	EntityManager em;
	
	@POST
	@Path("/adduser")
    @Consumes({ "application/json" })
	public void addUser(User u) {
		System.out.println("Utilisateur ajouté");
		em.persist(u);
	}
	
	@POST
	@Path("/addimage")
    @Consumes({ "application/json" })
	public void addImage(Image i) {
		System.out.println("Image ajouté");
		em.persist(i);
	}
	
	@GET
	@Path("/listimage")
    @Produces({ "application/json" })
	public Collection<Image> listImage() {
		return em.createQuery("from Image", Image.class).getResultList();
	}
	
	@GET
	@Path("/listuser")
    @Produces({ "application/json" })
	public Collection<User> listUser() {
		return em.createQuery("from User", User.class).getResultList();	
	}
	
	@GET
	@Path("/listmeme")
    @Produces({ "application/json" })
	public Collection<Meme> listMeme() {
		return em.createQuery("from Meme", Meme.class).getResultList();	
	}
	
	@POST
	@Path("/associateuserimage")
    @Consumes({ "application/json" })
	public void associateUserImage(Associate as) {
		System.out.println(as.getUserId() +" "+ as.getImageId());
		User u = em.find(User.class, as.getUserId());
		Image i = em.find(Image.class, as.getImageId());
		i.setUser(u);
	}
	
	@POST
	@Path("/associateusermeme")
    @Consumes({ "application/json" })
	public void associateUserMeme(Associate as) {
		System.out.println(as.getUserId() +" "+ as.getMemeId());
		User u = em.find(User.class, as.getUserId());
		Meme m = em.find(Meme.class, as.getMemeId());
		m.setUser(u);
	}
	
	@POST
	@Path("/associatememecomment")
    @Consumes({ "application/json" })
	public void associateMemeComment(Associate as) {
		System.out.println(as.getMemeId() +" "+ as.getCommentId());
		Meme m = em.find(Meme.class, as.getMemeId());
		Comment c = em.find(Comment.class, as.getCommentId());
		c.setMeme(m);
	}
	
	@POST
	@Path("/associatememetag")
    @Consumes({ "application/json" })
	public void associateMemeTag(Associate as) {
		System.out.println(as.getMemeId() +" "+ as.getTagId());
		Meme m = em.find(Meme.class, as.getMemeId());
		Tag t = em.find(Tag.class, as.getTagId());
		t.setMeme(m);
	}
	@POST
	@Path("/associateimagetag")
    @Consumes({ "application/json" })
	public void associateImageTag(Associate as) {
		System.out.println(as.getImageId() +" "+ as.getTagId());
		Image i = em.find(Image.class, as.getImageId());
		Tag t = em.find(Tag.class, as.getTagId());
		t.setImage(i);
	}
}
