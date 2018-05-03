import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class MainCodeArea {

	public MainCodeArea(){}

	public void readFile(){
		String line = null;
		ArrayList<String> array = new ArrayList<String>(); 
		try{
			FileReader fileReader = new FileReader("input1.txt");
			BufferedReader bufferedReader = new BufferedReader(fileReader);  
			while( ( line = bufferedReader.readLine() ) != null ){
				array.add(line);
			}
		}
		catch(FileNotFoundException exFileNotFound){
			System.out.println("input1.txt cant find");
		}
		catch(IOException exIO){
			System.out.println("cantReadFile");
		}
		part1(array);
	}

	public void part1(ArrayList<String> ar){
		ArrayList<Integer> xVarList = new ArrayList<Integer>();
		String[] line;
		int temp;

		for(int i = 0; i < ar.size(); i++){
			line=ar.get(i).split(" ");
			temp = Integer.parseInt(line[0]);
			xVarList.add(temp);
			temp = Integer.parseInt(line[1]);
			xVarList.add(temp);     
		}
		for(int i = 0; i < xVarList.size(); i++) {   
			System.out.print(xVarList.get(i) + " ");
			i++;
			System.out.print(xVarList.get(i) + "\n");
		} 
		sortValues(xVarList);
	}

	public boolean checkIfItExists(ArrayList<Integer> absValueList,int temp){

		for(int i = 0; i < absValueList.size(); i++){
			if(temp ==absValueList.get(i))
				return true;
		}
		return false;


	}

	public void sortValues(ArrayList<Integer> xVarList){
		ArrayList<Integer> absValueList = new ArrayList<Integer>();

		int temp;
		int totalNums = 0;


		for(int i = 0; i < xVarList.size(); i++){
			temp = Math.abs(xVarList.get(i));
			if(checkIfItExists(absValueList,temp) == false){
				absValueList.add(temp);
				totalNums++;
				absValueList.add((temp * -1));
			}
		}

		//Collections.sort(absValueList); // sort

		for(int i = 0; i < absValueList.size(); i++) {   
			System.out.print(absValueList.get(i) + " ");
		} 

		System.out.println("\n*****************\n");
		next(absValueList,totalNums);


		
	}

	public void next(ArrayList<Integer> absValueList,int totalNums){
		String[][] truthTable = new String[(int)((Math.pow(2,totalNums))+1)][totalNums*2];
		int ans =0, counter = 0;

		for(int col = 0; col < (totalNums*2) ; col++){
			truthTable[0][col] = "" + absValueList.get(col);
		}

		for (int row=1; row<(int)((Math.pow(2,totalNums))+1); row++) {
			counter = 0;
			for (int col=n-1; col>=0; col--) {
				ans = ((row-1)/ ((int) Math.pow(2, col)))%2 ;
				truthTable[row][counter] = "" + ans;
				counter++;
				if(ans == 0)
					ans = 1;
				else 
					ans = 0;
				truthTable[row][counter] = "" + ans;
				counter++;
			}
		}

		for (int row = 0; row < (int)((Math.pow(2,totalNums))+1)  ; row++) {
			for (int col = 0 ;col < (totalNums*2) ;  col++) {
				System.out.print(truthTable[row][col] + "\t");

			}
			if(row == 0)
				System.out.print("\n--------------");
			System.out.println();
		}

	}



}            