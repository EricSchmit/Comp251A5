import java.util.*;
import java.io.*;
import java.util.regex.*;
import static java.lang.System.out;
import java.math.*;
//Eric Schmit 260 678 187
//Collaboration : myCourses discussion boards

public class mancala {
	
	
	public static boolean areThereMoves(int[] board){
		for (int i = 2; i < board.length; i++){
			if (board[i-2] == 0 && board[i-1] == 1 && board[i] == 1){//A left move exists
				return true;
			}
			
			else if(board[i-2] == 1 && board[i-1] == 1 && board[i] == 0){ //A right move exists
				return true;
			}
		
		}
		return false;	
	}
	
	//Returns an array with a 1 at positions where a leftMove is possible, 0 otherwise.
	public static int[] getLeftMoves(int[] board){
		int[] new_board = new int[board.length];
		
		for (int i = 2; i < board.length; i++){
			if (board[i-2] == 0 && board[i-1] == 1 && board[i] == 1){
				new_board[i-1] = 1;
			}
		}
		return new_board;
	}
	
	//Returns an array with a 1 at positions where a rightMove is possible, 0 otherwise.
	public static int[] getRightMoves(int[] board){
		int[] new_board = new int[board.length];
		
		for (int i = 2; i < board.length; i++){
			if (board[i-2] == 1 && board[i-1] == 1 && board[i] == 0){
				new_board[i-2] = 1;
			}
		}
		return new_board;
	}
	
	
	public static int getNumPebbles(int[] board){
		int num = 0;
		for (int i = 0; i < board.length; i++){
			if (board[i] == 1){
				num++;
			}
		}
		
		return num;
	}
	
	//Performs a left move given a board and returns the updated board
	//leftMove : 011 --> 100
	//index is index of the 1st pebble
	public static int[] leftMove(int[] board, int index){
		int[] new_board = board;
		new_board[index-1] = 1;
		new_board[index] = 0;
		new_board[index+1] = 0;
		
		return new_board;
	}
	
	public static int[] rightMove(int[] board, int index){
		int[] new_board = board;
		new_board[index] = 0;
		new_board[index+1] = 0;
		new_board[index+2] = 1;
		
		return new_board;
	}
	
	//Returns an array of the size of the number of moves possible with in it each index of the moves
	public static int[] indexMoves(int[] leftMoves){ // Left doesnt matter here works also for right moves
		int[] moves = new int[getNumPebbles(leftMoves)];
		int k = 0;
		for (int i = 0; i < leftMoves.length; i++){
			if (leftMoves[i] == 1){
				moves[k] = i;//Index at which that move is
				k++;
			}
		}
		return moves;
	}
	
	public static int generateRandom(int range){
		Random random = new Random();
		return random.nextInt(range);
	}
	
	//Considers every possible board recursively
	//Lowest number of pebbles on board stored in board[12]. the 11 other spots in the array board store the current configuration of the board
	public static int[] rSolve(int[] board){
		int[] newBoard = new int[board.length];
		int best_score = 13;
		int[] temp = new int[board.length];
		
		//Base case: No moves possible
		if (areThereMoves(board) == false){
			best_score = getNumPebbles(board);
			if (best_score < board[12]){
				board[12] = best_score;
			}
			return board;
		}
		
		int[] leftMoves;
		int[] rightMoves;
		int[] ind_leftMoves;
		int[] ind_rightMoves;
		leftMoves = getLeftMoves(board);
		rightMoves = getRightMoves(board);
		ind_leftMoves = indexMoves(leftMoves);
		ind_rightMoves = indexMoves(rightMoves);
		
		ArrayList<int[]> boards = new ArrayList<int[]>();
		
		for (int i = 0; i < ind_leftMoves.length; i++){
			newBoard = board.clone();
			newBoard = leftMove(newBoard, ind_leftMoves[i]);
			boards.add(newBoard);
		}
		
		for(int j = 0; j < ind_rightMoves.length; j++){
			newBoard = board.clone();
			newBoard = rightMove(newBoard, ind_rightMoves[j]);
			boards.add(newBoard);
		}
		
		for (int k = 0; k < boards.size(); k++){
			temp = rSolve(boards.get(k));
			if (temp[12] < board[12]){
				board[12] = temp[12];
			}
		}
		return board;
	}
	
	
	
	public static void main(String[] args){
		//long startTime = System.currentTimeMillis();
		//String file = args[0];
		String file = "testMancala.txt";
		//args[0] = "testMancala.txt";
		ArrayList<int[]> problems = new ArrayList<int[]>();
		int num_prob = 0;
		
		//Parse File
		try{
			Scanner f = new Scanner(new File(file));
			num_prob = Integer.parseInt(f.nextLine()); //Line 1: number of problems in the file
	        String[] line;
	        
			while (f.hasNext()){
				
	        	line = f.nextLine().split("\\s+");
	        	int[] prob = new int[line.length+ 1];
	        	for (int i = 0; i < line.length; i++){
	        		prob[i] = Integer.parseInt(line[i]);
	        	}
	        	prob[12] = 15;
	        	problems.add(prob);
	        	
	        };
	        
			f.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }
		
		int[] board = new int[13];
		
		int[] sols = new int[num_prob];
		int[] temp = new int[13];
		
		for (int i = 0; i < problems.size(); i++){
			board = problems.get(i);
			temp = rSolve(board);
			sols[i] = temp[12];			
		}
		BufferedWriter output = null;
		String text = "";
		
		try{
			File out_file = new File("testMancala_solution.txt");
			output = new BufferedWriter(new FileWriter(out_file));
			
			for (int i = 0; i < num_prob; i++){
				text = Integer.toString(sols[i]);
				output.write(text);
				output.newLine();
			}
			
			output.close();
		} catch (IOException e){
			System.out.println("Error");
		} 
		//long endTime = System.currentTimeMillis();
		//System.out.println("Took "+(endTime - startTime) + " ms");
		
		
	}
	
}
