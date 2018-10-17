package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.data.validation.Min;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Curso extends Model{
	
	@Required
	public String nomeCurso;
	
	@Required
	@Min(0)
	public int idadeMinima, idadeMaxima;
	
	@Required
	public int cargaHoraria;
	
	@OneToMany(mappedBy="curso")
	public List<Turma> turmas;
	
}
