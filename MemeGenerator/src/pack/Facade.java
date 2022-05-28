package pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.awt.*;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.nio.file.Paths;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataOutput;
import org.jboss.resteasy.plugins.providers.multipart.MultipartOutput;


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


	@GET
	@Path("/authentification")
	@Produces({ "application/json" })
	public boolean authentification(@QueryParam("username")String username, @QueryParam("password")String password) {
		System.out.println("authentification");
		System.out.println(username);
		System.out.println(password);
		User u = em.find(User.class, username);
		System.out.println(u.getPassword());
		
		if (u == null) {
			System.out.println("user non trouvé on va retourné false");
			return false;
		}
		else if(password.equals(u.getPassword())) {
			System.out.println("user trouvé on va retourné que l'authentification est bonne");
			
			return true;
		} else  {
			System.out.println("user trouvé on va retourné que l'authentification est mauvaise");
			return false;
		}
	}
	
	@POST
	@Path("/addimage")
    @Consumes({ MediaType.MULTIPART_FORM_DATA})
	public void addimage(MultipartFormDataInput input) {
		try {
			//Le nom du fichier est stocké dans la variable suivante
			String fileName = input.getFormDataPart("name", String.class, null);
			//On ccree un file pour recuperer le file envoyé
			File file = input.getFormDataPart("file", File.class, null);
			//On enregistre par la suite le file dans un dossier
			InputStream picture = new FileInputStream(file); 
            int size = picture.available();
            byte[] bucket = new byte[size];
            picture.read(bucket);
            try (FileOutputStream outputStream = new FileOutputStream(new File("/home/cedricazanove/git/ProjetWeb/MemeGenerator/dbImg/" + fileName))) {
                outputStream.write(bucket);
                outputStream.flush();
                outputStream.close();
            }
			System.out.println("Image ajouté");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@GET
	@Path("/listimage")
    @Produces({ MediaType.MULTIPART_FORM_DATA })
	public MultipartFormDataOutput listImage() {
		//l'objet suivant permet d'envoyer des files au front
		MultipartFormDataOutput output = new MultipartFormDataOutput();
		output.addFormData("file", new File("/home/cedricazanove/git/ProjetWeb/MemeGenerator/dbImg/doigtLeve.png"), MediaType.APPLICATION_OCTET_STREAM_TYPE, "/home/cedricazanove/git/ProjetWeb/MemeGenerator/dbImg/doigtLeve.png");
		return output;
	}
}
