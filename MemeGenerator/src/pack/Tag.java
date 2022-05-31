package pack;

import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Tag {
	
	@Id
	private String mot;
	
	@ManyToMany(mappedBy = "tags", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	private Collection<Template> templates;
	
	public String getMot() {
		return mot;
	}

	public void setMot(String mot) {
		this.mot = mot;
	}

	public Collection<Template> getTemplates() {
		return templates;
	}

	public void setTemplates(Collection<Template> templates) {
		this.templates = templates;
	}

	public String toString() {
		String str = mot;
		if (templates != null) {
			for (Template p : templates) {
				str += "\n\t picture : " + p.toString() + "\n";
			}
		}
		return str;
	}
}
