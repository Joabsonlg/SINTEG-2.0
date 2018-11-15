package jobs;

import models.Admin;
import models.Escolaridade;
import models.Sala;
import models.Uf;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job{
	
	@Override
	public void doJob() throws Exception {
		
		if(Admin.count() == 0) {
			Admin admin = new Admin();
			admin.nomeAdmin = "Admin";
			admin.username = "admin";
			admin.senhaAdmin = "123";
			admin.save();
		}
		if(Uf.count() == 0) {
			Uf uf = new Uf();
			uf.nome = "RN";
			uf.save();
		}
		if(Escolaridade.count() == 0) {
			Escolaridade esc = new Escolaridade();
			esc.nome = "Ensino Médio Completo";
			esc.save();
		}
		if(Sala.count() == 0) {
			Sala sala = new Sala();
			sala.nome = "Belas Artes";
			sala.save();
		}
		
		
	}
	
}
