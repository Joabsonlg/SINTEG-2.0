package controllers;

import java.util.List;

import models.*;
import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
public class Cursos extends Controller{
	
	public static void formCadCurso() {
		List<Curso> cursos = Curso.findAll();
		render(cursos);
	}
	
	public static void addCurso(Curso curso) {
		if (curso.save() != null) {
			flash.success("Curso cadastrado.");
		}else {
			flash.error("Erro ao cadastrar.");
		}
		formCadCurso();
	}
	
	public static void listCurso() {
		List<Curso> cursos = Curso.findAll();
		render(cursos);
	}
	
	public static void removeCurso(Long id) {
		Curso c = Curso.findById(id);
		c.delete();
		formCadCurso();
	}
	
	public static void pesquisaNome(String nome) {
		String nomeP = "%"+nome+"%";
		List<Curso> cursos = Curso.find("nomeCurso LIKE '"+nomeP+"'").fetch();
		renderTemplate("Cursos/formCadCurso.html", cursos);
	}
	
	public static void detailCurso(Long id) {
		Curso curso = Curso.findById(id);
		List<Curso> cursos = Curso.findAll();
		renderTemplate("Cursos/formCadCurso.html", curso, cursos);
	}
	
	public static void editCurso() {}
	
	
	public static void imprimir(Long id) {
		Curso curso = Curso.findById(id);
		render(curso);
	}
	
}
