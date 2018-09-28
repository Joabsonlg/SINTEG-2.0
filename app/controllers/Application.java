package controllers;

import play.*;
import play.data.validation.Required;
import play.mvc.*;

import java.util.*;

import models.*;

@With(Secure.class)
public class Application extends Controller {

    public static void index() {
        render();
    }
    
    public static void ajuda() {
    	render();
    }
    
    public static void esqueceuSenha(){
    	render();
    }
    
    public static void login() {
    	render();
    }
}