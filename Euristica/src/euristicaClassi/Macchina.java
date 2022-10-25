package euristicaClassi;

import java.util.List;

public class Macchina {
	
	private int machineId;
	private int c;
	private List<Job> jobSchedulati;
	
	public Macchina(int machineId, int c, List<Job> jobSchedulati) {
		super();
		this.machineId = machineId;
		this.c = c;
		this.jobSchedulati = jobSchedulati;
	}

	public int getMachineId() {
		return machineId;
	}

	public void setMachineId(int machineId) {
		this.machineId = machineId;
	}

	public int getC() {
		return c;
	}

	public void setC(int c) {
		this.c = c;
	}

	public List<Job> getJobSchedulati() {
		return jobSchedulati;
	}

	public void setJobSchedulati(List<Job> jobSchedulati) {
		this.jobSchedulati = jobSchedulati;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + machineId;
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
		Macchina other = (Macchina) obj;
		if (machineId != other.machineId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Macchina [machineId=" + machineId + ", c=" + c + "]";
	}
	
	
	
	
	
	

}
