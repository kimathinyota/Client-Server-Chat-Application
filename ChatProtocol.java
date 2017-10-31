import java.net.*;
import java.io.*;
 
public class ChatProtocol {
    private static final int INITIAL = 0;
    private static final int CALCULATE = 1;
    private static final int INTERPRET = 2;
    private static final int CHAT = 3;
 

 
    private int state = INITIAL;
   
    String returnRightOfOperator(String input){ // e.g. for INTERPRET clear z; this method will return clear z
		state = 0;
		if(input==null){
			return "Rules for using this chat stream: Type CALCULATE expression to return calculated value of input expression or Type INTERPRET source-code or file-name to interpret KEBB source code or Just write a normal message  ";
		}
		input = input.trim();
		String splitExpr[] = input.split("\\s+"); //splits string by space
		String states[] = {"CALCULATE","INTERPRET"}; //possible identifiers
		
		for(int i=0;i<2;i++){
			if(splitExpr[0].equals(states[i]) && splitExpr.length>=2){
				state = i + 1;
				return input.substring(states[i].length()+1,input.length()); //this will return the string to the right of the identifier 
			}
		}
		state = CHAT; // CHAT messages do not have an identifier that precedes the message
		return input; 
				
	}
	
	String calculate(String expression){ // this function will return the value of the input mathematical expression in string form
		try{
			InterpretBareBones myInterpreter = new InterpretBareBones();
			Integer val = myInterpreter.returnCalculationValue(expression); //this method evaluates expressions 
			return Integer.toString(val); //convert integer to string
		}catch(Exception E){
			return "An error occurred while trying to calculate " + expression;
		}
	}
	
	String interpret(String input){ //this will interpret KEBB (Kimathi's Extended Bare Bones) 
		try{
			InterpretBareBones myInterpreter = new InterpretBareBones();
			input = input.trim();
			if(input.contains(".txt")){
				myInterpreter.interpretFile(input); //this will interpret a file
			}else{
				myInterpreter.interpretSourceCode(input,0); //this will interpret input source code
			}
			
			return myInterpreter.output.replace(";", " ==> "); // ==> arrors are better at showing flow compared to ;
		}catch(Exception E){
			return "An error occurred while trying to interpret input";
		}
	}
	
 
    public String processInput(String input) { //this method will be used to generate server response to a client's message/request
		if(input=="Bye")
			return input;
		}
		String rightPart = returnRightOfOperator(input);
		if(state==CALCULATE){
			return calculate(rightPart);
		}else if(state==INTERPRET){
			return interpret(input);
		}
        	return rightPart;
    }
}
