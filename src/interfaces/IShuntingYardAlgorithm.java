package interfaces;

import structures.Queue;
import structures.Token;

public interface IShuntingYardAlgorithm
{
    Queue<Token> ConvertToRPN(Queue<Token> input);
}
