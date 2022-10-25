package euristicaClassi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;


public class Problem {
	
	List<Macchina> macchine= new LinkedList<>();
	List<Job> jobs=  new LinkedList<>();
	List<Conflitto> conflitti =  new LinkedList<>();
	double probConfl;
	//int nJobsPerMatheur= 120;
	
	public Macchina inserisciMacchina(int id) {
		Macchina temp= new Macchina(id, 0, null);
		this.macchine.add(temp);
		return temp;
		
	}
	
	public Macchina macchina(int id) {
		for(Macchina m: this.macchine) {
			if(m.getMachineId()==id) {
				return m;
			}
		}
		return null;
	}
	
	
	public Job inserisciJob(int id, int p) {
		Job temp= new Job(id, p, null, new LinkedList<Integer>());
		this.jobs.add(temp);
		return temp;
	}
	
	public Job job(int id) {
		for(Job j :this.jobs) {
			if(j.getId()==id) {
				return j;
			}
		}
		return null;
	}
	
	public void setProbConfl(double probConfl) {
		this.probConfl=probConfl;
	}
	
	public Conflitto inserisciConflitto(int id1, int id2) {
		Conflitto temp= new Conflitto(id1, id2);
		
		if(this.job(id1).getConflitti()==null) {
			this.job(id1).setConflitti(new LinkedList<>());
			
		}
		
		if(this.job(id2)!=null && this.job(id1)!= null) 
		   this.job(id1).getConflitti().add(id2);
		  
		if(this.job(id2).getConflitti()==null) 
			this.job(id2).setConflitti(new LinkedList<>());
		
		
		if(this.job(id2)!=null && this.job(id1)!= null) {
		   this.job(id2).getConflitti().add(id1);
		   
		}
		return temp;
		
	}
	
	public List<Job> jobOrdinati(){
		LinkedList<Job> jobsOrdinati= new LinkedList<>(this.jobs);
		ComparatoreDiJobs comparatore= new ComparatoreDiJobs();
		
		Collections.sort(jobsOrdinati, comparatore);
		
		return jobsOrdinati;
		
	}
	
	public  List<Job> jobOrdinati2(){
		LinkedList<Job> jobsOrdinati2= new LinkedList<>(this.jobs);
		ComparatoreDiJobs2 comparatore2= new ComparatoreDiJobs2();
		
		Collections.sort(jobsOrdinati2, comparatore2);
		
		return jobsOrdinati2;
		
	}
	
	public  List<Job> jobOrdinati3(){
	
		ComparatoreDiJobs comparatoreParz1= new ComparatoreDiJobs(); 
		ComparatoreDiJobs2 comparatoreParz2= new ComparatoreDiJobs2(); 
		LinkedList<Job> primaMeta= new LinkedList<>();
		LinkedList<Job> secondaMeta= new LinkedList<>();
		LinkedList<Job> listaOrdinata= new LinkedList<>();
		
		int size=this.jobs.size();
		for(int i=0; i<size; i++) {
			Job temp= this.jobs.get(i);
			if(i<this.probConfl*size) {
				primaMeta.add(temp);
			}else {
				secondaMeta.add(temp);
			}
		}
		
		Collections.sort(primaMeta, comparatoreParz1);
		Collections.sort(secondaMeta, comparatoreParz2);
		listaOrdinata.addAll(primaMeta);
		listaOrdinata.addAll(secondaMeta);
		
		return listaOrdinata;
		
	}
	
	public boolean schedula() {
		boolean ammissibile= true;
		
		//schedulo i primi m jobs (da 0 a m-1)
		for(int i=0; i<this.macchine.size(); i++) {
			Macchina tempM= macchine.get(i);
			tempM.setJobSchedulati(new LinkedList<>());
			Job tempJ=this.jobOrdinati().get(i);
			tempM.getJobSchedulati().add(tempJ);
			tempM.setC(tempJ.getP());
			tempJ.setMacchinaACuiVieneAssegnato(tempM);
			
		}
		
		//schedulo dal job m al job n
		int nJobs=this.jobOrdinati().size();
		
		for(int i=this.macchine.size(); i<nJobs/*this.nJobsPerMatheur*/ && ammissibile==true; i++) {
			Job temp= this.jobOrdinati().get(i);
			if(this.macchineAmmissibili(temp)==null) {
				ammissibile= false;
				break;
			}
		    Macchina scelta= this.macchinaConMinimoC(this.macchineAmmissibili(temp));
		    this.job(temp.getId()).setMacchinaACuiVieneAssegnato(scelta);
		    this.macchina(scelta.getMachineId()).getJobSchedulati().add(temp);
		    this.macchina(scelta.getMachineId()).setC(scelta.getC()+temp.getP());
		}
		
		return ammissibile;
		
	}
	
	public Macchina macchinaConMinimoC(List<Macchina> listaMacchine) {
		int minimo = Integer.MAX_VALUE;
		Macchina macchinaMin=null;
		
		for(Macchina m: listaMacchine) {
			if(m.getC()<minimo) {
				minimo= m.getC();
				macchinaMin=m;
			}
		}
		
		return macchinaMin;
	}
	
	public List<Macchina> macchineAmmissibili(Job j){
		LinkedList<Macchina> macchineAmmissibili= new LinkedList<>(this.macchine);
		
		for(Macchina m: this.macchine) {
		    for(Job job : m.getJobSchedulati()) {
		    	for(int i=0; i<j.getConflitti().size(); i++) {
				    if(job.getId()==j.getConflitti().get(i)) {
				    	macchineAmmissibili.remove(m);
				    }
		    	}
			}
		}
		if(macchineAmmissibili.size()==0) {
			return null;
		}
		
		return macchineAmmissibili;
	}
	
	public int Makespan() {
		int max = Integer.MIN_VALUE;
		
		for(Macchina m: this.macchine) {
			//System.out.println(m.getC());
			if(m.getC()>max) {
				
				max= m.getC();
			}
		}
		
		return max;
	}
	
	public void leggiProcessTime(String nomeFile) {
		try {
			FileReader fr = new FileReader(nomeFile);
			BufferedReader br = new BufferedReader(fr);
			
			String riga;
			int id=1;
			while( (riga = br.readLine()) != null ) {
				
				String pString= riga;
				int p= Integer.valueOf(pString);
				this.inserisciJob(id, p);
				id++;
				
			}
			br.close();
			fr.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public int leggiConflitti(String nomeFile) {
		try {
			FileReader fr = new FileReader(nomeFile);
			BufferedReader br = new BufferedReader(fr);
			
			String riga;
		    int nConfl=0;
			while( (riga = br.readLine()) != null ) {
				if(riga.contains(",")) {
				  nConfl++;
				  String campi[] = riga.split(",");
				  int id1= Integer.parseInt(campi[0]);
				  int id2= Integer.parseInt(campi[1]);
				  this.inserisciConflitto(id1, id2);
				}
			}
			br.close();
			fr.close();
			return nConfl;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		
		
	}
	
	public List<Job> jobOrdinati4(){
		LinkedList<Job> jobsOrdinati2= new LinkedList<>(this.jobs);
		ComparatoreDiJobs2 comparatore2= new ComparatoreDiJobs2();
		
		Collections.sort(jobsOrdinati2, comparatore2);
		
		int nJobs=this.jobs.size();
		int nMac=this.macchine.size();
		double nDivisoM= (double)nJobs/nMac;
		
	    int nDivisoMInt= (int)nJobs/nMac;
	    //System.out.println(nDivisoM);
	    //System.out.println(nDivisoMInt);
	    int jobsTotali=nJobs;
		if(nDivisoM!=nDivisoMInt) {
		   int interoSup= nDivisoMInt+1;
		   int jobsDaAgg= (interoSup*nMac)-nJobs;
		   jobsTotali+=jobsDaAgg;
		   for(int i=nJobs+1; i<=jobsTotali; i++) {
			   Job temp= new Job(i, 0,  null, new LinkedList<Integer>());
			   jobsOrdinati2.add(temp);
		   }
		}
		
		List<Job> listaOutput= new LinkedList<>();
		
		int nTuple= jobsTotali/nMac;
		//System.out.println(nTuple);
		int inizioTupla[]= new int[nTuple];
		int fineTupla[]= new int[nTuple];
		
		TreeMap<Double, LinkedList<Job>> mappeSlackJobs= new TreeMap<>(Collections.reverseOrder());
		LinkedList<Job> temp= new LinkedList<>();
		inizioTupla[0]=0;
		fineTupla[0]=nMac;
		
		for(int i=0; i<nTuple; i++) {
			if(i==0) {
				int slack=jobsOrdinati2.get(inizioTupla[i]).getP()-jobsOrdinati2.get(fineTupla[i]-1).getP();
				int nConflittiTupla=0;
				for(int j=inizioTupla[i]; j<fineTupla[i]; j++) {
					temp.add(jobsOrdinati2.get(j));
					nConflittiTupla+=jobsOrdinati2.get(j).getConflitti().size();
			    }
                double indiceD=(double) (nTuple-i)/10000000;
				
				Double nConflittiTuplaD=(double)(nConflittiTupla)/10000;
				double slackD=(double)slack*10;
				double key=slackD+nConflittiTuplaD+indiceD;
				//System.out.println(key);
				mappeSlackJobs.put(key, new LinkedList<>(temp));
			    temp.clear();
			}else {
				inizioTupla[i]=fineTupla[i-1];
				fineTupla[i]=inizioTupla[i]+nMac;
				int slack=jobsOrdinati2.get(inizioTupla[i]).getP()-jobsOrdinati2.get(fineTupla[i]-1).getP();
				int nConflittiTupla=0;
				for(int j=inizioTupla[i]; j<fineTupla[i]; j++) {
					temp.add(jobsOrdinati2.get(j));
					nConflittiTupla+=jobsOrdinati2.get(j).getConflitti().size();
			    }
				double indiceD=(double) (nTuple-i)/10000000;
				
				Double nConflittiTuplaD=(double)(nConflittiTupla)/10000;
				double slackD=(double)slack*10;
				double key=slackD+nConflittiTuplaD+indiceD;
				//System.out.println(key);
				mappeSlackJobs.put(key, new LinkedList<>(temp));
			    temp.clear();
			}
	    }
		//System.out.println(mappeSlackJobs.size());
		
		for(double i: mappeSlackJobs.keySet()) {
			//System.out.println(i+" ");
			listaOutput.addAll(mappeSlackJobs.get(i));
			
		}
		
		return listaOutput;
	}
	
	/*public List<Boolean> cambiaDatiPerMathEur() {
		int nJobsTot= this.jobOrdinati().size();
		int idN=1;
		LinkedList<Boolean> listaJobMacchineVietate= new LinkedList<>();
		//Map<Integer, LinkedList<Integer>> mappaJobMacchineVietate= new TreeMap<>();
		for(int i=this.nJobsPerMatheur; i<nJobsTot; i++) {
			Job temp =this.jobOrdinati().get(i);
			temp.setNuovoId(idN);
			
			List<Integer> macchineVietate= new LinkedList<>();
			for(int j=0; j<temp.getConflitti().size(); j++) {
				for(Macchina m: this.macchine) {
					if(m.getJobSchedulati().contains(this.job(j)) ) {
						macchineVietate.add(m.getMachineId());
						
					}
				}
			}
			
			for(Macchina m: this.macchine) {
				if(macchineVietate.contains(m.getMachineId())) {
					listaJobMacchineVietate.add(true);
				}else {
					listaJobMacchineVietate.add(false);
				}
			}
			//mappaJobMacchineVietate.put(idN,new LinkedList<>(macchineVietate));
			idN++;
		}
		
		
			
				
		//for(Boolean b: listaJobMacchineVietate) {
		//	System.out.print(b);
		//}
		/*for(Integer j: mappaJobMacchineVietate.keySet()) {
			System.out.println(mappaJobMacchineVietate.get(j).size())
		}*/
		
		/*System.out.println("\n\n Job, MacchinaVietata:");
		for(Integer j: mappaJobMacchineVietate.keySet()) {
			for(Integer m: mappaJobMacchineVietate.get(j)) {
				System.out.println(j+","+m);
			}
		}*/
		//System.out.println("Coppie j,m= "+listaJobMacchineVietate.size());
	/*	
		return listaJobMacchineVietate;
	}
	
	public List<Boolean> ottieniConflPerMathEur() {
		List<Boolean> result= new LinkedList<>();
		int nJobsTot= this.jobOrdinati().size();
		for(int j=this.nJobsPerMatheur; j<nJobsTot; j++) {
			for(int k=this.nJobsPerMatheur; k<nJobsTot; k++) {
					if(this.jobOrdinati().get(k).getNuovoId()>this.jobOrdinati().get(j).getNuovoId()) {
					if(this.jobOrdinati().get(j).getConflitti().contains((this.jobOrdinati().get(k).getId()))) {
						result.add(true);
					}else {
						result.add(false);
					}
					}
				}
			
			}
		//System.out.println("Coppie j,j= "+result.size());
		
		return result;
		/*System.out.println("\n\n");
		
		for(String s: result) {
			System.out.println(s);
		}*/
	
	//}
	
	public void stampaProcessTime() {
		int nJobsTot= this.jobOrdinati().size();
		System.out.println("\n\n");
		for(int i=0; i<nJobsTot; i++) {
			Job temp =this.jobOrdinati().get(i);
			System.out.println(temp.getP());
		}
	}
	
	/*public void fileJobMacchineVietate() {
		try {
		 File fileJMV = new File("inputJMV.txt");
	     fileJMV.createNewFile();
	     FileWriter myWriter = new FileWriter("inputJMV.txt");
	     String s="";
	     for(Boolean b: this.cambiaDatiPerMathEur()) {
	 		s+=b+"\n";
	 	 }
	     myWriter.write(s);
	     myWriter.close();
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	}
	
	public void fileConflitti() {
		try {
			 File fileConfl = new File("inputConfl.txt");
		     fileConfl.createNewFile();
		     FileWriter myWriter = new FileWriter("inputConfl.txt");
		     String s="";
		     for(Boolean b: this.ottieniConflPerMathEur()) {
		 		s+=b+"\n";
		 	 }
		     myWriter.write(s);
		     myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}*/
	
	public void fileXIJ(){
		try {
			 File fileXIJ = new File("inputXIJ.txt");
		     fileXIJ.createNewFile();
		     FileWriter myWriter = new FileWriter("inputXIJ.txt");
		    
		     String s="";
		    
		    
		     for(Macchina m: this.macchine) {
		    	 for(Job j: this.jobs) {
		    		 if(m.getJobSchedulati().contains(j)) {
		    			 s+="1 \n";
		    			 
		    		 }else {
		    			 s+="0 \n";
		    			 
		    		 }
		    	 }
		     }
		    
		   
		   
		     myWriter.write(s);
		     myWriter.close();
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
		
	}
	
	public void fileConflicts() {
		try {
		File fileCon = new File("inputConflicts.txt");
	     fileCon.createNewFile();
	     FileWriter myWriter = new FileWriter("inputConflicts.txt");
	    
	     String s="";
	    
	    
	     for(Job j: this.jobs) {
	    	 for(Job k: this.jobs) {
	    		 if(k.getId()>j.getId()) {
	    			 
	    			 if(j.getConflitti().contains(k.getId())) {
	    				 s+="true \n";
	    				
	    			 }else {
	    				 s+="false \n";
	    				 
	    			 }
	    			 
	    			 	    			 
	    		 }
	    	 }
	     }
	    
	    
	   
	     myWriter.write(s);
	     myWriter.close();
	    } catch (IOException e) {
	      System.out.println("An error occurred.");
	      e.printStackTrace();
	    }
	

		
	}
	
	
}
