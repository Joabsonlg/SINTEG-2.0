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
	 	
	@Required
	public String apelido;
	
	public Blob foto;
	
	@Required
	public String cpf;
	
	@Required
	@Temporal(TemporalType.DATE)
	public Date dataNascimento;
	
	@Required
	public String sexo;
	
	@Required
	public String naturalidade;

	@Required
	@ManyToOne
	public Uf uf, ufNasc;

	public String fone;

	@Required
	public String pai;

	@Required
	public String mae;

	@Required
	public String endereco;

	@Required
	public String bairro;

	@Required
	public String cep;

	@Required
	public String cidade;

	@Required
	@ManyToOne
	public Escolaridade escolaridade;

	@OneToMany(mappedBy="aluno")
	public List<Matricula> matriculas;

	@Required
	public String rg;

	public String observacao;
	
	@Temporal(TemporalType.DATE)
	public Date dataCadastro;
	
}
