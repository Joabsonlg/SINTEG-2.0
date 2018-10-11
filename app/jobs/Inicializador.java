package jobs;

import models.Admin;
import models.Sala;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job{
	
	@Override
	public void doJob() throws Exception {
		
		if(Sala.count() == 0) {
			Sala sala = new Sala();
			sala.nome = "Belas Artes";
			sala.save();
		}
		
		
	}
	
}
