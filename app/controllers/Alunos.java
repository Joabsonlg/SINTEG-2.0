package controllers;

import java.text.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import models.*;
import play.*;
import play.db.jpa.Blob;
import play.mvc.*;

@With(Secure.class)
public class Alunos extends Controller{
		
	public static void indexAluno() {
		render();
	}
	
	public static void formCadAluno() throws ParseException {
		List<Uf> ufs = Uf.findAll();
		List<Escolaridade> niveis = Escolaridade.findAll();
		render(ufs, niveis);
	}
	
	public static void cadAluno(Aluno aluno, String up) throws ParseException {
		//pegaData(aluno);
		/*if(up == null) {
			if(jaExiste(aluno) == true) {
				flash.error("Já existe um aluno com esses dados.");
				formCadAluno();
			}
		}*/
		aluno.save();
		flash.success(aluno.nomeAluno + " registrado");
		detailAluno(aluno.id);
	}
	
	static boolean jaExiste(Aluno aluno) {
		Aluno pesquisa = Aluno.find("cpf = ?", aluno.cpf).first();
		if(pesquisa == null) {
			return false;
		}else {
			return true;
		}
	}
	
	static Date pegaData(Aluno aluno) throws ParseException {

		Date dataCadastro = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date();
		String dStr = DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
		dataCadastro = formato.parse(dStr);		
		return aluno.dataCadastro = dataCadastro;
	}
	
	public static void listAluno() {
		List<Aluno> alunos = Aluno.findAll();
		List<Turma> turmas = Turma.findAll();
		List<Curso> cursos = Curso.findAll();
		render(alunos, turmas, cursos);
	}
	
	public static void pesquisaNome(String nome) {
		String nomeP = "%"+nome+"%";
		List<Aluno> alunos = Aluno.find("nomeAluno LIKE '"+nomeP+"'").fetch();
		List<Uf> ufs = Uf.findAll(); 
		List<Escolaridade> niveis = Escolaridade.findAll();
		renderTemplate("Alunos/formCadAluno.html", alunos, ufs, niveis);
	}
	
	public static void pesquisa(Long turmaId, Long periodoId, Long cursoId) {

		List<Aluno> alunos = new ArrayList<Aluno>();		
		int aux = 0;
		List<Matricula> matriculas = null;
		
		if(turmaId == null && periodoId == null && cursoId != null) {
			matriculas = Matricula.find("turma.curso.id = ?", cursoId).fetch();
		}else if(turmaId == null && periodoId != null && cursoId == null) {
			matriculas = Matricula.find("turma.periodo.id = ?", periodoId).fetch();
		}else if(turmaId != null && periodoId == null && cursoId == null) {
			matriculas = Matricula.find("turma.id = ?", turmaId).fetch();
		}else if(turmaId == null && periodoId != null && cursoId != null){
			matriculas = Matricula.find("turma.curso.id = ? and turma.periodo.id = ?", cursoId, periodoId).fetch();
		}else if(turmaId != null && periodoId == null && cursoId != null){
			matriculas = Matricula.find("turma.id = ? and turma.curso.id = ?", turmaId, cursoId).fetch();
		}else if(turmaId != null && periodoId != null && cursoId == null){
			matriculas = Matricula.find("turma.id = ? and turma.periodo.id = ?", turmaId, periodoId).fetch();
		}else if(turmaId != null && periodoId != null && cursoId != null){
			matriculas = Matricula.find("turma.id = ? and turma.periodo.id = ? and turma.curso.id = ?", turmaId, periodoId, cursoId).fetch();
		}else {
			aux = 1;
		}	
		
		Turma turmaP = Turma.find("id = ?", turmaId).first();
		Curso cursoP = Curso.find("id = ?", cursoId).first();		
		

		
		if(aux == 1) {
			alunos = Aluno.findAll();
		}else {
			for (int i = 0; i < matriculas.size(); i++) {
				long id = matriculas.get(i).aluno.id;
				Aluno a = Aluno.find("id =?", id).first();
				alunos.add(a);
			}
		}
		
		List<Turma> turmas = Turma.findAll();
		List<Curso> cursos = Curso.findAll();
				
		renderTemplate("Alunos/listAluno.html",alunos, turmaP, cursoP, turmas, cursos);
	}
	
	public static void removeAluno(Long id, String senhaPadrao) throws ParseException {
		Setting setting = Setting.findById((long) 1);
		
		if(senhaPadrao.equals(setting.senhaPadrao)) {
			List<Matricula> matriculas = Matricula.find("aluno.id = ?", id).fetch();
			for (Matricula matricula : matriculas) {
				Turma t = Turma.findById(matricula.turma.id);
				if(t.estado == false) {
					t.vagasTurma += 1;
				}
				t.qtde -= 1;
				t.save();
				matricula.delete();
			}			
			Aluno a = Aluno.findById(id);
			a.delete();
			flash.success(a.nomeAluno + " foi removido");
		}else {
			flash.error("Senha incorreta, tente novamente.");
			detailAluno(id);
		}	

		listAluno();
	}
	
	public static void detailAluno(Long id) throws ParseException {			
		List<Matricula> matriculas = Matricula.find("select m from Matricula m where m.aluno.id = ?", id).fetch();
		Aluno aluno = Aluno.findById(id);
		List<Uf> ufs = Uf.findAll();
		List<Curso> cursos = Curso.findAll();
		List<Escolaridade> niveis = Escolaridade.findAll();
		String idade = calculaIdade(aluno.dataNascimento);
		List<Turma> turmas = Turma.findAll();
		renderTemplate("Alunos/formCadAluno.html", turmas, cursos, matriculas, idade, aluno, ufs, niveis);
	}
	
	public static void comprovante(Long id) {
		Matricula matricula = Matricula.findById(id);
		render(matricula);
	}
	
	public static void ficha(Long id) {
		Aluno aluno = Aluno.findById(id);
		render(aluno);
	}
	
	public static void historico(Long id) {
		Aluno aluno = Aluno.findById(id);
		List<Matricula> matriculas = Matricula.find("aluno.id = ?", id).fetch();
		render(aluno, matriculas);
	}
	
	public static void userPhoto(long id) { 
	   final Aluno user = Aluno.findById(id); 
	   response.setContentTypeIfNotSet(user.foto.type());
	   renderBinary(user.foto.get());
	} 
	
	public static String calculaIdade(java.util.Date dataNasc){

		Calendar dateOfBirth = new GregorianCalendar();

		dateOfBirth.setTime(dataNasc);

		// Cria um objeto calendar com a data atual
		Calendar today = Calendar.getInstance();
		 

		// Obtém a idade baseado no ano
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		
		int mes = today.get(Calendar.MONTH) - dateOfBirth.get(Calendar.MONTH);

		dateOfBirth.add(Calendar.YEAR, age);
		
		 

		//se a data de hoje é antes da data de Nascimento, então diminui 1(um)

		if (today.before(dateOfBirth)) {

			age--;

		}
		
		dateOfBirth.add(Calendar.MONTH, mes);
		
		if(mes<0) {
			mes = 12+mes;
		}	
				
		String idad = age+" /"+mes;
		return idad;

	}

}
