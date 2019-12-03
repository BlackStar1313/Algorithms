package junit;

import org.junit.Test;

import algorithm.ShuntingYardAlgorithm;
import enums.OperatorType;
import structures.Queue;
import structures.Token;

public class ShuntingYardAlgorithmTest {

    @Test
    public void convertToRPN() {
        Queue<Token> inQ = new Queue<>();
        Token three = new Token(3), plus = new Token(OperatorType.Plus), four = new Token(4);

        inQ.Enqueue(three);
        inQ.Enqueue(plus);
        inQ.Enqueue(four);

        Queue<Token> outQ = new ShuntingYardAlgorithm().ConvertToRPN(inQ);

        assert outQ.Dequeue() == three;
        assert outQ.Dequeue() == four;
        assert outQ.Dequeue() == plus;
    }
}