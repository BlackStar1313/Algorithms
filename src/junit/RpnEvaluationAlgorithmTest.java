package junit;

import org.junit.Test;

import algorithm.RpnEvaluationAlgorithm;
import enums.OperatorType;
import structures.Queue;
import structures.Token;

public class RpnEvaluationAlgorithmTest
{

    @Test
    public void evaluateExpression()
    {
        Queue<Token> tokenQueue = new Queue<>();

        tokenQueue.Enqueue(new Token(5));
        tokenQueue.Enqueue(new Token(4));
        tokenQueue.Enqueue(new Token(OperatorType.Plus));

        assert new RpnEvaluationAlgorithm().EvaluateExpression(tokenQueue) == 9;
    }
}