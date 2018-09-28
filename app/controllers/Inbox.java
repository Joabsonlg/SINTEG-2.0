package controllers;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import models.ContatoMail;
import play.data.validation.Valid;
import play.libs.Mail;
import play.mvc.Controller;

public class Inbox extends Controller{
	
	public static void inbox() {
		render();
	}
	
	public static void enviar(String para, String assunto, String mensagem) {
		
		SimpleEmail email = new SimpleEmail();
		try {
			email.setFrom("sender@zenexity.fr");
			email.addTo(para);
			email.setSubject(assunto);
			email.setMsg(mensagem);
			Mail.send(email);  
		} catch (Exception e) {
			e.printStackTrace();
		}
		Admins.inicio();
	}
	
	public static void detalhes(Long id) {
		ContatoMail email = ContatoMail.findById(id);
		render(email);
	}
	
	public static void listar() {
		List<ContatoMail> emails = ContatoMail.findAll();
		render(emails);
	}
}
