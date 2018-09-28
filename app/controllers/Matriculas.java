package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.*;
import play.mvc.*;


@With(Secure.class)
public class Matriculas extends Controller {
	
	public static void addMatricula(Matricula matricula) throws ParseException {

		
		Calendar dateOfBirth = new GregorianCalendar();
		dateOfBirth.setTime(matricula.aluno.dataNascimento);
		// Cria um objeto calendar com a data atual
		Calendar today = Calendar.getInstance();
		// Obtém a idade baseado no ano
		int age = today.get(Calendar.YEAR) - dateOfBirth.get(Calendar.YEAR);
		dateOfBirth.add(Calendar.YEAR, age);
		//se a data de hoje é antes da data de Nascimento, então diminui 1(um)
		if (today.before(dateOfBirth)) {
			age--;
		}
		if((age < matricula.turma.curso.idadeMinima) || (age > matricula.turma.curso.idadeMaxima)) {
			flash.error("O Aluno não possui idade adequada para este curso.");
			Alunos.detailAluno(matricula.aluno.id);
		}
		
		
		Turma turma = Turma.findById(matricula.turma.id);
		matricula.atendente = session.get("user");
		//pegaData(matricula);
		matricula.save();
		diminuirVaga(turma);
		//verEstado(turma);
		turma.qtde += 1;
		turma.save();
		
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		String hora = params.get("matricula.horaMatricula");
		Date h = null;
		try {
			h = formato.parse(hora);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		matricula.horaMatricula = h;
		
		matricula.save();
		flash.success("Matricula realizada");
		Alunos.detailAluno(matricula.aluno.id);
	}
	
	public static void comprovante(Long id) {
		Matricula matricula = Matricula.find("aluno.id = ?", id).first();
		render(matricula);
	}

	/*
	public static String verEstado(Turma turma) {
		if(turma.vagasTurma == 0) {
			return turma.estado = "Inativa";
		}else {
			return null;
		}
	}
	
	public static boolean verMatricula(Long id) {
		int aux = 0;
		List<Matricula> matriculas = Matricula.find("select m from Matricula m where m.aluno.id = ?", id).fetch();
		Matricula mat = null;
		for(int i=0; i < matriculas.size(); i++) {
			mat = matriculas.get(i);
			if(mat.turma.estado.equals("Ativa")) {
				aux = 1;
			}
		}
		if(aux == 1) {
			return true;
		}
		else {
			return false;
		}
	}
	*/
	public static int diminuirVaga(Turma turma) {
		return turma.vagasTurma -= 1;
	}
	
	public static Date pegaData(Matricula matricula) throws ParseException {
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date();
		String dStr = java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
		Date dataCadastro = formato.parse(dStr);
		return matricula.dataMatricula = dataCadastro;
	}	

	public static void listMatriculas() {
		List<Matricula> matriculas = Matricula.findAll();
		render(matriculas);
	}

	public static void removeMatricula(Long id, String senhaPadrao) throws ParseException {
		Setting setting = Setting.findById((long) 1);
		Matricula m = Matricula.findById(id);
		if(senhaPadrao.equals(setting.senhaPadrao)) {
			Turma t = Turma.findById(m.turma.id);
			if(t.estado == false) {
				t.vagasTurma += 1;
			}
			t.qtde -= 1;
			t.save();
			m.delete();
			flash.success("Matricula removida");
		}else {
			flash.error("Senha incorreta, tente novamente.");
		}	
		Alunos.detailAluno(m.aluno.id);	
	}

	public static void editMatricula() {
	}
}
