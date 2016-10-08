import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Kalah {

	//default number of players
	final int DEFAULT_NUMBER_OF_PLAYERS = 2;

	//HashMap to access the players according to player id
	HashMap<Integer, Player> players;


	public Kalah(int numOfPlates, int numOfStones){

		// initialize the map
		players = new HashMap<Integer, Player>();

		//initialize the required Player and their information regarding pits
		init(numOfPlates, numOfStones);

	}

	/*Initialize the pits and players
	 * 
	 */
	public void init(int numOfPlatesPerPlayer, int numOfStones){

		for (int j = 0; j < DEFAULT_NUMBER_OF_PLAYERS; j++){	

			// create pit array list for each Player
			ArrayList<Pit> tempPt = new ArrayList<Pit>();

			for (int i = 0; i < numOfPlatesPerPlayer; i++){

				//create Pit according to desired number of pits for each Player
				Pit pt = new Pit(i, numOfStones);

				//add to pit list for the Player
				tempPt.add(pt);
			}

			//create the layer
			Player pl = new Player(j, tempPt);

			//add plaayer to hashmap
			players.put(j, pl);

		}
	}

	/* The rules of the Kalah: I just use update name regarding than rules
	 * Get player id and selected pit id, and operate on them. 
	 */
	public int update(int pl_ID, int pt_ID){

		//First, getting the number of stone information for the pit of the player
		int numOfStones = players.get(pl_ID).pits.get(pt_ID).numberOfStones;

		//Test to see if there is a stone in the pit
		if (numOfStones == 0){
			return -1;
		}

		//if there is pit, reset the number of the stone on the pit to 0
		players.get(pl_ID).pits.get(pt_ID).resetStones();

		//decrease the number of the stones until it becomes 0
		while (numOfStones > 0){

			//First update the pits for the same player
			numOfStones = updateSamePlayer(pl_ID, numOfStones, pt_ID + 1);

			//if numOfStones==0 after updating the same player pits: return 0, to inform that the Player should switch, 
			if (numOfStones == 0){
				return 0;
			}

			//if there is stone after updating the player pits, add it to Kalah 
			if (numOfStones > 0){
				players.get(pl_ID).countStones++;
				numOfStones--;
			}

			//if numOfStones==0 after updating the player Kalah: return 1, to inform that the Player should continue to play, 
			if (numOfStones == 0){
				return 1;
			}

			//if still there is stone after adding Kalah, add stones to the other player pits						
			numOfStones = updateOppositePlayer(pl_ID, numOfStones);

			// Update Pit id so that in the next iterate it will start from beginning
			pt_ID = -1;
		}

		//To test whether the game end
		if (checkEnd()){
			return 2;
		}

		//if previous return does not hold, return 0 to inform that Player needs to switch
		return 0;
	}

	/* This function return the winner information
	 * 
	 */
	public void checkWin() {
		// TODO Auto-generated method stub

		// Iterate all players
		for (int j = 0; j < DEFAULT_NUMBER_OF_PLAYERS; j++){

			// Iterate all pits for each player
			for (int i = 0; i < players.get(j).pits.size(); i++){

				// Add the left over stones in the pits to counts
				players.get(j).countStones = players.get(j).countStones + players.get(j).pits.get(i).numberOfStones;
			}
		}

		//Build string according to stones of kalah for players
		String result = "Player 0 total Stones: " + players.get(0).countStones + "\nPlayer 1 total Stones: " + players.get(1).countStones;

		//Compare the Kalah for all players to find the winner and display the winners
		if (players.get(0).countStones > players.get(1).countStones){
			JOptionPane.showMessageDialog (null, "Player 0 Win\n" + result, "Winner", JOptionPane.INFORMATION_MESSAGE);
		}else if (players.get(0).countStones < players.get(1).countStones){
			JOptionPane.showMessageDialog (null, "Player 1 Win\n" + result, "Winner", JOptionPane.INFORMATION_MESSAGE);
		}else{
			JOptionPane.showMessageDialog (null, "Tie\n" + result, "Winner", JOptionPane.INFORMATION_MESSAGE);
		}

	}

	/* Update the same player pits
	 * 
	 */
	public int updateSamePlayer(int pl_ID, int numOfStone, int pt_ID){

		// Loop until no stone left in the hand and there is no pit to update for the player
		while(numOfStone > 0 && pt_ID < players.get(pl_ID).pits.size()){

			// Rule: If there is one stone in the hand and the next pit has no stones
			if (numOfStone == 1 && players.get(pl_ID).pits.get(pt_ID).numberOfStones == 0){

				// Get the the other player
				int oppositePl = getOppositePlayer(pl_ID);

				// Add stones in the opposite side of the other player and the stone in the hand to Kalah
				players.get(pl_ID).countStones = players.get(pl_ID).countStones + 1 + players.get(oppositePl).pits.get(pt_ID).numberOfStones;

				// Reset the stone in the opposite side of the pit for the other player
				players.get(oppositePl).pits.get(pt_ID).resetStones();
			}else{

				// otherwise, just add stone to pits
				players.get(pl_ID).pits.get(pt_ID).numberOfStones++;
			}

			// Increase the pit id to access the next pit
			pt_ID++;

			// Decrease the number of stones in the hand
			numOfStone--;
		}

		// Return the number of stones in the hand after updating all pits
		return numOfStone;
	}

	/* Update the other player pits from the stones of the other player
	 * 
	 */
	public int updateOppositePlayer(int pl_ID, int numOfStone){

		//Get the other player
		int temp_pl_ID = getOppositePlayer(pl_ID);

		//Start from pit 0
		int temp_pt_ID = 0;

		//Iterate until stones in the hand is 0 and pit id is not bigger than the size of the pits of the player
		while(numOfStone > 0 && temp_pt_ID < players.get(temp_pl_ID).pits.size()){

			// Update the stones in the pit
			players.get(temp_pl_ID).pits.get(temp_pt_ID).numberOfStones++;

			// Increase pit id to access next pit
			temp_pt_ID++;

			//Decrease the number of stones in tha hand
			numOfStone--;
		}

		// Return the number of stones in the hand after updating all pits
		return numOfStone;
	}

	/* Return the other player on the opposite side
	 * 
	 */
	public int getOppositePlayer(int pl_ID){
		// Initialize the Player id
		int temp_pl_ID = 0;

		// Test the current player id
		if (pl_ID == 0){

			// Change the player id
			temp_pl_ID = 1;
		}

		// Return new player id
		return temp_pl_ID;
	}

	/*Check whether the game ends
	 * 
	 */
	public boolean checkEnd(){
		
		// Count the players to see of all the stones of the player pits are zero
		int count = 0;
		
		// Iterate all players
		for (int j = 0; j < DEFAULT_NUMBER_OF_PLAYERS; j++){			
			// Iterate the player pits to see if the pits have no stones 
			for (int i = 0; i < players.get(j).pits.size(); i++){
				if (players.get(j).pits.get(i).numberOfStones != 0){
					count++;
					break;
				}else if (players.get(j).pits.get(i).numberOfStones < 0){

					// For safety test.
					System.out.println("game check: should not be less than 0");
				}
			}
		}
		
		// Test to see the player count is  equal to DEFAULT_NUMBER_OF_PLAYERS
		if (count != DEFAULT_NUMBER_OF_PLAYERS){
			return true;
		}
		
		return false;

	}

}



