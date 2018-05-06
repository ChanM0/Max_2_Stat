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
		int[] clausesLeft = new int[input.size()];
		int[] clausesRight = new int[input.size()];
		ArrayList<Integer> clauses = new ArrayList<Integer>();
		String[] line;

		for(int i = 0; i < input.size(); i++){
			line = input.get(i).split(" ");
			//clausesLeft[i] = Integer.parseInt(line[0]);
			//clausesRight[i] = Integer.parseInt(line[1]);
			clauses.add(Integer.parseInt(line[0]));
			clauses.add(Integer.parseInt(line[1]));     
		}
		for(int i = 0; i < clauses.size(); i++) {   
			System.out.print(clauses.get(i) + " ");
			i++;
			System.out.print(clauses.get(i) + "\n");
			// System.out.print(clausesLeft[i] + " ");
			// System.out.print(clausesRight[i] + "\n");
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
		} //Collections.sort(absValueList); // sort

		truthTable = createTruthTable(absValueList,totalNums);
		ArrayList<Integer> xVarUsed = xVarUsed(clauses, absValueList);
		
		print2dArray(totalNums, truthTable);

		part3(clauses,truthTable,xVarUsed);
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
		int ans =0, counter = 0;

		for(int col = 0; col < (totalNums*2) ; col++){
			truthTable[0][col] = absValueList.get(col);
		}

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

	//public boolean findIndex()
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

	public int getCol (int[][] truthTable, int row, int c1){
		//boolean value;
		for(int columnValue = 0; columnValue < truthTable[0].length ; columnValue++){
			if( c1 == truthTable[0][columnValue])
				return columnValue;
		}

		return -10;

		//return true; 

	}
	public void part3(ArrayList<Integer> clauses, int[][] truthTable, ArrayList<Integer> xVarUsed ){
		//0 = false
		//1 = true

		System.out.println(xVarUsed);
		System.out.println(xVarUsed.size() * 2);

		int c1Bool = 1, c2Bool = 1;
		int c1 = 0, c2 = 0;
		int columnValue = 0, truthCounter =0, max = 0, temp = 0;

		boolean test = false;
		System.out.println();



		for(int row = 1; row < truthTable.length; row++){
			truthCounter = 0;
			System.out.println("ROW :" +row);
			
			for (int clos = 0; clos < clauses.size() ;clos++ ) {
				c1 = clauses.get(clos);
				clos++;
				c2 = clauses.get(clos);

				columnValue = getCol(truthTable, row, c1);
				temp = truthTable[row][columnValue ];
				c1Bool = temp;

				columnValue = getCol(truthTable, row, c2);
				temp = truthTable[row][columnValue ];
				c2Bool = temp;

				test = (c1Bool == 1) || (c2Bool == 1);
				System.out.println("c1 : "+c1+ " = "+c1Bool+"\tc2 : "+c2+ " = "+c2Bool+"\tboolean comp: "+test );

				if(test)
					truthCounter++;

			}
			max = Math.max(truthCounter,max);
			System.out.println();
		}



		System.out.println("Max truths "+max);
	}

	// for(int xBeingUsed= 0; xBeingUsed < xVarUsed.size(); xBeingUsed++){	 // loops through all the xVar used 
		// 	x1 = xVarUsed.get(xBeingUsed);
		// 	// for (int choice = 0; choice < 2 ; choice++ ) {


		// 	// 	if (choice == 0){
		// 	// 		if(x1 > 0) xOneBool = true; // if x1 positive then true
		// 	// 		else xOneBool = false; //else if negative then false
		// 	// 	}
		// 	// 	else{
		// 	// 		if(x1 < 0) xOneBool = true; // if x1 negative then true
		// 	// 		else xOneBool = false; // else if positive then false
		// 	// 	}
		// 		for (int i = 0; i < clauses.size() ; i++){ // gets clauses
		// 			c1 = clauses.get(i);
		// 			i++;
		// 			c2 = clauses.get(i);
		// 			if (c1 == x1){
		// 				// use xOneBool

		// 			}
		// 			if (c2 == x1){

		// 			}
		// 				//if c1 == xVar used 
		// 				// if c2 == xvar used 
		// 				// truth table
		// 		}
		// 		for (int column = 0;column < (clauses.size()/2)  ; column++) {

		// 		}
		// 	//}		
		// }

		// for(int i = 0; i < clauses.size(); i++){
		// 	bool1 = clauses.get(i);
		// 	i++;
		// 	bool2 = clauses.get(i);

		// 	if(bool1>0)
		// 		xBool1 =true;
		// 	else
		// 		xBool1 = false;
		// 	if (bool2 > 0)
		// 		xBool2 = true;
		// 	else
		// 		xBool2=false;

		// 	result = (xBool2 || xBool1);
		// 	if(result)
		// 		counter++;
		// }
		//System.out.println("\n"+counter);
}
