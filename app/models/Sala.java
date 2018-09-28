package models;

import javax.persistence.Entity;

import play.db.jpa.Model;

@Entity
public class Sala extends Model{
	public String nome;
}
