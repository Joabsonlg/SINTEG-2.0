package controllers;


import play.mvc.Before;
import play.mvc.Controller;

public class Secure extends Controller{
	
	@Before
	static void checkAutenticated() {
		if(session.get("user") == null) {
			Login.login();
		}else {
			String perfil = session.get("type");
			Administrador adminAnnotation =	getActionAnnotation(Administrador.class);
			if (adminAnnotation != null && !"Admin".equals(perfil)) {
				flash.error("Acesso restrito aos administradores do sistema");
				Admins.inicio();
			}
		}
	}
}
