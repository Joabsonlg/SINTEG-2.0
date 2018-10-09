package controllers;

import java.util.Date;
import java.util.List;

import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import models.*;
import play.libs.Mail;
import play.mvc.Controller;

public class Login extends Controller{
	
	public static void login(){
		render();
	}
	
	public static void autenticar(String username, String pass) {
		Admin admin = Admin.find("nome = ? and senhaAdmin = ?", username, pass).first();
		if(admin == null) {
			Professor prof = Professor.find("username = ? and senha = ?", username, pass).first();
			if(prof != null) {
				session.put("user", prof.username);
				session.put("userId", prof.id);
				session.put("type", "Professor");
				Admins.inicio();
			}else {
				flash.error("Usuário ou senha inválidos.");
				params.flash();
				login();
			}
		}else {
			session.put("user", admin.username);
			session.put("userId", admin.id);
			session.put("type", "Admin");
			Admins.inicio();
		}
	}
	
	public static void enviar(String para, String nome, Date dataNasc) {
		
		Admin admin = Admin.find("nomeAdmin = ? and email = ? and dataNascimento = ?", nome, para, dataNasc).first();
		if(admin != null) {
			HtmlEmail email = new HtmlEmail();
			try {
				email.setFrom("funffes@gmail.com");
				email.addTo(para);
				email.setSubject("Recuperação da conta");
				email.setMsg(
						"<!DOCTYPE html>\r\n" + 
						"<html>\r\n" + 
						"<head>\r\n" + 
						"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\"/>\r\n" + 
						"<title>Login</title>\r\n" + 
						"	<meta charset=\"UTF-8\">\r\n" + 
						"	<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">	\r\n" + 
						"<!--===============================================================================================-->\r\n" + 
						"	<link href=\"@{'/public/vendors/bootstrap/dist/css/bootstrap.min.css'}\" rel=\"stylesheet\">\r\n" + 
						"<!--===============================================================================================-->\r\n" + 
						"	<link href=\"@{'/public/vendors/font-awesome/css/font-awesome.min.css'}\"	rel=\"stylesheet\">\r\n" + 
						"<!--===============================================================================================-->	\r\n" + 
						"	<link rel=\"stylesheet\" type=\"text/css\" href=\"@{'/public/vendors/css-hamburgers/hamburgers.min.css'}\">\r\n" + 
						"<!--===============================================================================================-->\r\n" + 
						"	<link rel=\"stylesheet\" type=\"text/css\" href=\"@{'/public/vendors/select2/dist/css/select2.min.css'}\">\r\n" + 
						"<!--===============================================================================================-->\r\n" + 
						"	<link rel=\"stylesheet\" type=\"text/css\" href=\"@{'/public/stylesheets/cssLogin/util.css'}\">\r\n" + 
						"	<link rel=\"stylesheet\" type=\"text/css\" href=\"@{'/public/stylesheets/cssLogin/main.css'}\">\r\n" + 
						"<!--===============================================================================================-->\r\n" + 
						"</head>\r\n" + 
						"<body>\r\n" + 
						"	<h1>Username: " + admin.nomeAdmin +"</h1>\r\n" + 
						"	<h1>Senha: " + admin.senhaAdmin +"</h1>\r\n" + 
						"</body>\r\n" + 
						"</html>");
				Mail.send(email);  
			} catch (Exception e) {
				e.printStackTrace();
			}
			flash.success("<script>alert('Verifique seu E-mail para continuar.');</script>");
			params.flash();
			login();
		}else {
			flash.error("Erro no processamento, verifique se as informações estão corretas.");
			params.flash();
			resetPass();
		}
	}
	
	public static void resetPass() {
		render();
	}
	
	public static void logout() {
		session.clear();
		System.out.println("logout");
		login();
	}
}
