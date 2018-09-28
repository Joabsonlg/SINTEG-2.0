package controllers;


import play.mvc.Before;
import play.mvc.Controller;

public class Secure extends Controller{
	
	@Before
	static void checkAutenticated() {
		if(session.get("user") == null) {
			Login.login();
		}else {
			
		}
	}
}
