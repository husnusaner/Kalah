

import javax.swing.JOptionPane;

public class PlayKalah {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Number of stones in each pit
		int numOfStones = 6;
		
		//number of pits for each player
		int numOfPits = 6;
		
		//number of players (Player cannot be changed)
		final int numofPlayers = 2;
		
		//Create a Kalah object
		Kalah gmKalah = new Kalah(numOfPits, numOfStones);
		
		//temp player id
		int player = 0;
		
		// rc is used for input from JOptionPane
		int rc = -1;
		
		//JoptionPane buttons options
		String [] buttons = new String [numOfPits];
		for (int i = 0; i < numOfPits; i++){
			buttons[i] = "Pit " + (i + 1);
		}
		
		String [] selection = {"Yes" , "No"};
		
		// hold number of stones in each pit with total gathered stones for all players to display
		String eachPitsSizeWithTotal = "";
		
		// hold which play turns to display
		String playerInfo = "";
		
		//check whether the game ended
		boolean check = false; 
		
		int confirmExit = 1;
				
		while (!check){			
			
			//gathering information for each user regarding pits and their number of stones
			eachPitsSizeWithTotal = getStoneNumber(numofPlayers, gmKalah);
			
			//play order, first order every time is given to Player 0
			playerInfo = "Player " + player +" turns \n"; 
			
						
			//if the user wrongly closed the pane or wants to close
			confirmExit = 1;
			while((rc = JOptionPane.showOptionDialog(null, playerInfo + eachPitsSizeWithTotal, "Player " + player, JOptionPane.WARNING_MESSAGE, 0, null, buttons, buttons[0])) == -1){
				//Pane for option for Players
				if (rc == -1){
					confirmExit = JOptionPane.showOptionDialog(null, "", "Do you really want to exit?", JOptionPane.WARNING_MESSAGE, 0, null, selection, selection[1]);
					if (confirmExit == 0){
						break;
					}
				}
				
			}
			
			if (confirmExit == 0){
				break;
			}
			
			// update the Player information and return what will be the next action
			int updateInfo = gmKalah.update(player, rc);
			
			//check whether the game ends
			check = gmKalah.checkEnd();
		
			/*	apply rules according to updates
			 * 	-1: Player cannot select that option, therefore, giving warning
			 * 	0: The player is required to change
			 * 	1: The same player continues to play
			 * 	2: End of the game
			 */
			if (updateInfo == -1){
				JOptionPane.showMessageDialog (null, "Pit " + (rc + 1) + " has 0 stone. Please select Different pit", "Player " + player, JOptionPane.INFORMATION_MESSAGE);
			}else if(updateInfo == 0){
				player = gmKalah.getOppositePlayer(player);
			}
			if (updateInfo == 2){
				break;
			}
		}
		
		if (confirmExit != 0){
			//Game is ended, display winner. 
			gmKalah.checkWin();
		}
		
		
		//To properly exit
		System.exit(0);
	}

	/*This method returns the number of the stone in each pits for all Players
	 * 
	 */
	private static String getStoneNumber(int pl, Kalah gmKalah) {
		// TODO Auto-generated method stub
		
		//empty string
		String stonesInEachPit = "";
		
		//for loop : for all Players
		for (int j = 0; j < pl; j++){
			
			//Player info
			stonesInEachPit = stonesInEachPit + "Player " + j + " -> ";
			
			//access player pits 
			for (int i = 0; i < gmKalah.players.get(j).pits.size(); i++){
				stonesInEachPit = stonesInEachPit + " Pit " + (i + 1) + ": " + gmKalah.players.get(j).pits.get(i).numberOfStones + " stones    ";
			}
			
			//access player total pits in Kalah or home
			stonesInEachPit = stonesInEachPit + " Total: " + gmKalah.players.get(j).countStones+"\n";
		}	
		
		//return the information as string
		return stonesInEachPit;
	}

}
