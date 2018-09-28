package models;

import java.util.Date;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Matricula extends Model{
	
	
	@Required
	@ManyToOne
	public Aluno aluno;
	
	public String observacao;
		
	@Required
	@ManyToOne
	public Turma turma;

	@Required
	@Temporal(TemporalType.DATE)
	public Date dataMatricula;
	
	@Temporal(TemporalType.TIME)
	public Date horaMatricula;
	
	@Temporal(TemporalType.DATE)
	public Date termino;
	
	public String motivo;
	
	public String atendente;
}
