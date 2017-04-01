package edu.wou.cs260.SpellChecker;

//===================================================================

import java.util.Iterator;

/**
 * This project is a simple spellchecker that just identifies which
 * words are correctly spelled in any text file.  The real primary purpose is
 * for a more robust test of student ADT implementations.
 *
 * @author Mitchel Fry
 * @version 1.2
 */
public class SpellCheckUser
{
    // instance variables - replace the example below with your own
    private long totalWords = 0, correctWords = 0, totalCompares = 0,
            runTime = 0;
    private Dictionary dictionary;
    private static final boolean DEBUG = true;

    private String randomDict = ".//English_Dictionary_Randomized.txt";
    private String regularDict = ".//English_Dictionary.txt";
    private String currentDict = regularDict;

    private String largeTextFile = ".//TextFileLarge.txt";
    private String smallTextFile = ".//TextFile.txt";
    private String currentTextFile = smallTextFile;

    private boolean isGui = false;

    // ======================== Constructors =========================

    /**
     * Constructor for objects of class SpellCheckUser
     */
    public SpellCheckUser()
    {
        // Initialize instance variable
        dictionary = new Dictionary();
    }

    public SpellCheckUser(int dictType, int hashSize)
    {
        isGui = true;
        dictionary = new Dictionary(dictType, hashSize);
    }

    // ======================== General Methods ======================

    /**
     * Basic logic for simple word spellchecking
     *
     * @param dictFN Name of the file to use as the dictionary
     * @param chkFN  Name of the file to spellcheck
     */
    public void runSpellChecker(String dictFN, String chkFN)
    {
        // Create the objects and flow of program
        FileParser dictionaryFile = new FileParser(dictFN);
        FileParser checkFile = new FileParser(chkFN);

        // Fill the dictionary with correct words
        String dictWord = dictionaryFile.getNextWord();
        while(dictWord != null)
        {
            dictionary.addWord(dictWord.toLowerCase());
            dictWord = dictionaryFile.getNextWord();
        }

        // Time just the loop for checking all of the words
        long beginTime = getSystemTime();

        // Check each word in words2Check and tally results
        String fileWord = checkFile.getNextWord();
        while(fileWord != null)
        {
            totalWords++;
            if(dictionary.checkWord(fileWord.toLowerCase()))
            {
                if(DEBUG && !isGui)
                {
                    System.out.println("Recognized Word: " + fileWord);
                }
                correctWords++;
            }
            else if(DEBUG && !isGui)
            {
                System.out.println("Unrecognized Word: " + fileWord);
            }
            totalCompares += dictionary.getLastCompareCount();
            fileWord = checkFile.getNextWord();
        }

        // Calculate the total run-time of the checker
        runTime = getSystemTime() - beginTime;
    }

    /**
     * Prints the spellcheck summary results
     */
    public void printResults()
    {
        if(!isGui)
        {
            // Create the objects and flow of program
            System.out.println("=================================================");
            System.out.println("Total words checked:   " + totalWords);
            System.out.println("Total correct words:   " + correctWords);
            System.out.println("Total incorrect words: "
                    + (totalWords - correctWords));
            if(totalWords > 0)
                System.out.println("Average compares per word: "
                        + (totalCompares / totalWords));
            System.out.println("RunTime for checking:  " + (runTime));
        }

    }

    public void printToScreen()
    {

    }

    public long getTotalWordsChecked()
    {
        return totalWords;
    }

    public long getCorrectWords()
    {
        return correctWords;
    }

    public long getIncorrectWords()
    {
        return (totalWords - correctWords);
    }

    public long getAverageCompares()
    {
        if(this.totalWords > 0)
            return (totalCompares / totalWords);
        else
            return 0;
    }

    public long getRunTime()
    {
        return runTime;
    }

    /**
     * Return the current time of the system clock (in milli-seconds). This
     * value is the internal system time that is the measured in the number of
     * nano seconds since Jan. 1, 1970.
     */
    private long getSystemTime()
    {
        return System.nanoTime();
    }

    /**
     * The spellchecker application entry point
     *
     * @param args Command line arguments; not used
     */
    public static void main(String args[])
    {
        // Create the objects and flow of program
        SpellCheckUser spellcheckuser = new SpellCheckUser();
        spellcheckuser.runSpellChecker(".//English_Dictionary_Randomized.txt",
                ".//TextFileLarge.txt");
        spellcheckuser.printResults();
    }
}
