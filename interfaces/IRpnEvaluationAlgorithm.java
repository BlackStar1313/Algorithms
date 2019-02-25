package interfaces;

import structures.Queue;
import structures.Token;

public interface IRpnEvaluationAlgorithm
{
    double EvaluateExpression(Queue<Token> inputQueue);
}
