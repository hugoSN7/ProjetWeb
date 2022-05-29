package pack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
import javax.persistence.TypedQuery;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
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
	
	private String pathToStore = "/home/greveill/Annee_2/Projet Web/ProjetWeb/memegeneratorreact/src/db";
	private String pathToGetMeme = "../db/meme/";
	private String pathToGetTemplate = "../db/template/";
	
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
		System.out.println(u);
		
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
			//On cree un file pour recuperer le file envoyé
			File file = input.getFormDataPart("file", File.class, null);
			//on regarde s'il s'agit d'un meme ou d'un template
			Boolean isMeme = input.getFormDataPart("isMeme", Boolean.class, null);
			//on recupere les tags s'il existe
			String tagContent;
			Tag tag = null;
			try {
				tagContent = input.getFormDataPart("tag", String.class, null);
				tag = new Tag();
				tag.setMot(tagContent.toLowerCase());
				System.out.println("le tag enregistré est " + tag.getId());
	            em.persist(tag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//On enregistre par la suite le file dans un dossier
			InputStream picture = new FileInputStream(file); 
            int size = picture.available();
            byte[] bucket = new byte[size];
            picture.read(bucket);
            
            try (FileOutputStream outputStream = new FileOutputStream(new File(pathToStore + (isMeme ? "/meme/": "/template/") + fileName))) {
                outputStream.write(bucket);
                outputStream.flush();
                outputStream.close();
            }
            //On cree l'objet et on l'enregistre ds la db
            Picture pic = new Picture();
            pic.setIsMeme(isMeme);
            pic.setNamePicture(fileName);
            if (isMeme) {
                pic.setPath(pathToGetMeme + fileName);
            } else {
            	pic.setPath(pathToGetTemplate + fileName);
            }
            em.persist(pic);
            if (isMeme) {
                System.out.println("Meme ajouté");
                System.out.println(pathToGetMeme + fileName);                
            } else {
            	System.out.println("Template ajouté");
            	System.out.println(pathToGetTemplate + fileName);
            }
            
            Picture p = em.find(Picture.class, fileName);
            if (tag != null) {
            	p.getTags().add(tag);
            }
            System.out.println("picture : " + p.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@GET
	@Path("/listtemplate")
    @Produces({ "application/json" })
	public Collection<Picture> listTemplate() {
		System.out.println("Template");
		List<Picture> allPictures = em.createQuery("from Picture where isMeme = false", Picture.class).getResultList();
		for (Picture p : allPictures) {
			System.out.println("tag : " + p.getTags().toString());
			p.setTags(null);
			System.out.println(p.toString());
		}
		return allPictures;
	}
	
	@GET
	@Path("/listtemplatewithtag")
    @Produces({ "application/json" })
	public Collection<Picture> listTemplateWithTag(@DefaultValue("*") @QueryParam("tag") String mot) {
		System.out.println("Tag " + mot);
		return null;
	}
	
	@GET
	@Path("/listmeme")
    @Produces({ "application/json" })
	public Collection<Picture> listMeme() {
		System.out.println("Meme");
		return em.createQuery("from Picture where isMeme = true", Picture.class).getResultList();
	}


	@POST
	@Path("/associate_meme_user")
	@Consumes({ "application/json" })
	public void associate_meme_user(HashMap<String,String> association) {
		System.out.println("association du meme au user");
		User u = em.find(User.class, association.get("token"));
		System.out.println(u);
		Picture meme = em.find(Picture.class, association.get("name"));
		System.out.println(u);
		System.out.println(meme);
		if (u==null | meme==null ) {System.out.println("association impossible");}
		else {
			meme.setOwner(u);
			System.out.println("association faite");
		}
	}
	
	@GET
	@Path("/listuser_meme")
    @Produces({ "application/json" })
	public Collection<Picture> list_user_meme(@DefaultValue("*") @QueryParam("token")String username){
		User u = em.find(User.class, username);
		return u.getMemes();
		
	}
	
	



}