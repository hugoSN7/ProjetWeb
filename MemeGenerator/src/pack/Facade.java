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
import javax.management.Query;
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
	
	private String pathToStore = "/home/cedricazanove/n7/2sn/s8/applicationWeb/projet/ProjetWeb/memegeneratorreact/src/db/";
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
            if (isMeme) {
                System.out.println("Meme ajouté");
                System.out.println(pathToGetMeme + fileName);                
            } else {
            	System.out.println("Template ajouté");
            	System.out.println(pathToGetTemplate + fileName);
            }
            //on recupere les tags s'il existe
			String tagContent = input.getFormDataPart("tag", String.class, null);;
			Tag tag = em.find(Tag.class, tagContent.toLowerCase());
			if (tag == null) {
				System.out.println("le tag " + tagContent + " n'existe pas");
				tag = new Tag();
				tag.setMot(tagContent.toLowerCase());
			} else {
				System.out.println("tag existant " + tag.toString());
	        	Collection<Picture> allPicturesWithThisTag = tag.getPictures();
	        	if (allPicturesWithThisTag == null) {
	        		System.out.println("le tag existe mais n'a aucune picture");
	        	}
				System.out.println("le tag existe");
			}
			System.out.println("now le tag existe et tag : " + tag.toString());
        	Collection<Picture> allPicturesWithThisTag = tag.getPictures();
        	if (allPicturesWithThisTag == null) allPicturesWithThisTag = new ArrayList<Picture>();
        	allPicturesWithThisTag.add(pic);
        	tag.setPictures(allPicturesWithThisTag);
        	
        	Collection<Tag> allTagsOnThePicture = pic.getTags();
        	if (allTagsOnThePicture == null) allTagsOnThePicture = new ArrayList<Tag>();
        	allTagsOnThePicture.add(tag);
        	pic.setTags(allTagsOnThePicture);
        	
			em.persist(tag);
            em.persist(pic);
        	System.out.println("tag : " + tag.toString()); 
            System.out.println("pic : " + pic.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@GET
	@Path("/listtemplate")
    @Produces({ "application/json" })
	public Collection<Picture> listTemplate() {
		System.out.println("Template");
		Collection<Picture> allTemplates = em.createQuery("from Picture where isMeme = false", Picture.class).getResultList();
		Collection<Picture> allTemplatesToSend = new ArrayList<Picture>();
		for (Picture p : allTemplates) {
			System.out.println(p.toString());
			Picture pCopy = new Picture();
			pCopy.setIsMeme(p.getIsMeme());
			pCopy.setNamePicture(p.getNamePicture());
			pCopy.setPath(p.getPath());
			allTemplatesToSend.add(pCopy);
		}
		return allTemplatesToSend;
	}
	
	@GET
	@Path("/listtemplatewithtag")
    @Produces({ "application/json" })
	public Collection<Picture> listTemplateWithTag(@DefaultValue("*") @QueryParam("tag") String mot) {
		System.out.println("Tag " + mot);
		Tag tag = em.find(Tag.class, mot.toLowerCase());
		if (tag == null) {
			System.out.println("le tag " + mot + " n'existe pas");
			return listTemplate();
		} else {
			System.out.println("tag existant " + tag.toString());
			Collection<Picture> allTemplates = em.createQuery("SELECT p FROM Picture p JOIN p.tags c WHERE c.mot = :word and p.isMeme = false").setParameter("word", mot).getResultList();
			Collection<Picture> allTemplatesToSend = new ArrayList<Picture>();
			for (Picture p : allTemplates) {
				System.out.println("p : " + p.toString());
				Picture pCopy = new Picture();
				pCopy.setIsMeme(p.getIsMeme());
				pCopy.setNamePicture(p.getNamePicture());
				pCopy.setPath(p.getPath());
				allTemplatesToSend.add(pCopy);
				System.out.println("pCopy : " + pCopy.toString());
			}
			return allTemplatesToSend;
		}
	}
	
	@GET
	@Path("/listmeme")
    @Produces({ "application/json" })
	public Collection<Picture> listMeme() {
		System.out.println("Meme");
		Collection<Picture> allMemes = em.createQuery("from Picture where isMeme = true", Picture.class).getResultList();
		Collection<Picture> allMemesToSend = new ArrayList<Picture>();
		for (Picture p : allMemes) {
			System.out.println(p.toString());
			Picture pCopy = new Picture();
			pCopy.setIsMeme(p.getIsMeme());
			pCopy.setNamePicture(p.getNamePicture());
			pCopy.setPath(p.getPath());
			allMemesToSend.add(pCopy);
		}
		return allMemesToSend;
	}
}
