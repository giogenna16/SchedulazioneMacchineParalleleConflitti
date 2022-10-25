package euristicaClassi;

import java.util.List;

public class Job {
	private int id;
	private int p;
	private Macchina macchinaACuiVieneAssegnato;
	private List<Integer> conflitti;
	//private Integer nuovoId;
	
	public Job(int id, int p, Macchina macchinaACuiVieneAssegnato, List<Integer> conflitti) {
		
		this.id = id;
		this.p = p;
		this.macchinaACuiVieneAssegnato = macchinaACuiVieneAssegnato;
		this.conflitti = conflitti;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getP() {
		return p;
	}

	public void setP(int p) {
		this.p = p;
	}

	public Macchina getMacchinaACuiVieneAssegnato() {
		return macchinaACuiVieneAssegnato;
	}

	public void setMacchinaACuiVieneAssegnato(Macchina macchinaACuiVieneAssegnato) {
		this.macchinaACuiVieneAssegnato = macchinaACuiVieneAssegnato;
	}

	public List<Integer> getConflitti() {
		return conflitti;
	}

	public void setConflitti(List<Integer> conflitti) {
		this.conflitti = conflitti;
	}

	@Override
	public String toString() {
		return "Job [id=" + id + ", p=" + p + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Job other = (Job) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/*public Integer getNuovoId() {
		return nuovoId;
	}

	public void setNuovoId(Integer nuovoId) {
		this.nuovoId = nuovoId;
	}*/


}
