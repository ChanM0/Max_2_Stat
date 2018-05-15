import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class NotBruteForce{
	public NotBruteForce(){

	}

	public void notBruteForce(ArrayList<Integer> clausesInt){
		//System.out.println(clauses);

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
}