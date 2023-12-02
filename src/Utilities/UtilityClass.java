package src.Utilities;

import java.util.HashMap;
import java.util.HashSet;

public class UtilityClass {


    static HashSet<Character> vowels = new HashSet<Character>();

    public static void preProcess () {
        populateVowels();
        }
    private static void populateVowels () {
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
    }

    public static HashMap<String, Integer> extractWords (String corpus) {
        if (corpus.charAt(corpus.length() -1) != ' ')
             corpus = corpus + " ";   //this is just to potentially prevent the last word from being truncated     
        HashMap<String, Integer> words = new HashMap<String, Integer>();
        int window = 0; 
        for (int i = 0; i<corpus.length() - 1; ++i) {

            if ((corpus.charAt(i+1) == ' ' || corpus.charAt(i+1) == '\n' || ((i+1) ==corpus.length() -1))  && (corpus.charAt(i) != ' ')) {
               String word = corpus.substring(window, i+1);
               word = word.stripLeading();
               System.out.println("Word detected :" + word);

                if (words.containsKey(word)) {
                    int count = words.get(word);
                    words.put(word, ++count);
                }
                else { 
                    words.put(word, 1);
                }
                window = i+1;
            }
        }
        System.out.println("\n\n\n\n");
        return words;
    }

    public static String removePunctuation (String corpus) {
        return "";
    }

    public static Boolean isLetterVowel (int indexOfLetter, String word) {
        if(vowels.contains(word.charAt(indexOfLetter))) 
            return true;

        else if (word.charAt(indexOfLetter) == 'y') {
            //rules for y
            if (indexOfLetter == 0)
                return false; //if 'y' appears at the beginning then its a consonant
            else if (vowels.contains(word.charAt(indexOfLetter -1)))
                return false; //if 'y' is preceeded by a vowel then 'y' is a consonant
            return true; 
        }    
        return false;
    }
}
