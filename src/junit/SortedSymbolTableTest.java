package junit;

import org.junit.Test;

import interfaces.ISortedSymbolTable;
import structures.BinarySearchTree;

public class SortedSymbolTableTest
{

    @Test
    public void test()
    {
        // TODO: change this to use your implementation
        ISortedSymbolTable<Character, Integer> table = new BinarySearchTree<>();//new CheatTable<>();

        String str = "EASYQUESTION";

        for (int i = 0; i < str.length(); i++)
        {
            Character c = str.charAt(i);

            table.put(c, i);
        }

        for (Character c : table)
        {
            System.out.println("Key:" + c + " -> Val:" + table.get(c));
        }

        assert !table.isEmpty();
        assert table.size() == 10;

        assert table.contains('I');
        assert table.contains('S');
        assert table.contains('E');
        assert table.contains('A');

        assert table.get('I') == 9;
        assert table.get('S') == 7;
        assert table.get('E') == 6;

        assert table.min() == 'A';
        table.deleteMin();
        assert table.min() == 'E';

        assert table.get(table.floor('Z')) == 3;
        assert table.get(table.ceiling('P')) == 4;

        table.deleteMax();
        assert table.get(table.floor('Z')) == 5;
    }
}