
public class Pit {
	
	//Each pit has an id to identify
	int id;
	
	//Each pit has a number of stones
	int numberOfStones;
	
	
	public Pit(int id, int numberOfStones){
		this.id = id;
		this.numberOfStones = numberOfStones;
	}
	
	
	/*Reset number of stones
	 * 
	 */
	public void resetStones(){
		numberOfStones = 0;
	}

	
}
