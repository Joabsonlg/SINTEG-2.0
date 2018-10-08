package controllers;

import java.util.List;

import models.*;
import play.*;
import play.data.validation.Required;
import play.mvc.*;

@With(Secure.class)
public class Admins extends Controller{
	
	public static void autenticationAdmin(@Required String email, @Required String senha) {
    	if(validation.hasErrors()) {
            flash.error("Oops, Preencha todos os dados!");
            Application.index();
        }
    	inicio();
    }
    
    public static void inicio() {
    	String user = session.get("user");
    	List<Admin> admins = Admin.find("select a from Admin a where a.nomeAdmin = ?", user).fetch();
    	Admin admin = null;
    	for (int i = 0; i < admins.size(); i++) {
    		admin = admins.get(i);
		}
    	render(admin);
    }
    
    public static void configuracoes() {
    	render();
    }

    
    public static void detailAdmin(Long id) {
    	List<Uf> ufs = Uf.findAll();
    	Admin admin = Admin.findById(id);
    	renderTemplate("Admins/formCadAdmin.html", admin, ufs);
    }
	
	public static void formCadAdmin(Admin admin) {
		List<Uf> ufs = Uf.findAll();
		render(ufs);
	}
	
	public static void cadAdmin(Admin admin) {
		admin.save();
		listAdmin();
	}
	
	public static void removeAdmin(Long id){
		Admin admin = Admin.findById(id);
		admin.delete();
		listAdmin();
	}
	
	public static void listAdmin() {
		List<Admin> admins = Admin.findAll();
		render(admins);
	}
	
	public static void perfil(long id) {
		Admin admin = Admin.findById(id);
		render(admin);
	}
    
	public static void userPhoto(long id) { 
	   final Admin user = Admin.findById(id); 
	   response.setContentTypeIfNotSet(user.foto.type());
	   renderBinary(user.foto.get());
	} 

}
