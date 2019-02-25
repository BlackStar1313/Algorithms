package algorithm;

import enums.TokenType;
import interfaces.IShuntingYardAlgorithm;
import structures.Queue;
import structures.Stack;
import structures.Token;

public class ShuntingYardAlgorithm implements IShuntingYardAlgorithm{
	
@Override
public Queue<Token> ConvertToRPN(Queue<Token> input){
	
	var outputQueue = new Queue<Token>();
	var outputStack = new Stack<Token>();

	while(!input.IsEmpty()) {
		var currentToken = input.Dequeue();
		
		if(currentToken.type == TokenType.Numeric){
			outputQueue.Enqueue(currentToken);
		}else if(currentToken.type == TokenType.Function) {
				outputStack.push(currentToken);
		}else if(currentToken.type == TokenType.Operator) {	
			//Check whether the stack is empty or not.
			//If empty then add only the operator.
			if(outputStack.isEmpty()) {
				outputStack.push(currentToken);
			}else {

					//Initializing some useful variables...
					var stop = false;
					var hasHigherPrecedence = false;
					var samePrecedence = false;
					int topOperatorPrecedence = -1, currentOperatorPrecedence = -1;

					//Loop as long as the stack is not empty and some conditions described below are required
					while(!outputStack.isEmpty() && !stop) {

						//Take the operator at the top of the operator stack.
						var topOperator = outputStack.peek();

						//Check if there is a function at the top of the operator stack
						var thereIsFunctionAtTheTop = topOperator.asFunction != topOperator.asFunction.None;

						//Don't take the precedence of the brackets and functions of the top token from the output stack.
						if(topOperator.type != TokenType.Bracket && 
								topOperator.type != TokenType.Function) {
							topOperatorPrecedence = topOperator.GetOperatorPrecedence();
						}

						//Don't take the precedence of the brackets and functions of the current token from the input queue.
						if(currentToken.type != TokenType.Bracket && 
								currentToken.type != TokenType.Function) {
							currentOperatorPrecedence = currentToken.GetOperatorPrecedence();
						}

						//Check higher and same precedence if and only if there were not any brackets and functions.
						if(topOperatorPrecedence != -1 && currentOperatorPrecedence != -1) {
							hasHigherPrecedence = topOperatorPrecedence > currentOperatorPrecedence;
							samePrecedence = topOperatorPrecedence == currentOperatorPrecedence;
						}

						//Check if the operator at the top of the operator stack is not a left bracket
						var isNotALeftBracket = topOperator.asBracket != topOperator.asBracket.Open;

						//the operator '^' is right associative.
						if((thereIsFunctionAtTheTop || hasHigherPrecedence || (samePrecedence && topOperatorPrecedence != 3))
								&& isNotALeftBracket) {
							//If conditions are met then,
							//pop operators from the operator stack onto the output queue.
							var operators =  outputStack.pop();
							outputQueue.Enqueue(operators);
						}else {
							//Otherwise stop looping.
							stop = true;
						}
					}
					//push it onto the operator stack.
					outputStack.push(currentToken);
				}
			}else if(currentToken.type == TokenType.Bracket) {
				switch(currentToken.asBracket) {
				//if the token is a left bracket (i.e. "("), then
				case Open:
					outputStack.push(currentToken);//push it into the operator stack.
					break;
				case Close:
					//while the operator at the top of the operator stack is not a left bracket:
					while(outputStack.peek().asBracket != currentToken.asBracket.Open) {
						//pop the operator from the operator stack onto the output queue.
						var topOperator = outputStack.pop();
						outputQueue.Enqueue(topOperator);
					}
					//FInally, pop the left bracket from the stack.
					outputStack.pop();
					break;
				default:
					break;
				}
			}
		}

		//While there are still operator tokens on the stack
		while(!outputStack.isEmpty()) {
			//pop the operator from the operator stack into the output queue.
			var operator = outputStack.pop();
			outputQueue.Enqueue(operator);
		}
		return outputQueue;
	}
}
