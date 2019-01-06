package controllers;

import play.mvc.Controller;
import play.mvc.With;
import antlr.collections.List;
import models.*;

@With(Secure.class)
@Administrador
public class Settings extends Controller{
	
	@Administrador
	public static void index() {
		Long id = (long) 1;
		Setting setting = new Setting().findById(id);
		render(setting);
	}

	public static void addSett(Setting setting){
		if(setting.save() != null) {
			flash.success("Configurações atualizadas");
		}else {
			flash.error("Erro!");
		}
		index();
	}
	
}
