package controllers;

import play.mvc.Controller;
import play.mvc.With;

@With(Secure.class)
@Administrador
public class Settings extends Controller{
	
	@Administrador
	public static void index() {
		render();
	}
	
}
