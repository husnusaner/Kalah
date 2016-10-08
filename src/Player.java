import java.util.ArrayList;

public class Player{
	
	// Each Player has an id
	int id;
	
	//Each player has a number of pits
	ArrayList<Pit> pits = new ArrayList<Pit>();
	
	//Each player has its own kalah or home to hold stones
	int countStones = 0;
	
	
	public Player(int id, ArrayList<Pit> pits){
		this.id = id;
		this.pits.addAll(pits);
	}
	
	/* Return Kalah count
	 * 
	 */
	public int getCountStones(){
		return countStones;
	}
	
	/* Increase Kalah count
	 * 
	 */
	public void increaseCountStones(){
		countStones++;
	}
}
