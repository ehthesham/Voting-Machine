

import java.rmi.registry.Registry;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Random;


public class Server implements VoterInterface {

    public Server() {}
    HashMap<String,String> Voter= new HashMap<String,String>();
    HashMap<String, Boolean> VoteCast=new HashMap<String, Boolean>();
    int [] Candidate= new int[10];
    
	@Override
	public boolean vote(String id, int candidate) throws RemoteException {
		// TODO Auto-generated method stub
		boolean x= verifyVoter(id);
		if(x==false) {
			//not yet voted lets vote
			VoteCast.put(id, true);
			Candidate[candidate]++;
			return true;
		}
		else
		return false;
	}
	
	@Override
	public String registerVoter(String name) throws RemoteException {
		Random r= new Random();
		Integer d=  r.nextInt(1000);
		String id=d.toString();
		if(Voter.containsKey(id)) {
			return "Please try again operation failed";
		}
		else {
			Voter.put(id, name);
			VoteCast.put(id, false);
			return id;
		}
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	public boolean verifyVoter(String id) throws RemoteException {
		// TODO Auto-generated method stub
		boolean x=VoteCast.get(id);
		if(x==false){
			//already voted if true 
			return false;
		}
		else return true;
	//	return false;
	}


	@Override
	public int[] tallyResults() throws RemoteException {
		// TODO Auto-generated method stub
		
		return Candidate;
	}

	@Override
	public int announceWinner() throws RemoteException {
		// TODO Auto-generated method stub
		  if ( Candidate == null || Candidate.length == 0 ) return -1; // null or empty

		  int largest = 0;
		  for ( int i = 1; i < Candidate.length; i++ )
		  {
		      if ( Candidate[i] > Candidate[largest] ) largest = i;
		  }
		  return largest; // position of the first largest found
		
	}
	

	public static void main(String args[]) {

        try {
            Server obj = new Server();
            VoterInterface stub = (VoterInterface) UnicastRemoteObject.exportObject(obj, 0);

            // Bind the remote object's stub in the registry
            Registry registry = LocateRegistry.getRegistry();
            registry.bind("election", stub);

            System.err.println("Server ready");
        } catch (Exception e) {
            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();
        }
    }
}