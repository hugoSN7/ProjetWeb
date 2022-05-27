package pack;

import java.util.Collection;
import java.util.HashMap;
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
	
	@GET
	@Path("/listuser")
    @Produces({ "application/json" })
	public Collection<User> listUser() {
		return em.createQuery("from User", User.class).getResultList();	
	}
	
	
	
	@POST
	@Path("/removeuser")
    @Consumes({ "application/json" })
	public void removeUser(User u) {
		System.out.println("user supprimé");
		em.remove(u);
	}

	@POST
	@Path("/authentification")
	@Produces({ "application/json" })
	public String authentification(HashMap<String,String> user) {
		System.out.println("authentification");
		System.out.println(user.get("username"));
		System.out.println(user.get("password"));
		User u = em.find(User.class, user.get("username"));
		System.out.println(u.getPassword());
		
		if (u == null) {
			System.out.println("user non trouvé on va retourné false");
			return "false car mauvais pseudo";
		}
		else if(user.get("password").equals(u.getPassword())) {
			System.out.println("user trouvé on va retourné que l'authentification est bonne");
			return "true";
		} else  {
			System.out.println("user trouvé on va retourné que l'authentification est mauvaise");
			return "false car mauvais password";
		}
	}
}
