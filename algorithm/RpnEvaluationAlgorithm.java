package algorithm;

import enums.TokenType;
import interfaces.IRpnEvaluationAlgorithm;
import structures.Queue;
import structures.Stack;
import structures.Token;
public class RpnEvaluationAlgorithm implements IRpnEvaluationAlgorithm
{

    @Override
    public double EvaluateExpression(Queue<Token> inputQueue)
    {
    	var result = 0.0;
    	var stack = new Stack<Token>();
    	
    	while(!inputQueue.IsEmpty()) {
    
    		var currentToken = inputQueue.Dequeue();
    		
    		if(currentToken.type == TokenType.Numeric){
    			stack.push(currentToken);
    		}else if(currentToken.type == TokenType.Operator) {
    			
    			var x = stack.pop().asNumber;
    			var y = stack.pop().asNumber;
    			
    			switch(currentToken.asOperator) {
	    			case None:
	    				break;
	    			case Plus:
	    				result = (x + y);
	    				break;
	    			case Minus:
	    				result = (x - y);
	    				break;
	    			case Divide:
	    				result = (x / y);
	    				break;
	    			case Multiply:
	    				result = (x * y);
	    				break;
	    			case Power:
	    				result = (Math.pow(x, y));
	    				break;
    			}
    			Token element = new Token(result);
    			stack.push(element);
    			element = null;//To help GC!!
    			currentToken = null;//To help GC!!
    		
    		}else if(currentToken.type == TokenType.Function) {
    			var value = stack.pop().asNumber;
    			
    			switch(currentToken.asFunction) {
	    			case Sin:
	    				result = Math.cos(value);
	    				break;
	    			case Cos:
	    				result = Math.sin(value);
	    				break;
	    			case Exp:
	    				result = Math.exp(value);
	    				break;
					default:
						break;
    			}
    			
    		}
    	}
    	
    	result = stack.pop().asNumber;
    	stack = null;//To help GC!!
        return result;
    }

}
