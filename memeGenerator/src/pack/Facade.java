package pack;

import java.util.Collection;
import java.util.List;
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
		System.out.println("Image ajoutée");
		em.persist(i);
	}
	
	@POST
	@Path("/addcomment")
    @Consumes({ "application/json" })
	public void addComment(Comment c) {
		System.out.println("Commentaire ajoutée");
		em.persist(c);
	}
	
	@POST
	@Path("/addmeme")
    @Consumes({ "application/json" })
	public void addMeme(Image i, List listeTexte) throws IOException {
		
		//On crée l'objet Meme
		Meme m = new Meme();
		//On crée l'objet BufferedImage
		BufferedImage image = null;
		image = ImageIO.read(new File(i.getImage()));
		//récupérer l'objet Graphics
	    Graphics g = image.getGraphics();
	    //définir le font
	    g.setFont(g.getFont().deriveFont(25f));
		
	    //On incruste dans l'image les différents textes
		for (int j = 0; j < listeTexte.size(); j++) {
			Texte t = ((List<Texte>) listeTexte).get(j);
			//afficher le texte sur les coordonnées(x=50, y=150)
			g.drawString(t.phrase, t.x, t.y);
			g.dispose();

		}
	    
		//On rentre dans l'objet meme l'emplacement du fichier png représentant le meme
	    String nomMeme = String.valueOf(m.getId())+".png";
	    m.setMemepath(nomMeme);
	    
	    //écrire l'image sous forme d'un fichier iddumeme.png
		ImageIO.write(image, "png", new File(nomMeme));
		System.out.println("Meme ajouté");
		
		//On enregistre le meme dans la database
		em.persist(m);
	}
	
	@POST
	@Path("/addimage")
    @Consumes({ "application/json" })
	public void addBrouillon(Brouillon b) {
		System.out.println("Brouillon ajouté");
		em.persist(b);
	}
	
	@POST
	@Path("/addtag")
    @Consumes({ "application/json" })
	public void addTag(Tag t) {
		System.out.println("Tag ajouté");
		em.persist(t);
	}
	
	@POST
	@Path("/addtexte")
    @Consumes({ "application/json" })
	public void addTexte(Texte t) {
		System.out.println("Texte ajouté");
		em.persist(t);
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
	
	//On récupère l'ensemble des commentaires correspondant au meme
	@GET
	@Path("/listcomment")
    @Produces({ "application/json" })
	public Collection<Comment> listComment(Meme m) {
		return em.createQuery("SELECT * FROM Comment WHERE meme = " + String.valueOf(m.getId()), Comment.class).getResultList();	
	}
	
	@GET
	@Path("/listtagimage")
    @Produces({ "application/json" })
	public Collection<Tag> listTagImage(Image i) {
		return em.createQuery("SELECT * FROM Tag WHERE image = " + i.getNom(), Tag.class).getResultList();	
	}
	
	@GET
	@Path("/listtagmeme")
	@Produces({ "application/json" })
	public Collection<Tag> listTagMeme(Meme m) {
		return em.createQuery("SELECT * FROM Tag WHERE meme = " + String.valueOf(m.getId()), Tag.class).getResultList();	
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
	
	@POST
	@Path("/associatebrouillontexte")
    @Consumes({ "application/json" })
	public void associateBrouillonImage(Associate as) {
		System.out.println(as.getBrouillonId() +" "+ as.getImageId());
		Brouillon b = em.find(Brouillon.class, as.getBrouillonId());
		Image i = em.find(Image.class, as.getTexteId());
		i.setBrouillon(b);
	}
	
	@POST
	@Path("/associatebrouillontexte")
    @Consumes({ "application/json" })
	public void associateBrouillonTexte(Associate as) {
		System.out.println(as.getBrouillonId() +" "+ as.getTexteId());
		Brouillon b = em.find(Brouillon.class, as.getBrouillonId());
		Texte t = em.find(Texte.class, as.getTexteId());
		t.setBrouillon(b);
	}
	
	@POST
	@Path("/removebrouillon")
    @Consumes({ "application/json" })
	public void removeBrouillon(Brouillon b) {
		System.out.println("brouillon supprimé");
		em.remove(b);
	}
	
	@POST
	@Path("/removeimage")
    @Consumes({ "application/json" })
	public void removeImage(Image i) {
		System.out.println("image supprimée");
		em.remove(i);
	}
	
	@POST
	@Path("/removememe")
    @Consumes({ "application/json" })
	public void removeMeme(Meme m) {
		System.out.println("meme supprimé");
		em.remove(m);
	}
	
	@POST
	@Path("/removecomment")
    @Consumes({ "application/json" })
	public void removeComment(Comment c) {
		System.out.println("comment supprimé");
		em.remove(c);
	}
	
	@POST
	@Path("/removeuser")
    @Consumes({ "application/json" })
	public void removeUser(User u) {
		System.out.println("user supprimé");
		em.remove(u);
	}
	
	@POST
	@Path("/removetag")
    @Consumes({ "application/json" })
	public void removeTag(Tag t) {
		System.out.println("tag supprimé");
		em.remove(t);
	}
	
	@POST
	@Path("/removetexte")
    @Consumes({ "application/json" })
	public void removeTexte(Texte t) {
		System.out.println("texte supprimé");
		em.remove(t);
	}
	
}
