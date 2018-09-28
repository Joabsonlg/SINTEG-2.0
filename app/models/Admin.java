package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Admin extends Model{
	
	@Required
	public String nomeAdmin;
	
	@Temporal(TemporalType.DATE)
	public Date dataNascimento;
	
	public String nome, rua, cidade, cargo;
	
	public String email;
	
	@ManyToOne
	public Uf uf;

	@Required
	@Password
	public String senhaAdmin;
	
	public static Admin connect(String nome, String senhaAdmin) {
	    return find("byNomeAndSenhaAdmin", nome, senhaAdmin).first();
	}
	
	@Required
	public Blob foto;
}
