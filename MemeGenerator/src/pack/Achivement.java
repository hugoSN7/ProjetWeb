package pack;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

public class Achivement {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)  
	private int idAch;
	private Boolean Create_user = true;
	private Boolean Create_1meme = false;
	private Boolean Create_10meme = false;
	private Boolean Create_100meme = false;
	private Boolean Create_1000meme = false;
	public static final String user = "Novice";
	public static final String un_meme = "Débutant";
	public static final String dix_meme = "Amateur";
	public static final String cent_meme = "Confirmé";
	public static final String mille_meme = "Expert";
	
	@OneToOne
	private User Owner;
	
	public User getOwner() {
		return Owner;
	}
	public void setOwner(User owner) {
		Owner = owner;
	}
	public int getIdAch() {
		return idAch;
	}
	public void setIdAch(int idAch) {
		this.idAch = idAch;
	}
	public Boolean getCreate_user() {
		return Create_user;
	}
	public void setCreate_user(Boolean create_user) {
		Create_user = create_user;
	}
	public Boolean getCreate_1meme() {
		return Create_1meme;
	}
	public void setCreate_1meme(Boolean create_1meme) {
		Create_1meme = create_1meme;
	}
	public Boolean getCreate_10meme() {
		return Create_10meme;
	}
	public void setCreate_10meme(Boolean create_10meme) {
		Create_10meme = create_10meme;
	}
	public Boolean getCreate_100meme() {
		return Create_100meme;
	}
	public void setCreate_100meme(Boolean create_100meme) {
		Create_100meme = create_100meme;
	}
	public Boolean getCreate_1000meme() {
		return Create_1000meme;
	}
	public void setCreate_1000meme(Boolean create_1000meme) {
		Create_1000meme = create_1000meme;
	}
	public static String getUser() {
		return user;
	}
	public static String getUnMeme() {
		return un_meme;
	}
	public static String getDixMeme() {
		return dix_meme;
	}
	public static String getCentMeme() {
		return cent_meme;
	}
	public static String getMilleMeme() {
		return mille_meme;
	}
	
	

}
