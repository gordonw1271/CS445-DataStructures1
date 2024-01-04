// Gordon Wong
// Assignment 3

// problem: will not try other direction after first direction is found. ie data was found going
// 			right, wont try other directions after backtracking

import java.io.*;
import java.util.*;

public class Assig3
{	
	private StringBuilder index;


	public static void main(String [] args)
	{
		new Assig3();
	}

	public Assig3(){
		Scanner inScan = new Scanner(System.in);
		Scanner fReader;
		File fName;
        String fString = "",phrase = "";
		index = new StringBuilder("");

       
       	// Make sure the file name is valid
        while (true)
        {
           try
           {
               System.out.println("Please enter grid filename:");
               fString = inScan.nextLine();
               fName = new File(fString);
               fReader = new Scanner(fName);
               break;
           }
           catch (IOException e)
           {
               System.out.println("Problem " + e);
           }
        }

		// Parse input file to create 2-d grid of characters
		String [] dims = (fReader.nextLine()).split(" ");
		int rows = Integer.parseInt(dims[0]);
		int cols = Integer.parseInt(dims[1]);
		
		char [][] theBoard = new char[rows][cols];

		for (int i = 0; i < rows; i++)
		{
			String rowString = fReader.nextLine();
			for (int j = 0; j < rowString.length(); j++)
			{
				theBoard[i][j] = Character.toLowerCase(rowString.charAt(j));
			}
		}

		// Show user the grid
		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				System.out.print(theBoard[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("Please enter phrase (sep. by single spaces):");
        phrase = (inScan.nextLine()).toLowerCase();
		while (!(phrase.equals("")))
		{
			int count = 1;

			for (int i = 0; i < phrase.length(); i++) {
				if (phrase.charAt(i) == ' ') {
					count++;
				}
			}
			System.out.println("Looking for: " + phrase + "\n" + "containing " + count + " words");
			// create an array of all words in phrase
			String[] userPhrase = phrase.split(" ");
//------------------------------------------------------------------------------------------------------------------------
//------------------------------------------------------------------------------------------------------------------------

				boolean found = false;
				for (int r = 0; (r < rows && !found); r++)
				{
					for (int c = 0; (c < cols && !found); c++)
					{
					// Start search for each position at index 0 of the word
						found = findPhrase(r, c, userPhrase,theBoard,0);
					}
				}

				if (found)
				{
					System.out.println("The phrase: " + phrase + "\nwas found:");
					for(int i = 0;i<userPhrase.length*4;i+=4){
						System.out.println(userPhrase[i/4] + ": [" + index.charAt(i) + ", " + index.charAt(i+1) + "] to [" + index.charAt(i+2) + ", " + index.charAt(i+3) + "]");
					}

					for (int i = 0; i < rows; i++)
					{
						for (int j = 0; j < cols; j++)
						{
							System.out.print(theBoard[i][j] + " ");
							theBoard[i][j] = Character.toLowerCase(theBoard[i][j]);
						}
						System.out.println();
					}
				}
				else
				{
					System.out.println("The phrase: " + phrase);
					System.out.println("was not found");
					for (int i = 0; i < rows; i++)
					{
						for (int j = 0; j < cols; j++)
						{
							theBoard[i][j] = Character.toLowerCase(theBoard[i][j]);
						}
					}
				}
			
				System.out.println("Please enter phrase (sep. by single spaces):");
        		phrase = (inScan.nextLine()).toLowerCase();

				index.setLength(0);
		}
	}

	// FIND PHRASE METHOD
	public boolean findPhrase(int r, int c, String [] userPhrase,char [][] bo,int count){
		boolean answer;
		int row = r, col = c;
		char dir = 'r';

		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0){
			return false;
		}else{
			answer = findWordRight(r, c, userPhrase[count], bo);  // right
			col = c + userPhrase[count].length() - 1;

			if(!answer){										  // down
				answer = findWordDown(r, c, userPhrase[count], bo);
				col = c;
				row = r + userPhrase[count].length() - 1;
				dir = 'd';
			}
			if(!answer){										  // left
				answer = findWordLeft(r, c, userPhrase[count], bo);
				col = c - userPhrase[count].length() + 1;
				row = r;
				dir = 'l';
			}
			if(!answer){										  // up
				answer = findWordUp(r, c, userPhrase[count], bo);
				col = c;
				row = r - userPhrase[count].length() + 1;
				dir = 'u';
			}
			if(!answer){
				return false;
			}
		}
		if(answer){													  // first word is found
			answer = findNext(userPhrase,bo,count,dir,r,c, row, col); // look for next word
		}
		if(!answer){   // couldn't find next word so back track
			backTrack(userPhrase,bo,count,dir,r,c);
		}
		// ----------------------------------------------------------------------------------------------------------------
		// This section of the code makes sure that each diirection is tried before back tracking
		// all the way. If failed on going right, program will try down left up. If then failed on 
		// down, will try left up. etc
		if(!answer && dir == 'r'){
			answer = findWordDown(r, c, userPhrase[count], bo);		  // down
			col = c;
			row = r + userPhrase[count].length() - 1;
			dir = 'd';
			if(!answer){
				return false;
			}else{						
				answer = findNext(userPhrase,bo,count,dir,r,c, row, col);
			}

			if(!answer){   // couldn't find next word so back track
				backTrack(userPhrase,bo,count,dir,r,c);
			}
		}else if(!answer && dir == 'd'){
			answer = findWordLeft(r, c, userPhrase[count], bo);
			col = c - userPhrase[count].length() + 1;
			row = r;
			dir = 'l';
			if(!answer){
				return false;
			}
			if(answer){						
				answer = findNext(userPhrase,bo,count,dir,r,c, row, col);
			}
			if(!answer){   // couldn't find next word so back track
				backTrack(userPhrase,bo,count,dir,r,c);
			}
		}else if(!answer && dir == 'l'){
			answer = findWordUp(r, c, userPhrase[count], bo);
			col = c;
			row = r - userPhrase[count].length() + 1;
			dir = 'u';
			if(!answer){
				return false;
			}
			if(answer){						
				answer = findNext(userPhrase,bo,count,dir,r,c, row, col);
			}
			if(!answer){   // couldn't find next word so back track
				backTrack(userPhrase,bo,count,dir,r,c);
			}
		}else{
			return answer;
		}
		return answer;
	}

	// FIND WORD METHODS
	public boolean findNext(String [] userPhrase,char [][] bo,int count,char dir,int r, int c, int row, int col){
		boolean answer;
		index.append(r);
		index.append(c);
		index.append(row);
		index.append(col);
		if(count == userPhrase.length-1){
			return true;
		}else{
			answer = findPhrase(row, col + 1,userPhrase,bo,count+1); // right
			if(!answer && dir != 'u'){
				answer = findPhrase(row + 1,col,userPhrase,bo,count+1);	//down
			}
			if(!answer && dir != 'r' ){
				answer = findPhrase(row,col -1,userPhrase,bo,count+1);	// left
			}
			if(!answer && dir != 'd'){
				answer = findPhrase(row - 1,col,userPhrase,bo,count+1);	// up
			}
		}
		return answer;
	}
	public void backTrack(String [] userPhrase,char [][] bo,int count,char dir,int r, int c){
		index.delete(index.length() -4, index.length());
		if(dir == 'r'){									 // back track right
			for(int i = 0;i<userPhrase[count].length();i++){
			bo[r][c+i] = Character.toLowerCase(bo[r][c+i]);
			}
		}else if(dir == 'd'){									 // back track down
			for(int i = 0;i<userPhrase[count].length();i++){
				bo[r+i][c] = Character.toLowerCase(bo[r+i][c]);
			}
		}else if(dir == 'l'){									 // back track left
			for(int i = 0;i<userPhrase[count].length();i++){
				bo[r][c-i] = Character.toLowerCase(bo[r][c-i]);
			}
		}else if(dir == 'u'){									 // back track up
			for(int i = 0;i<userPhrase[count].length();i++){
				bo[r-i][c] = Character.toLowerCase(bo[r-i][c]);
			}
		}
	}
	public boolean findWordRight(int r, int c, String word,char [][] bo){
		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0 || c + word.length() - 1 >=  bo[0].length){
			return false;
		}
		String test = "";
		for(int i = 0;i<word.length();i++){
			test += bo[r][c+i];
		}
		if(test.equals(word)){
			for(int i = 0;i<word.length();i++){
				bo[r][c+i] = Character.toUpperCase(bo[r][c+i]);
			}
			return true;
		}else{
			return false;
		}
	}
	public boolean findWordLeft(int r, int c, String word, char [][] bo){
		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0 || c - word.length() + 1 <  0){
			return false;
		}
		String test = "";
		for(int i = 0;i<word.length();i++){
			test += bo[r][c-i];
		}
		if(test.equals(word)){
			for(int i = 0;i<word.length();i++){
				bo[r][c-i] = Character.toUpperCase(bo[r][c-i]);
			}
			return true;
		}else{
			return false;
		}
	}
	public boolean findWordUp(int r, int c, String word, char [][] bo){
		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0 || r - word.length() + 1 <  0){
			return false;
		}
		String test = "";
		for(int i = 0;i<word.length();i++){
			test += bo[r-i][c];
		}
		if(test.equals(word)){
			for(int i = 0;i<word.length();i++){
				bo[r-i][c] = Character.toUpperCase(bo[r-i][c]);
			}
			return true;
		}else{
			return false;
		}
	}
	public boolean findWordDown(int r, int c, String word, char [][] bo){
		if (r >= bo.length || r < 0 || c >= bo[0].length || c < 0 || r + word.length() - 1 >= bo.length){
			return false;
		}
		String test = "";
		for(int i = 0;i<word.length();i++){
			test += bo[r+i][c];
		}
		if(test.equals(word)){
			for(int i = 0;i<word.length();i++){
				bo[r+i][c] = Character.toUpperCase(bo[r+i][c]);
			}
			return true;
		}else{
			return false;
		}
	}

	public String getIndex(){
		return index.toString();
	}
}