package euristicaClassi;

import java.util.Comparator;

public class ComparatoreDiJobs2 implements Comparator<Job>{

	//Ordina i jobs per process time descrescente; a parità di process
	//time, ordina per numero di conflitti decrescente
	@Override
	public int compare(Job j1, Job j2) {
		
		if(j1.getP()!= j2.getP()) {
			return -Integer.compare(j1.getP(), j2.getP());
		}else {
			return -Integer.compare(j1.getConflitti().size(), 
					j2.getConflitti().size());
		}
		
	}
}
