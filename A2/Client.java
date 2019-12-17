

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
	private Client() {}
	public static void main(String[] args) {

		String host = (args.length < 1) ? null : args[0];
		try {
			Registry registry = LocateRegistry.getRegistry(host);
			VoterInterface stub = (VoterInterface) registry.lookup("election");
			// String response = stub.sayHello();
			//    System.out.println("response: " + response);
			String [] Candidates= { "A","B","C","D","E","F","G","H","I","J"};
			while(true) {
				System.out.println("Please choose one option\n1. Register to Vote\r\n" + "2. Verification of Voter\r\n" + "3. Vote for candidates\r\n" + "4. Calculate Results\r\n" + "5. Check the Winner\r\n" + "0. Exit ");
				Scanner sc=new Scanner(System.in);
				int choice=sc.nextInt();
				String name=sc.nextLine();
				switch(choice)
				{
				case 1:
				{
					System.out.println("Enter voter name");
					name=sc.nextLine();
					String id=stub.registerVoter(name);
					System.out.println("voter id is "+ id);
					break;
				}
				case 2:
				{
					System.out.println("Enter your voter id");
					Integer d=  sc.nextInt();
					String id= d.toString();
					boolean vote=stub.verifyVoter(id);
					if(vote) {
						System.out.println("already voted");
					}
					else System.out.println("not yet voted");
					}
					break;
				case 3:
				{
					System.out.println("Pleas enter the voter id\n");
					Integer d=  sc.nextInt();
					String id= d.toString();
					System.out.println("Please select your candidate from the list\n");
					for(int i=0;i<Candidates.length;i++){
					System.out.println((i+1)+": "+Candidates[i]);
					}
					int candidate=sc.nextInt();
					boolean vote= stub.vote(id, --candidate);
					if(vote) {
						System.out.println("success! vote casted");
					}
					else {
						System.out.println("Error: Already casted/no such voter id");
					}
				}
				break;
				case 4:
				{
					int []resultlist= stub.tallyResults();
					System.out.println("Total vote count for each candidate\n");
					for (int i=0;i< Candidates.length;i++) {
						System.out.println(Candidates[i]+":"+resultlist[i]+"\n");
					}
					break;
						
				}
				case 5:
				{
					int winner=stub.announceWinner();
					System.out.println("Winning candidate is "+Candidates[winner]);
					break;
				}
				default: 
				{
					System.out.println("Thankyou!!");
					System.exit(0);
					break;
				}
				}
			}
		} catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}
}