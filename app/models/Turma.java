package models;

import java.util.Date;
import java.util.List;

import javax.persistence.*;

import play.data.validation.Required;
import play.db.jpa.*;

@Entity
public class Turma extends Model{
	
	public String codigo;
	
	@ManyToOne
	public Sala sala;
	
	public boolean estado;
	
	public int qtde;
	
	@ManyToOne
	public Curso curso;
		
	@ManyToOne
	public Professor professor;
	
	@Required
	public int vagasTurma;
	
	@OneToMany(mappedBy="turma")
	public List<Matricula> matriculas;

	public String diasAulas;
		
	@Temporal(TemporalType.DATE)
	public Date dataInicioAula;
	
	@Temporal(TemporalType.TIME)
	public Date horaInicioAula, horaFimAula;
	
}
