package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Setting extends Model{

	public String senhaPadrao;
	public String nomeInstituto;
	public String emailInstituto;
	public String websiteInstituto;
	public String foneInstituto;

}
