import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class ServiceCounter {
	
	public static int timeToInt(String time) {
		String[] temp = time.split(":");
		//System.out.println(temp[0] + " " + temp[1] + " " + temp[2]);
		int hours  = Integer.parseInt(temp[0]);
		if (hours < 8) {
			hours += 12;
		}
		return (((hours * 60) + Integer.parseInt(temp[1])) * 60) + Integer.parseInt(temp[2]);
	}
	
	public static String intToTime(int seconds) {
		int hh = seconds / 3600;
		int mm = ((seconds) / 60) % 60;
		int ss = seconds % 60;
		return String.format("%02d",hh) + ":" + String.format("%02d",mm) + ":" + String.format("%02d",ss);
	}
	
	public static void main(String args[]) throws FileNotFoundException, ParseException {
		
		int serviceTime;
		int currentTime;
		Queue<Customer> queue = new Queue<Customer>();
		int longestBreak = 0;
		int customersServed = 0;
		int totalBreakTime = 0;
		int CLOSING_TIME = timeToInt("5:00:00");
		int OPENING_TIME = timeToInt("9:00:00");
		ArrayList<Customer> customerData = new ArrayList<Customer>();
		int currentCustomer = 0;
		
		//import data
		if (!(args[0].equals("customersfile.txt") && args[1].equals("queriesfile.txt"))) {
			System.out.println("Please input appropriate data files as arguments.\nPress 'Enter' to exit.");
			Scanner scan = new Scanner(System.in);
			scan.nextLine();
			scan.close();
			System.exit(0);
		}
		
		//read customer file
		Scanner scan = new Scanner(new File(args[0]));
		
		String temp = scan.nextLine();
		serviceTime = Integer.parseInt(temp);
		//System.out.println("Service time = " + serviceTime);
		scan.nextLine();
		
		//Scan in customers
		while (scan.hasNextLine()) {
			String id = scan.nextLine();
			//System.out.println(id);
			String arrival = scan.nextLine();
			//System.out.println(arrival);
			
			//parse for the integer value
			for (int i = 0; i < id.length(); i++) {
				if (Character.isDigit(id.charAt(i))){
					id = id.substring(i);
					break;
				}
			}
			//System.out.println(id);
			
			//parse for the integer value
			for (int i = 0; i < arrival.length(); i++) {
				if (Character.isDigit(arrival.charAt(i))){
					arrival = arrival.substring(i);
					break;
				}
			}
			//System.out.println(arrival);
			//System.out.println(timeToInt(arrival));
			
			//System.out.println(id + "," + timeToInt(arrival));
			
			//Create temp customer
			Customer nextCustomer = new Customer(Integer.parseInt(id),timeToInt(arrival));
			//System.out.println(nextCustomer);
			customerData.add(nextCustomer);			
			
			//end of customers
			if (scan.hasNextLine())
				scan.nextLine();
		}
		
		scan.close();
		//System.out.println(customerData);
		
		//Add all the customers before opening time to the Queue
		for (Customer c: customerData) {
			if (c.getTimeOfArrival() <= OPENING_TIME) {
				queue.add(c);
				currentCustomer = customerData.indexOf(c);
			}
		}
		
		//set current time
		currentTime = OPENING_TIME;
		//System.out.println(OPENING_TIME);
		//System.out.println(CLOSING_TIME);
		//System.out.println(customerData.get(3));
		//queue.add(customerData.get(0));
		//System.out.println(queue.remove());
		//System.out.println(queue);
		
		//SERVICE TIME
		//else if the Queue is empty start a break
		
		while (currentCustomer < customerData.size() - 1 || !queue.isEmtpy()) {
			//System.out.println(intToTime(currentTime));
			//System.out.println(queue + "\n\n");
			if (queue.isEmtpy()) {
					
				//check next customer to calculate break time
				int nextCustomer = currentCustomer + 1;
					
				//did the customer come before closing?
				if (customerData.get(nextCustomer).getTimeOfArrival() < CLOSING_TIME) {
					
					//record break time until next customer
					//System.out.println("Breaking until " + intToTime(customerData.get(nextCustomer).getTimeOfArrival()));
					totalBreakTime += customerData.get(nextCustomer).getTimeOfArrival() - currentTime;
					//updateLongest break
					if ((customerData.get(nextCustomer).getTimeOfArrival() - currentTime) > longestBreak) {
						longestBreak = customerData.get(nextCustomer).getTimeOfArrival() - currentTime;
					}
					//add the next customer to the queue
					//System.out.println("Adding " + customerData.get(nextCustomer).getIdNumber() + " to the queue.");
					queue.add(customerData.get(nextCustomer));
					currentCustomer++;
					currentTime = customerData.get(currentCustomer).getTimeOfArrival();
					
				} else {
					//break until close
					totalBreakTime += CLOSING_TIME - currentTime; //time of last served customer
				}
					
			} else { //if the queue is not empty serve a customer 
				
				int nextCustomer = currentCustomer + 1;
				
				//did anyone come at the time of service?
				while (currentCustomer < customerData.size() - 1) {
					if (customerData.get(nextCustomer).getTimeOfArrival() <= (currentTime)
							&& customerData.get(nextCustomer).getTimeOfArrival() < CLOSING_TIME) {
						//System.out.println("A. Adding " + customerData.get(nextCustomer).getIdNumber() + " to the queue.");
						queue.add(customerData.get(nextCustomer));
						currentCustomer++;
						nextCustomer++;
					} else {
						break;
					}
				}
				
				//service the queue
				//record waiting time of the customer & remove from queue
				queue.remove().waitTime(currentTime);
				customersServed++;
				currentTime += serviceTime;	
				
				//did anyone come during service?
				while (currentCustomer < customerData.size() - 1) {
					if (customerData.get(nextCustomer).getTimeOfArrival() < (currentTime + serviceTime)
							&& customerData.get(nextCustomer).getTimeOfArrival() < CLOSING_TIME) {
						//System.out.println("Adding " + customerData.get(nextCustomer).getIdNumber() + " to the queue.");
						queue.add(customerData.get(nextCustomer));
						currentCustomer++;
						nextCustomer++;
					} else {
						break;
					}
				}

			}
			
		} 
		
		/*
		System.out.println(customerData);
		System.out.println("Longest Break: " + intToTime(longestBreak));
		System.out.println("Total Break Time: " + intToTime(totalBreakTime));
		System.out.println("Potential Customers Today: " + customerData.size());
		System.out.println("Longest Queue Length: " + queue.getLength());
		*/
		
		//Read Queries
		Scanner reader = new Scanner(new File(args[1]));
		
		while (reader.hasNextLine()) {
			temp = reader.nextLine();
			String[] lineText = temp.split("\\s+");
			switch (lineText[0]) {
				case "NUMBER-OF-CUSTOMERS-SERVED": {
					System.out.println("NUMBER-OF-CUSTOMERS-SERVED: " + customersServed);
				}
				break;
				case "LONGEST-BREAK-LENGTH": {
					System.out.println("LONGEST-BREAK-LENGTH: " + intToTime(longestBreak));
				}
				break;
				case "TOTAL-IDLE-TIME": {
					System.out.println("TOTAL-IDLE-TIME: " + intToTime(totalBreakTime));
				}
				break;
				case "MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME": {
					System.out.println("MAXIMUM-NUMBER-OF-PEOPLE-IN-QUEUE-AT-ANY-TIME: " + queue.getLength());
				}
				break;
				case "WAITING-TIME-OF": {
					int tempId = Integer.parseInt(lineText[1]);
					if (tempId <= customerData.size() && tempId > 0) {
						System.out.println("WAITING-TIME-OF " + lineText[1] + ": " +
								intToTime(customerData.get(tempId-1).getWaitTime()));
					}
				}
			}
		}
		reader.close();

		
	}

}
