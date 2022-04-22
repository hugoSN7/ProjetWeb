package classes;

import java.util.HashMap;
import java.util.Collection;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Singleton
public class Facade {

		
	public int idComment = 0;
	public int idImage = 0;
	public int idMeme = 0;
	public int idTag = 0;
	public int idUser = 0;
	
	@PersistenceContext
	private EntityManager em;
		
	public void ajoutComment(String message){
		Comment C = new Comment();
		C.setComment(message);
		C.setId(idComment);
		em.persist(C);
	}

	public void ajoutImage(){
		Image c = new Image();
	}
		
	public Collection <Personne> listePersonnes(){
		TypedQuery<Personne> req = em.createQuery("select p from Personne p",Personne.class);
		return req.getResultList();
	}
		
	public void ajoutAdresse(String rue, String ville) {
		Adresse A = new Adresse();
		A.setRue(rue);
		A.setVille(ville);
		A.setId(idA);
		em.persist(A);
	}
		
	public Collection<Adresse> listeAdresses(){
		TypedQuery<Adresse> req = em.createQuery("select a from Adresse a",Adresse.class);
		return req.getResultList();
	}
		
	public void associer(int idP, int idA) {
		Personne P = em.find(Personne.class, idP);
		if (P == null) throw new RuntimeException("Personne introuvable");
		Adresse A = em.find(Adresse.class, idA);
		if (A == null) throw new RuntimeException("Adresse introuvable");
		P.getAdresses().add(A);
		A.setProprietaire(P);
		em.merge(P);
		em.merge(A);
		
		
	}
		
}
