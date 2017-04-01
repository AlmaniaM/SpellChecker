package edu.wou.cs260.SpellChecker;

import java.util.Iterator;
import java.util.Objects;
import java.util.TreeSet;

/**
 * File Name: ${FILE_NAME}
 * Created by: Alexander Molodyh
 * Western Oregon University
 * Class: CS260
 * Created: 2/9/2017
 * Assignment:
 */
public class TestBSListWithMain
{


    public static void main(String[] args)
    {
        BSTreeSet<Integer> set = new BSTreeSet();

        set.add(new Integer(6));
        set.add(new Integer(3));
        set.add(new Integer(1));
        set.add(new Integer(8));
        set.add(new Integer(7));
        set.add(new Integer(2));
        set.add(new Integer(0));
        set.add(new Integer(10));
        set.add(new Integer(25));
        set.add(new Integer(12));
        set.add(new Integer(24));
        set.add(new Integer(35));
        set.add(new Integer(88));

        set.startPrint();

/*        Object[] intArray = set.toArray();
        Iterator<Integer> iterator = set.iterator();

        System.out.println("Set size is: " + set.size());

        for(int i = 0; i < intArray.length; i++)
            System.out.println(intArray[i]);

        System.out.println("6 is " + nInt.compareTo(tInt));

        System.out.println("\nList " + set.contains(1));

        TreeSet<Integer> treeSet = new TreeSet();

        Iterator<Integer> iterator = set.iterator();

        while(iterator.hasNext())
        {
            System.out.println("Number is: " + iterator.next());
        }

        Integer one = 1;
        Integer ten = 10;

        System.out.println("one is less than ten " + one.compareTo(ten));*/

        /*BSTreeSet<String> set = new BSTreeSet();
        String dictFN = ".//English_Dictionary_Randomized.txt";
        String chkFN = ".//TextFileSmall.txt";

        // Create the objects and flow of program
        FileParser dictionaryFile = new FileParser(dictFN);
        FileParser checkFile = new FileParser(chkFN);

        // Fill the dictionary with correct words
        String dictWord = dictionaryFile.getNextWord();
        while(dictWord != null)
        {
            set.add(dictWord.toLowerCase());
            dictWord = dictionaryFile.getNextWord();
        }

        Iterator<String> iterator = set.iterator();

        System.out.println("Does set contain puggier?: " + set.contains("puggier"));*/
    }

    public static void modify(BSTreeSet<Integer> bt, Integer value)
    {
        bt.add(value);
    }

}
