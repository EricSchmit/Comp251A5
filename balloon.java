import java.util.*;
import java.io.*;
import java.util.regex.*;
import static java.lang.System.out;
import java.math.*;

//Collaboration : myCourses discussion boards
//Eric Schmit 260678187
public class balloon {

	
	public static int solve(int[] balloons, int numBalloons){
		int numArrows = 0;
		int numPopped = 0;
		int[] popped = new int[balloons.length];
		int curH = -1;
		int count = 0;
		
		while (numBalloons != numPopped ){
			numArrows++;
			for (int i = 0; i < popped.length; i++){
				if (popped[i] == 0){
					curH = balloons[i];
					break;
				}
			
			}
			for (int j = 0; j < balloons.length; j++){
				
				if (popped[j] == 0 && curH == balloons[j]){
					numPopped++; //Popped 1 balloon
					popped[j] = 1; //Update to show balloon has been popped
					curH = balloons[j] - 1; //Arrow looses 1 height
				}
			}
			
		}
		return numArrows;
	}
	
	
	
	public static void main(String[] args){
		//long startTime = System.currentTimeMillis();
		String file = "testBalloons.txt";
		ArrayList<int[]> problems = new ArrayList<int[]>();
		int num_prob = 0;
		int[] num_balloons = new int[99999999];
		
		try{
			Scanner f = new Scanner(new File(file));
			num_prob = Integer.parseInt(f.nextLine()); //Line 1: number of problems in the file
			
	        String[] line = f.nextLine().split("\\s+");
	        //int[] num_balloons = new int[line.length]; //Line 2: number of balloons for each problem i
	        for (int i = 0; i < line.length; i++){
	        	num_balloons[i] = Integer.parseInt(line[i]);
	        }
	        
	        
			while (f.hasNext()){
				
	        	line = f.nextLine().split("\\s+");
	        	int[] prob = new int[line.length];
	        	for (int i = 0; i < line.length; i++){
	        		prob[i] = Integer.parseInt(line[i]);
	        	}
	        	problems.add(prob);
	        	
	        };
	        
			f.close();
        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }
		
		int[] sols = new int[num_prob];
		for (int k = 0; k < 6; k++){
			int[] test_prob = problems.get(k);
			int test_num_balloons = num_balloons[k];
			sols[k] = solve(test_prob, test_num_balloons);
		}
		
		BufferedWriter output = null;
		String text = "";
		
		try{
			File out_file = new File("testBalloons_solution.txt");
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

