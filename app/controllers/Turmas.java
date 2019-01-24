package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.cache.spi.UpdateTimestampsCache;

import models.*;
import play.db.jpa.Blob;
import play.mvc.Controller;
import play.mvc.With;
@With(Secure.class)
public class Turmas extends Controller{
	
	public static void formCadTurma() {
		List<Curso> cursos = Curso.findAll();
		List<Professor> profs = Professor.findAll();
		if(cursos.size() == 0 || profs.size() == 0) {
			flash.error("Existem dependências a serem resolvidas. Provavelmente recadastramento de outras entidades.");
		}
		List<Sala> salas = Sala.findAll();
		render(cursos, profs, salas);
	}
	
	public static void encerraTurma(Long id) throws ParseException {
		Turma turma = Turma.findById(id);
		turma.estado = true;
		turma.save();
		List<Matricula> mats = Matricula.find("turma.id = ?", turma.id).fetch();
		Date dataTermino = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date();
		String dStr = DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
		dataTermino = formato.parse(dStr);		
		for (Matricula matricula : mats) {
			if(matricula.motivo == null && matricula.termino == null) {
				matricula.motivo = "Conclusão do curso";
				matricula.termino = dataTermino;
			}
			matricula.save();
		}
		detailTurma(id);
	}
	
	public static void printTurmas() {
		List<Turma> turmas = Turma.findAll();
		render(turmas);
	}
	
	public static void cadTurma(Turma turma){	
		/*if(turma.periodo == null) {
			//flash.error("Erro ao cadastrar! Existem dependências a serem resolvidas. Provavelmente recadastramento de outras entidades.");
			//formCadTurma();
		//}
		if(!dataValida(turma)) {
			flash.error("Data inválida");
			formCadTurma();
		}*/
				
		SimpleDateFormat formato = new SimpleDateFormat("HH:mm");
		String hini = params.get("turma.horaInicioAula");
		String hfim = params.get("turma.horaFimAula");
		Date inicio = null;
		Date fim = null;
		try {
			inicio = formato.parse(hini);
			fim = formato.parse(hfim);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		turma.horaInicioAula = inicio;
		turma.horaFimAula = fim;
		if(turma.save() != null) {
			flash.success("Turma salva");
			detailTurma(turma.id);
		}else {
			flash.error("Erro ao cadatrar");
			formCadTurma();
		}
	}
	
	/*static boolean dataValida(Turma turma) {
		
		Date data = null;
		SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
		Date d = new Date();
		String dStr = DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
		try {
			data = formato.parse(dStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if((turma.dataInicioAula.compareTo(period.inicioPeriodo) > 0) && (data.compareTo(period.fimPeriodo) < 0)) {
			return true;
		}else {
			return false;
		}
		
	}*/
	
	public static void pesquisaNome(String nome) {
		String nomeP = "%"+nome+"%";
		List<Turma> turmas = Turma.find("codigo LIKE '"+nomeP+"'").fetch();
		renderTemplate("Turmas/formCadTurma.html", turmas);
	}
	
	public static void listTurma() {
		List<Turma> turmas = Turma.findAll();
		render(turmas);
	}
	
	public static void detailTurma(Long id) {
		Turma turma = Turma.findById(id);
		List<Curso> cursos = Curso.findAll();
		List<Sala> salas = Sala.findAll();
		List<Matricula> matriculas = Matricula.find("turma.id = ?", turma.id).fetch();
		renderTemplate("Turmas/formCadTurma.html", turma, cursos, salas, matriculas);
	}
	
	public static void removeTurma(Long id) {
		Turma t = Turma.findById(id);
		t.delete();
		formCadTurma();
	}
	
	public static void editTurma(Turma turma) {
		turma.refresh();
		listTurma();
	}
}
