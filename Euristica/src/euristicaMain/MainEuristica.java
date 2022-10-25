package euristicaMain;

import euristicaClassi.Problem;

public class MainEuristica {

	public static void main(String[] args) {
		
		long start =System.currentTimeMillis();
		Problem scheduling= new Problem();
		int macchine= 50;
		int jobs= 400;
		
		
		for(int i=1; i<=macchine; i++) {
			scheduling.inserisciMacchina(i);
		}
		
		/*for(int i=1; i<=jobs; i++) {
			double pD=50+50*Math.random();
			int pRandom= (int)pD;
			scheduling.inserisciJob(i, pRandom); 
		}*/
		
		//
		
		
		scheduling.leggiProcessTime("inputProcessTime.txt");
		int nConfl=scheduling.leggiConflitti("inputConflitti.txt");
		double probConfl=(double)(2*nConfl)/((jobs*(jobs-1)));
		scheduling.setProbConfl(probConfl);
		
		
		
		
		/*for(int j=1; j<=jobs; j++) {
			for(int k=1; k<=jobs; k++) {
				if(k>j ) {
					double alea=Math.random();
					if(alea<=probConfl) {
				//System.out.println(j+","+k);
					scheduling.inserisciConflitto(j, k);
				}
			}
		}
		}*/
		
		
		if(scheduling.schedula()) {
			System.out.println("Il makespan calcolato con l'euristica consederata è: "+scheduling.Makespan()+"\nIl tempo di esecuzione è: "+ (System.currentTimeMillis()-start) +" ms");
			
			scheduling.fileXIJ();
			scheduling.fileConflicts();
			
		}else {
			System.out.print("L'euristica considerata non riesce a trovare una soluzione ammissibile per gli input inseriti!");
		}
		
		

	}

}
