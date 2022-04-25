package pack;

import java.util.Collection;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**@POST quand tu veux modifier un truc 
 * @GET quand tu veux juste afficher un truc*/

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
	
	@POST
	@Path("/addmeme")
    @Consumes({ "application/json" })
	public void addMeme(Image i, String texte, int x, int y) {
		//String paragraphe[] = texte.split(";");
		
		//lire l'image
	    BufferedImage image = null;
		try {
			image = ImageIO.read(new File(i.getImage()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    //récupérer l'objet Graphics
	    Graphics g = image.getGraphics();
	    //définir le font
	    g.setFont(g.getFont().deriveFont(25f));
	    //afficher le texte sur les coordonnées(x=50, y=150)
	    g.drawString(texte, x, y);
	    g.dispose();
	    
	    Meme m = new Meme();
	    String nomMeme = String.valueOf(m.getId())+".png";
	    m.setMemepath(nomMeme);
	    
	    //écrire l'image sous forme d'un fichier iddumeme.png
	    try {
			ImageIO.write(image, "png", new File(nomMeme));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
		System.out.println("Meme ajouté");
		em.persist(m);
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
