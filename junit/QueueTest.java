package junit;

import org.junit.Test;

import structures.Queue;

public class QueueTest
{
    @Test
    public void dequeue()
    {
        Queue<Integer> integerQueue = new Queue<>();
        integerQueue.Enqueue(1);
        integerQueue.Enqueue(2);
        integerQueue.Enqueue(3);

        assert integerQueue.Dequeue() == 1;
        assert integerQueue.Dequeue() == 2;
        assert integerQueue.Dequeue() == 3;
        assert integerQueue.IsEmpty();

        integerQueue.Enqueue(1);
        integerQueue.Enqueue(2);
        integerQueue.Enqueue(3);
        integerQueue.Enqueue(4);

        assert integerQueue.Dequeue() == 1;
        assert integerQueue.Dequeue() == 2;
        assert integerQueue.Dequeue() == 3;
        assert integerQueue.Dequeue() == 4;
        assert integerQueue.IsEmpty();
    }
}