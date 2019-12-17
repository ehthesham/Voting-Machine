
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
public interface VoterInterface extends Remote {
	

	    String registerVoter(String name) throws RemoteException;
	    boolean verifyVoter(String id) throws RemoteException;
	    boolean vote(String id, int candidate) throws RemoteException;
	    int[] tallyResults() throws RemoteException;
	    int announceWinner() throws RemoteException;
	}

