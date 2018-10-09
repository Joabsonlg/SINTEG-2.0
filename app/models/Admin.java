package models;

import java.util.*;
import javax.persistence.*;

import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Admin extends Model{
		
	public String nomeAdmin, username, senhaAdmin, email, cpf, sexo, naturalidade, fone;
	public String pai, mae, endereco, bairro, cep, cidade, rg, observacao, cargo;
	
	public Blob foto;
	
	@Temporal(TemporalType.DATE)
	public Date dataNascimento, dataCadastro;
	
	@ManyToOne
	public Uf uf, ufNasc;
	
	@ManyToOne
	public Escolaridade escolaridade;
	
	public static Admin connect(String nome, String senhaAdmin) {
	    return find("byNomeAdminAndSenhaAdmin", nome, senhaAdmin).first();
	}
}
