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
	//"/home/cedricazanove/n7/2sn/s8/applicationWeb/projet/ProjetWeb/memegeneratorreact/src/db/";
	private String pathToStore = "/home/cedricazanove/n7/2sn/s8/applicationWeb/projet/ProjetWeb/memegeneratorreact/src/db/";
	private String pathToGetMeme = "../db/meme/";
	private String pathToGetTemplate = "../db/template/";
	
	//methode pour update ou ajouter un tag
	public void updateTag(String mot, Template pic) {
		Tag tag = em.find(Tag.class, mot);
		if (tag == null) {
			System.out.println("le tag " + mot + " n'existe pas");
			tag = new Tag();
			tag.setMot(mot);
		} else {
			//sinon on ajoute l'image au tag deja existant
			System.out.println("tag existant " + tag.toString());
        	Collection<Template> allPicturesWithThisTag = tag.getTemplates();
        	if (allPicturesWithThisTag == null) {
        		System.out.println("le tag existe mais n'a aucune picture");
        	}
			System.out.println("le tag existe");
		}
		System.out.println("now le tag existe et tag : " + tag.toString());
    	Collection<Template> allPicturesWithThisTag = tag.getTemplates();
    	if (allPicturesWithThisTag == null) allPicturesWithThisTag = new ArrayList<Template>();
    	allPicturesWithThisTag.add(pic);
    	tag.setTemplates(allPicturesWithThisTag);
    	em.persist(tag);	        	
    	System.out.println("tag : " + tag.toString()); 
    	
    	Collection<Tag> allTagsOnThePicture = pic.getTags();
    	if (allTagsOnThePicture == null) allTagsOnThePicture = new ArrayList<Tag>();
    	allTagsOnThePicture.add(tag);
    	pic.setTags(allTagsOnThePicture);
	}
	
	//methode pour update ou ajouter une category
	public void updateCategory(String mot, Meme picture) {
		Category category = em.find(Category.class, mot);
		if (category == null) {
			System.out.println("la category " + mot + " n'existe pas");
			category = new Category();
			category.setMot(mot);
		} else {
			//sinon on ajoute l'image au tag deja existant
			System.out.println("category existant " + category.toString());
        	Collection<Meme> allPicturesWithThisTag = category.getPictures();
        	if (allPicturesWithThisTag == null) {
        		System.out.println("la category existe mais n'a aucune picture");
        	}
			System.out.println("le category existe");
		}
		System.out.println("now la category existe et tag : " + category.toString());
    	Collection<Meme> allPicturesWithThisTag = category.getPictures();
    	if (allPicturesWithThisTag == null) allPicturesWithThisTag = new ArrayList<Meme>();
    	allPicturesWithThisTag.add(picture);
    	category.setPictures(allPicturesWithThisTag);
    	em.persist(category);	        	
    	System.out.println("category : " + category.toString()); 
    	
    	Collection<Category> allTagsOnThePicture = picture.getTags();
    	if (allTagsOnThePicture == null) allTagsOnThePicture = new ArrayList<Category>();
    	allTagsOnThePicture.add(category);
    	picture.setTags(allTagsOnThePicture);
	}
	
	
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


	@GET
	@Path("/authentification")
	@Produces({ "application/json" })
	public boolean authentification(@QueryParam("username")String username, @QueryParam("password")String password) {
		System.out.println("authentification");
		System.out.println(username);
		System.out.println(password);
		User u = em.find(User.class, username);
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
			//on récupere le token pour l'association au user
			String token = input.getFormDataPart("token", String.class, null);
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
            Meme pic = new Meme();
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
			String categoryContent = input.getFormDataPart("tag", String.class, null);
			if (categoryContent.equals("undefined")) {
				System.out.println("Aucun tag");
			} else {
				System.out.println("existence de tags");
				categoryContent = categoryContent.toLowerCase();
				//premiere tentative de recuperation de tag si des # ont ete utilisé
				String[] lesCategories = categoryContent.split("#");
				//si la taille fait 1 c'est qu'il n'y a pas de #, soit qu'on a utilisé des , soit qu'il y a depuis le debut qu'un seul mot
				if (lesCategories.length == 1) {
					lesCategories = lesCategories[0].split(",");
				}
				//s'il y a tjr une taille c'est qu'il y avait qu'en fait un seul tag depuis le debut
				if (lesCategories.length == 1) {
					updateCategory(lesCategories[0], pic);
				} else {
					for (int i = 0; i < lesCategories.length; i++) {
						System.out.println("le tag qu'on update est : " + lesCategories[i]);
						updateCategory(lesCategories[i], pic);
					}
				}
			}
            em.persist(pic);
            System.out.println("pic : " + pic.toString());
            if(token != "false") {
            	System.out.println("association de l'image au user");
        		User u = em.find(User.class, token);
        		if (u==null) {System.out.println("association impossible");}
        		else {
        			pic.setOwner(u);
        			System.out.println("association faite");
        		}
        	}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@POST
	@Path("/addtemplate")
    @Consumes({ MediaType.MULTIPART_FORM_DATA})
	public void addtemplate(MultipartFormDataInput input) {
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
            
            try (FileOutputStream outputStream = new FileOutputStream(new File(pathToStore + "/template/" + fileName))) {
                outputStream.write(bucket);
                outputStream.flush();
                outputStream.close();
            }
            //On cree l'objet et on l'enregistre ds la db
            Template pic = new Template();
            pic.setIsMeme(isMeme);
            pic.setNamePicture(fileName);
            pic.setPath(pathToGetMeme + fileName);
            System.out.println("Meme ajouté");
            System.out.println(pathToGetMeme + fileName);
            //on recupere les tags s'il existe
			String tagContent = input.getFormDataPart("tag", String.class, null);
			if (tagContent.equals("undefined")) {
				System.out.println("Aucun tag");
			} else {
				System.out.println("existence de tags");
				tagContent = tagContent.toLowerCase();
				//premiere tentative de recuperation de tag si des # ont ete utilisé
				String[] lesTags = tagContent.split("#");
				//si la taille fait 1 c'est qu'il n'y a pas de #, soit qu'on a utilisé des , soit qu'il y a depuis le debut qu'un seul mot
				if (lesTags.length == 1) {
					lesTags = lesTags[0].split(", ");
				}
				//s'il y a tjr une taille c'est qu'il y avait qu'en fait un seul tag depuis le debut
				if (lesTags.length == 1) {
					updateTag(lesTags[0], pic);
				} else {
					for (int i = 0; i < lesTags.length; i++) {
						System.out.println("le tag qu'on update est : " + lesTags[i]);
						updateTag(lesTags[i], pic);
					}
				}
			}
            em.persist(pic);
            System.out.println("pic : " + pic.toString());
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	@GET
	@Path("/listuser_meme")
    @Produces({ "application/json" })
	public Collection<Meme> list_user_meme(@DefaultValue("*") @QueryParam("token")String username){
		User u = em.find(User.class, username);
		if(u==null) {
			System.out.println("user inexistant");
			return null;
		}else {
			System.out.println("liste des pictures");
			try {
				Collection<Meme> allPicture = u.getMemes();
				Collection<Meme> memeuser = new ArrayList<Meme>();
				for (Meme p : allPicture) {
					if (p.getIsMeme()) {
						Meme pCopy = new Meme();
						pCopy.setIsMeme(p.getIsMeme());
						pCopy.setNamePicture(p.getNamePicture());
						pCopy.setPath(p.getPath());
						memeuser.add(pCopy);
					}
				
				}
				System.out.println("retour de la liste des memes");
				return memeuser;
			} catch (Exception e) {
				System.out.println(e);
				return null;
			}
		}
	}
	
	
	@GET
	@Path("/listtemplate")
    @Produces({ "application/json" })
	public Collection<Template> listTemplate() {
		System.out.println("Template");
		Collection<Template> allTemplates = em.createQuery("from Template where isMeme = false", Template.class).getResultList();
		Collection<Template> allTemplatesToSend = new ArrayList<Template>();
		for (Template p : allTemplates) {
			System.out.println("template : " + p.toString());
			Template pCopy = new Template();
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
	public Collection<Template> listTemplateWithTag(@DefaultValue("*") @QueryParam("tag") String mot) {
		mot = mot.toLowerCase();
		if (mot.equals("")) mot = "*";
		System.out.println("Tag souhaité " + mot);
		Tag tag = em.find(Tag.class, mot);
		if (tag == null) {
			System.out.println("le tag " + mot + " n'existe pas");
			return listTemplate();
		} else {
			System.out.println("tag existant " + tag.toString());
			Collection<Template> allTemplates = em.createQuery("SELECT p FROM Template p JOIN p.tags c WHERE c.mot = :word and p.isMeme = false").setParameter("word", mot).getResultList();
			Collection<Template> allTemplatesToSend = new ArrayList<Template>();
			for (Template p : allTemplates) {
				System.out.println("template : " + p.toString());
				Template pCopy = new Template();
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
	public Collection<Meme> listMeme() {
		System.out.println("Meme");
		Collection<Meme> allMemes = em.createQuery("from Meme where isMeme = true", Meme.class).getResultList();
		Collection<Meme> allMemesToSend = new ArrayList<Meme>();
		for (Meme p : allMemes) {
			System.out.println("meme : " + p.toString());
			Meme pCopy = new Meme();
			pCopy.setIsMeme(p.getIsMeme());
			pCopy.setNamePicture(p.getNamePicture());
			pCopy.setPath(p.getPath());
			allMemesToSend.add(pCopy);
		}
		return allMemesToSend;
	}

	//en réalité la fonction associe user et template aussi
	/*@POST
	@Path("/associate_meme_user")
	@Consumes({ "application/json" })
	public void associate_meme_user(HashMap<String,String> association) {
		System.out.println("association du meme au user");
		System.out.println(association.get("token"));
		User u = em.find(User.class, association.get("token"));
		
		System.out.println(association.get("name"));
		Picture meme = em.find(Picture.class, association.get("name"));
		
		if (u==null | meme==null ) {System.out.println("association impossible");}
		else {
			meme.setOwner(u);
			System.out.println("association faite");
		}
	}*/
	
	
	@POST
	@Path("/removeuser")
	@Consumes({"application/json"})
	public void removeUser(HashMap<String,String> user) {
		System.out.println(user.get("name"));
		User u = em.find(User.class, user.get("name"));
		System.out.println("remove user");
		if (u==null) {
			System.out.println("suppression impossible l'user n'existe pas");
			}
		else {
			Collection<Meme> allMemes = u.getMemes();
			System.out.println("suppression des memes");
			for (Meme p : allMemes) {
				u.removeImage(p);
			}
			System.out.println("suppression du user");
			//em.remove(u);
			System.out.println("suppression faite");
		}
	}
	
	//get comments picture
	@GET
	@Path("/listcomment_picture")
    @Produces({ "application/json" })
	public Collection<Comment> list_comment_picture(String namePicture){
		System.out.println(namePicture);
		int idp = Integer.parseInt(namePicture);
		Meme meme = em.find(Meme.class, idp);
		//if (meme.getComments().size() == 0) {

		return meme.getComments();
		
	}
	
	//post comment picture user
	@POST
	@Path("/associate_comment")
	@Consumes({ "application/json" })
	public void associate_comment(HashMap<String,String> association) {
		System.out.println("association du commentaire au user");
		User writer = em.find(User.class, association.get("token"));

		Meme meme = em.find(Meme.class, Integer.parseInt(association.get("idMeme")));

		Comment c = new Comment(association.get("content"), writer, meme);
		
		//verif string !=null

		if (writer==null | meme==null ) {System.out.println("association impossible");}
		else {
			em.persist(c);
			System.out.println("association faite");
		}
	}
	



}
