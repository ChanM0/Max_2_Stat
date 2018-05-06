import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class MainCodeArea {

	public MainCodeArea(){}

	/*
	reads input from input3.txt file. 
	reads input by line and puts each line into a string ArrayList. 
	then passes the String ArrayList to 
	*/
	public void readFile(){
		String line = null;
		ArrayList<String> input = new ArrayList<String>(); 
		try{
			FileReader fileReader = new FileReader("input3.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);  
			while( ( line = bufferedReader.readLine() ) != null ){
				input.add(line);
			}
		}
		catch(FileNotFoundException exFileNotFound){
			System.out.println("input3.txt cant find");
		}
		catch(IOException exIO){
			System.out.println("cant Read File!");
		}
		initalizeXValues(input);
	}
	/*
	creates an int array to hold all the x values
	*/

	public void initalizeXValues(ArrayList<String> input){
		ArrayList<Integer> clauses = new ArrayList<Integer>();
		String[] line;

		for(int i = 0; i < input.size(); i++){
			line = input.get(i).split(" ");
			clauses.add(Integer.parseInt(line[0]));
			clauses.add(Integer.parseInt(line[1]));     
		}
		for(int i = 0; i < clauses.size(); i++) {   
			System.out.print(clauses.get(i) + " ");
			i++;
			System.out.print(clauses.get(i) + "\n");
		} 
		getTableNArrays(clauses);
	}

	/*
	returns true if a member of the ArrayList Integer exists 
	retruns false if it doesnt 
	*/
	public boolean checkIfItExists(ArrayList<Integer> ar,int temp){
		for(int i = 0; i < ar.size(); i++)
			if(temp ==ar.get(i)) 
				return true;
			return false;
		}

	/*
	creates an array of all x variables used 
	and a truth table 
	with the corresponding not values
	*/
	public void getTableNArrays(ArrayList<Integer> clauses){
		ArrayList<Integer> absValueList = new ArrayList<Integer>();
		int[][] truthTable;
		int temp,totalNums = 0;

		for(int i = 0; i < clauses.size(); i++){
			temp = Math.abs(clauses.get(i));
			if(checkIfItExists(absValueList,temp) == false){
				absValueList.add(temp);
				totalNums++;
				absValueList.add((temp * -1));
			}
		}

		truthTable = createTruthTable(absValueList,totalNums);
		ArrayList<Integer> xVarUsed = xVarUsed(clauses, absValueList);

		print2dArray(totalNums, truthTable);

		bruteForceMethod(clauses,truthTable);
	}

	/*
	prints a 2d array 
	*/
	public void print2dArray(int totalNums, int[][] truthTable){
		for (int row = 0; row < truthTable.length ; row++) {
			//System.out.print(row+" :");
			for (int col = 0 ;col < truthTable[0].length ;  col++) {
				System.out.print(truthTable[row][col] + "\t");
			}
			if(row == 0)
				System.out.print("\n-------------------------");
			System.out.println();
		}

	}

	/*
	creates a truth table with the coresspoding not values
	*/
	public int[][] createTruthTable(ArrayList<Integer> absValueList,int totalNums){
		int[][] truthTable = new int[(int)((Math.pow(2,totalNums))+1)][totalNums*2];
		int ans = 0, counter = 0;

		for(int col = 0; col < (totalNums*2) ; col++)
			truthTable[0][col] = absValueList.get(col);

		for (int row = 1; row<(int)((Math.pow(2,totalNums))+1); row++) {
			counter = 0;
			for (int col=totalNums-1; col>=0; col--) {
				ans = ((row-1)/ ((int) Math.pow(2, col)))%2 ;
				if(ans == 0) ans = 1;
				else ans = 0;
				truthTable[row][counter] = ans;
				counter++;
				if(ans != 0) ans = 0;
				else ans = 1;	
				truthTable[row][counter] = ans;
				counter++;
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
	*/)
	public int getColIndex (int[][] truthTable,  int x1){
		for(int col = 0; col < truthTable[0].length ; col++)
			if( x1 == truthTable[0][col]) 
				return col;
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
		int x1Bool = 1, x2Bool = 1, x1 = 0, x2 = 0;
		int col = 0, truthCounter = 0, max = 0;

		for(int row = 1; row < truthTable.length; row++){
			truthCounter = 0;
			for (int clos = 0; clos < clauses.size() ;clos++ ) {
				x1 = clauses.get(clos);
				clos++;
				x2 = clauses.get(clos);

				col = getColIndex(truthTable, x1);
				x1Bool = truthTable[row][col];

				col = getColIndex(truthTable, x2);
				x2Bool = truthTable[row][col];

				if( ( (x1Bool == 1) || (x2Bool == 1) ) )
					truthCounter++;
			}
			max = Math.max(truthCounter,max);
		}
		System.out.println("Max truths "+max);
	}
}