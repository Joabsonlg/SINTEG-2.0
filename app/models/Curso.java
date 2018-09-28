package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import play.db.jpa.Model;

@Entity
public class Curso extends Model{
	
	public String nomeCurso;
	
	public int idadeMinima, idadeMaxima;
	
	public int cargaHoraria;
	
	@OneToMany(mappedBy="curso")
	public List<Turma> turmas;
	
}
