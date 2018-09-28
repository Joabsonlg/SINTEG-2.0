package models;


import javax.persistence.*;
import java.util.*;
import play.data.validation.*;
import play.db.jpa.*;

@Entity
public class Aluno extends Model{
	
	@Required
	public String nomeAluno;
	
	@Required
	@Email
	public String emailAluno;
	 	
	public String apelido;
	
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
	
	@OneToMany(mappedBy="aluno")
	public List<Matricula> matriculas;
	
	public String rg;
	
	public String observacao;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date dataCadastro;
	
}
