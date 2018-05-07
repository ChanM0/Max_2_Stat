import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class MainCodeArea {

	public MainCodeArea(){}

	/*
	reads input from input3.txt file. 
	reads input by line a into a line array with a delimiter
	then passes the int ArrayList 
	then sends int array list to getTableNArrays
	*/
	public void readFile(){
		ArrayList<Integer> clauses = new ArrayList<Integer>();
		String line = null;
		String[] lineA;

		try{
			FileReader fileReader = new FileReader("input3.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);  
			while( ( line = bufferedReader.readLine() ) != null ){
				lineA = line.split(" ");
				clauses.add(Integer.parseInt(lineA[0]));
				clauses.add(Integer.parseInt(lineA[1])); 
			}
		}
		catch(FileNotFoundException exFileNotFound){
			System.out.println("input3.txt cant find");
		}
		catch(IOException exIO){
			System.out.println("cant Read File!");
		}
		getTableNArrays(clauses);
	}

	/*
	returns true if a member of the ArrayList Integer exists 
	retruns false if it doesnt 
	*/
	public boolean checkIfItExists(ArrayList<Integer> ar,int temp){
		for(int i = 0; i < ar.size(); i++)
			if(temp ==ar.get(i))  return true; // does exist
		return false; // does not exist
	}

	/*
	prints a 2d array 
	*/
	public void print2dArray(int totalNums, int[][] truthTable){
		for (int row = 0; row < truthTable.length ; row++) {
			for (int col = 0 ;col < truthTable[0].length ;  col++) 
				System.out.print(truthTable[row][col] + "\t");
			if(row == 0) System.out.print("\n-------------------------\n");
		}
	}

	/*
	creates an array of all x variables used 
	and a truth table 
	with the corresponding not values
	*/
	public void getTableNArrays(ArrayList<Integer> clauses){
		ArrayList<Integer> absValueList = new ArrayList<Integer>(); //rename single variables
		int[][] truthTable;
		int temp,totalNums = 0;

		for(int i = 0; i < clauses.size(); i++){ // put this in read file
			temp = Math.abs(clauses.get(i));
			if(checkIfItExists(absValueList,temp) == false){ //checks if it does not exist
				absValueList.add(temp);
				totalNums++;
				absValueList.add((temp * -1));
			}
		}
		truthTable = createTruthTable(absValueList,totalNums);
		//ArrayList<Integer> xVarUsed = xVarUsed(clauses, absValueList); delete this maybe?
		print2dArray(totalNums, truthTable);
		bruteForceMethod(clauses,truthTable);
	}
	/*
	creates a truth table with the coresspoding not values
	*/
	public int[][] createTruthTable(ArrayList<Integer> absValueList,int totalNums){
		int[][] truthTable = new int[(int)((Math.pow(2,totalNums))+1)][totalNums*2];
		int ans = 0, column = 0;

		for(int col = 0; col < (totalNums*2) ; col++)
			truthTable[0][col] = absValueList.get(col);

		for (int row = 1; row<(int)((Math.pow(2,totalNums))+1); row++) {
			column = 0;
			for (int power=totalNums-1; power>=0; power--) {
				ans = ((row-1)/ ((int) Math.pow(2, power)))%2 ;
				if(ans == 0) ans = 1;
				else ans = 0;
				truthTable[row][column] = ans;
				column++;
				if(ans != 0) ans = 0;
				else ans = 1;	
				truthTable[row][column] = ans;
				column++;
			}
		}
		return truthTable;
	}

	/*
	creates the an array list with only the x variables used within the input
	*/
	public ArrayList<Integer> xVarUsed(ArrayList<Integer> clauses, ArrayList<Integer> absValueList){
		int temp = 0;
		boolean checkIfItExists = true;
		ArrayList<Integer> xVarUsed = new ArrayList<Integer>();

		for(int i = 0; i < absValueList.size(); i++){
			temp = absValueList.get(i);
			checkIfItExists = checkIfItExists(clauses,temp);
			if(checkIfItExists)
				xVarUsed.add(temp);
		}
		return xVarUsed; 
	}
	/*
	get the corresponding variables boolean value via the truth table 
	by finding its coressponding column : 
	truthTable[row itteration][coresspponding(x1 or x2) col]
	*/
	public int getColIndex (int[][] truthTable,  int x1){
		for(int col = 0; col < truthTable[0].length ; col++)
			if( x1 == truthTable[0][col]) return col;
		return -1;// forerror
	}
	/*
	checks loops through the truth table rows, 
		within each row, we loop through all the clauses 
		and store clauses in variables x1 and x2

		then we get the corresponding variables boolean value via the truth table 
		by finding its coressponding column : 
		truthTable[row itteration][coresspponding(x1 or x2) col]
		we then check 
		if either x1Boolean and x2Boolean values are == 1
			then we increase the truth counter

	before the ending of each row itteration 
	we take the max truthCounter and the previous max 
	to find the max amount of truths between all the itterations
	*/
	public void bruteForceMethod(ArrayList<Integer> clauses, int[][] truthTable){
		int x1Bool = 1, x2Bool = 1, prevMax = 0, currMax = 0, col = 0, truthCounter = 0;
		String output = "";
		

		for(int row = 1; row < truthTable.length; row++){
			truthCounter = 0;
			for (int clos = 0; clos < clauses.size() ; clos++ ) {

				col = getColIndex(truthTable, clauses.get(clos) );
				x1Bool = truthTable[row][col];

				clos++;

				col = getColIndex(truthTable, clauses.get(clos) );
				x2Bool = truthTable[row][col];

				if( ( (x1Bool == 1) || (x2Bool == 1) ) )
					truthCounter++;
			}
			prevMax = currMax;
			currMax = Math.max(truthCounter,currMax);
			if(currMax == truthCounter ){
				if(currMax > prevMax ) output = "";
				for(int c = 0; c < truthTable[0].length; c++){
					if(truthTable[0][c] > 0){
						if (truthTable[row][c] == 1) output += "T";
						else output += "F";	
					}
				}
				output+="\n";
			}
		}
		System.out.println("Max truths " + currMax + "\n"+output);
	}	
}

