package controllers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import models.*;
import play.*;
import play.data.validation.Required;
import play.db.jpa.Blob;
import play.mvc.*;

@With(Secure.class)
public class Professores extends Controller{
	
	public static void autenticationProfessor(@Required String email, @Required String senha) {
    	if(validation.hasErrors()) {
            flash.error("Oops, Preencha todos os dados!");
            Application.index();
        }
    	indexProfessor();
    }
	
	public static void indexProfessor() {
		render();
	}
	
	//@Administrador
	public static void formCadProfessor() {
		List<Uf> ufs = Uf.findAll();
		List<Escolaridade> niveis = Escolaridade.findAll();
		render(ufs, niveis);
	}
	
	//@Administrador
	public static void cadProfessor(Professor professor) {
		if(professor.save() != null) {
			flash.success(professor.nomeProfessor + " registrado");
			detailProfessor(professor.id);
		}else {
			flash.error("Erro ao cadastrar.");
			formCadProfessor();
		}
	}
	
	public static void ficha(Long id) throws ParseException {
		Professor prof = Professor.findById(id);
		render(prof);
	}
	
	public static void listProfessor() {
		List<Professor> professores = Professor.findAll();
		render(professores);
	}
	
	//@Administrador
	public static void removeProfessor(Long id) {
		Professor prof = Professor.findById(id);
		prof.delete();
		formCadProfessor();
	}
	
	//@Administrador
	public static void editProfessor(Long id) {

	}
	
	
	public static void detailProfessor(Long id) {			
		Professor professor = Professor.findById(id);
		String idade = calculaIdade(professor.dataNascimento);
		List<Uf> ufs = Uf.findAll();
		List<Escolaridade> niveis = Escolaridade.findAll();
		renderTemplate("Professores/formCadProfessor.html", professor, idade, ufs, niveis);
	}
	
	public static void pesquisaNome(String nome) {
		String nomeP = "%"+nome+"%";
		List<Aluno> alunos = Aluno.find("nomeAluno LIKE '"+nomeP+"'").fetch();
		List<Uf> ufs = Uf.findAll(); 
		List<Escolaridade> niveis = Escolaridade.findAll();
		renderTemplate("Alunos/formCadAluno.html", alunos, ufs, niveis);
	}
	
	public static void userPhoto(long id) { 
	   final Professor user = Professor.findById(id); 
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
