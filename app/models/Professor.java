package models;


import javax.persistence.*;
import java.util.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Professor extends Model{
	
	@Required
	public String nomeProfessor;
	
	public String username, emailProfessor;
	
	public String senha;
	
	@Required
	public Blob foto;
	
	public String cpf;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date dataNascimento;
	
	public String sexo;
	
	public String naturalidade;
	
	@ManyToOne
	public Uf uf, ufNasc;
	
	public String fone;
	
	public String pai;
	
	public String mae;
	
	public String endereco;
	
	public String bairro;
	
	public String cep;
	
	public String cidade;
	
	@ManyToOne
	public Escolaridade escolaridade;
	
	@OneToMany(mappedBy="professor")
	public List<Turma> turmas;
	
	public String rg;
	
	public String observacao;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date dataCadastro;
	
	public static Professor connect(String nomeProfessor, String senha) {
	    return find("byNomeProfessorAndSenha", nomeProfessor, senha).first();
	}
	
}
