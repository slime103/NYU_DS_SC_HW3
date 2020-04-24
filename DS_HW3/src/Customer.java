
public class Customer {
	
	private int idNumber;
	private int timeOfArrival;
	private int waitTime = 0;
	
	public Customer(int id, int arrival) {
		idNumber = id;
		timeOfArrival = arrival;
	}
	
	public int getIdNumber() {
		return idNumber;
	}
	
	public int getTimeOfArrival() {
		return timeOfArrival;
	}
	
	public void waitTime(int timeOfService) {
		waitTime = timeOfService - timeOfArrival;
	}
	
	public int getWaitTime() {
		return waitTime;
	}
	
	public String toString() {
		return "Customer - ID number: " + idNumber + "\nTime of Arrival: " + timeOfArrival
				+ "\nWaiting Time: " + waitTime + "\n";
	}
}
