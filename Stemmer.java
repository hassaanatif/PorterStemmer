import java.util.HashMap;
import java.util.HashSet;

public class Stemmer {


    static HashSet<Character> vowels = new HashSet<Character>();
        
    public static void main (String args [])  {
        preProcess();
        String myWord = "feed";
        System.out.println(PorterStemmer(myWord));

        // Boolean isVowel = isLetterVowel(3, myWord);   
        // System.out.println(isVowel);
        

    }

    /*         String corpus = "This is a test Hello World how's are you doing yayay this is really good hello again ";
        corpus = corpus.toLowerCase();
        HashMap<String, Integer> words = extractWords(corpus);
        for (Map.Entry<String, Integer> map : words.entrySet() ) {
            System.out.println(map.getKey() + " : " + map.getValue());
        }
 */    

    public static void preProcess () {
        vowels.add('a');
        vowels.add('e');
        vowels.add('i');
        vowels.add('o');
        vowels.add('u');
        
    }

    public static String removePunctuation (String corpus) {

        return null;
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

    public static String PorterStemmer (String word) {
        Boolean isVowel = isLetterVowel(0, word);
        if (word.length() == 1) {
            return (isVowel)? "V" : "C";
        }
        String form = "";
        if (!isVowel) {
            //Starts with C 
            form +=  "C";
        }
        else { 
               //Starts with V 
            form +=  "V";
        }
        
       int j = 0; 
       for (int i = 1; i<word.length(); ++i) {
           char tag = getTag(i, word);
           if (tag == word.charAt(j));
           else  {
               form += tag;  
               ++j;
              }
            }
        String cleanedForm = "";
        cleanedForm += form.charAt(0);
        //We have got the form at this point, we will now shrink it.
         for (int i = 1; i<form.length(); ++i) {
            if (form.charAt(i) == form.charAt(i-1)) ;
            else 
                cleanedForm += form.charAt(i);
         }
         //continue only if the length of our form is greater than 2 

         int measure  = 0;
         if (cleanedForm.length() == 2) {
            if (cleanedForm.charAt(0) == 'V' && cleanedForm.charAt(1) == 'C') {
                //measure is "1"
                measure = 1; 

            }

         } 
         else {
            //
            for (int  i = (cleanedForm.charAt(0) == 'V' ? 0 : 1); i<cleanedForm.length() - 1; ++i) {
                //if ((i+1) != (cleanedForm.length() - 1)) {
                    if (cleanedForm.charAt(i) == 'V' && cleanedForm.charAt(i+1) == 'C')
                        measure +=1;                    
                //}
            }
         }
         System.out.println("Measure is : " + measure);
         String stem = ApplyStemRules(cleanedForm, measure, word);
         System.out.println("Stem is : " + stem);
         //At this point, we have the tag form and the measure, retrieve the stem 
         
        return cleanedForm; 
    }

    public static String ApplyStemRules(String cleanedForm, int measure, String word) {
        String stem = word;
        if (word.charAt(word.length() - 1) == 's') { 
             stem = applyStep1a(word);
            System.out.println("About to apply step1b");
        }
       
        stem = applyStep1b(stem,measure);
        

        return stem;
    }
    private static String applyStep1a (String word) {
        Character tmpChar = word.charAt(word.length() - 2);
        String truncatedWord = "";
        if (tmpChar == 's') {
            //ss
            return word;
        }
        else if (tmpChar == 'e')  {
            if (word.charAt(word.length() - 3) == 'i') {
                //IES -> I 
                truncatedWord = word.substring(0, word.length() - 2); //we will just leave out the i
            }
            else if (word.charAt(word.length() - 3) == 's' && word.charAt(word.length() - 4) == 's') {
                //SSES -> SS
                truncatedWord = word.substring(0, word.length() - 2); //we will just leave out the ss
            }
        }
        else { //if it is just a single 's' at the end
            truncatedWord = word.substring(0, word.length() - 1); //we will just leave out the i
        }
        return truncatedWord;
    }

    public static String applyStep1b (String word, int measure) {

        String truncatedWord = word;
        Boolean containsVowel = false;
        if ((word.charAt(word.length() - 1) == 'd') && word.charAt(word.length()-2) == 'e') {
            if (measure > 0) {
                if (word.charAt(word.length() -3) == 'e')
                 truncatedWord = word.substring(0, word.length() - 1);
            }
            //case 2
            for (int i =0; i<word.length() - 2; ++i) {
                if (isLetterVowel(i, word)) {
                    containsVowel = true;
                    //truncate word
                    truncatedWord = word.substring(0, word.length() - 2);
                    break;
                }
            }

        }
       

        if ((word.charAt(word.length() - 1) == 'g') && (word.charAt(word.length() - 2) == 'n') && word.charAt(word.length()- 3) == 'i') {
            //case 3
            for (int i = 0; i<word.length() - 3; ++i) {
                if (isLetterVowel(i, word))  {
                    containsVowel = true;
                    truncatedWord = word.substring(0, word.length() - 3);
                    System.out.println("yes, contains vowel!");
                    break;
                }
            }
        }
        

        return truncatedWord; //temporarily returning this. But this is to be continued. WE need to follow up the cleaning up steps after 1b
    }
    
    public static char getTag (int indexOfLetter, String word) {

        return isLetterVowel(indexOfLetter, word)? 'V' : 'C';
    }

    public static boolean isLetterVowel(int indexOfLetter, String word) { //because in case of "Y", it can function as both depending on the context
  
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
