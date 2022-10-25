package euristicaClassi;

import java.util.Comparator;

public class ComparatoreDiJobs implements Comparator<Job>{

	//Ordina i jobs per numero di conflitti descrescente; a parità di numero di 
	//conflitti, ordina i job per process time descrescente
	@Override
	public int compare(Job j1, Job j2) {
		
		if(j1.getConflitti().size()!=j2.getConflitti().size()) {
			return -Integer.compare(j1.getConflitti().size(), 
					j2.getConflitti().size());
		}else {
			return -Integer.compare(j1.getP(), j2.getP());
		}
		
	}

}
