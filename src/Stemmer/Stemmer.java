package src.Stemmer;
import java.util.HashMap;
import java.util.HashSet;

import src.Utilities.UtilityClass;

public class Stemmer {
        
    public static void main (String args [])  {
        preProcess();
        String myWord = "rationalization";
        System.out.println("initial word : " + myWord);
        System.out.println(RealPorterStemmer(myWord));
        
    }

    public static void preProcess () {
        UtilityClass.preProcess();
    }

    public static String removePunctuation (String corpus) {
        return UtilityClass.removePunctuation(corpus);
    }


  public static HashMap<String, Integer> extractWords (String corpus) {
        return UtilityClass.extractWords(corpus);
  }
  
  public static String RealPorterStemmer (String word) {
         String stem = ApplyStemRules(word);
         System.out.println("Stem is : " + stem);
         //At this point, we have the tag form and the measure, retrieve the stem 
         
        return stem;
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
         String stem = ApplyStemRules(word);
         System.out.println("Stem is : " + stem);
         //At this point, we have the tag form and the measure, retrieve the stem 
         
        return cleanedForm; 
    }

    public static String ApplyStemRules(String word) {
        String stem = word;
        if (word.charAt(word.length() - 1) == 's') { 
             stem = applyStep1a(word);
            System.out.println("About to apply step1b");
        }
       
        stem = applyStep1b(stem);
        
        if (stem.charAt(stem.length() - 1) == 'y') {
          stem = applyStep1c(stem);
        }

       stem = applyStep2(stem);
        
        

        return stem;
    }
    private static String applyStep2 (String word) {
        Boolean none = false;
        String truncatedWord = word;
        switch(word.charAt(word.length() - 1)) {
            case 'l' :
            //rules l
          //   if (word.length() > 6) {

              if (word.endsWith("tional")) {
            System.out.println("ends with tional");                
                 if (word.charAt(word.length() - 7) == 'a') {
            System.out.println("ends with ational");
                    //case for ational
                    //calculate measure here
                    int m = getMeasure(word,
                    word.length() - 7);
                        System.out.println("measure is "+ m);        
                    if (m > 0) {
                     truncatedWord = word.substring(0, word.length() - 7);
                     truncatedWord += "ate";
                     return truncatedWord;
                    }
                 }
                 else {
                 //calculate measure here
                 //case for tional
                    int m = getMeasure(word,
                    word.length() - 6);
                    if (m > 0) {
                     truncatedWord = word.substring(0, word.length() - 6);
                     truncatedWord += "tion";
                     return truncatedWord;
                    }

                 }
               }
            // }
            break;

            case 'i':
            //rules for i;
             if (word.endsWith("nci")) {
                Character tmpLetter = word.charAt(word.length() -4);
                if (tmpLetter == 'a' 
                || tmpLetter == 'e') {
                  int m  = getMeasure(word, word.length() - 4);
                  if (m > 0) {  
                    truncatedWord = truncatedWord.substring(0, truncatedWord.length() - 1);
                    truncatedWord += tmpLetter;
                    return truncatedWord;
                  }
                }
             }
             else if (word.endsWith("li")) {
            
                if (word.substring(0, word.length() - 2)
                .endsWith("al")
                || 
                (word.substring(0, word.length() - 2).endsWith("ab"))) {
                    int m = getMeasure(word, word.length() - 4);
                    if (m > 0) {
                        truncatedWord = word.substring(0, word.length() - 2);

                     if (truncatedWord.charAt(truncatedWord.length()) == 'b')   {
                        truncatedWord += "le";
                     }
                     return truncatedWord;
                    }
                }
                else if (
                 word.substring(0, word.length()- 2)
                .endsWith("e")) {
                    int m = getMeasure(word,word.length() - 3);
                    if (m > 0) {
                        return word.substring(0, word.length() -2);
                    }
                }
                else if (word.substring(0, word.length() - 2)
                .endsWith("ent")) {
                   int m = getMeasure(word,word.length() - 5);
                    if (m > 0) {
                        return word.substring(0, word.length()-2);
                    }
                }
                


             }
             else if (word.endsWith("iti")) {
                if (word.substring(0, word.length() -3)
              .endsWith("al")
              || word.substring(0,word.length()-3)
              .endsWith("iv")) {
                int m = getMeasure(word, word.length() -5);
                if (m > 0) {
                    truncatedWord = word.substring(0, word.length() -3);
                    if (truncatedWord.endsWith("iv")) {
                        truncatedWord += "e";
                        return truncatedWord;
                    }

                }
              }
              else if (word.substring(0, word.length() - 3)
              .endsWith("bil")) {
                //biliti
                int m = getMeasure(word, word.length() -6);
                if (m > 0) {
                    truncatedWord = word.substring(0, word.length() -5);
                    truncatedWord += "le";
                    return truncatedWord;
                }
              }
             }
            break;

            case 'n':
             if (word.endsWith("ation")) {
                if (word.substring(0, word.length() -5)
                .endsWith("iz")) {
                    //ization
                    int m = getMeasure(word, word.length() -5);

                    if (m > 0) {
                    truncatedWord = word.substring(0, word.length()- 5);
                    truncatedWord += 'e';
                    return truncatedWord;
                    }
                }
                else {
                    //ation
                    int m = getMeasure(word, word.length() - 5);
                    if(m > 0)  {
                        truncatedWord = word.substring(0, word.length()- 3);
                        truncatedWord += 'e';
                    
                    return truncatedWord;
                    }
                }
             }
            break;

            case 'r':
             if (word.endsWith("ator")) {
                //and m > 0 
                int m = getMeasure(word, word.length() - 4);
                if (m > 0) {
                truncatedWord = word.substring(0, word.length() - 2);
                truncatedWord += 'e';
                return truncatedWord;
                }
             }
            break;

            case 'm':
             if (word.endsWith("alism")) {
                int m = getMeasure(word, word.length() - 5);
                if (m > 0) {
                    return word.substring(0, word.length()- 3);
                }
            }
            break;

            default :
             none = true;
             break;
        }
                //cases for ness and itis outside of the previous switch

         if (none) {
             switch(word.charAt(word.length() - 1)) {
                case 's' : 
                    if (word.endsWith("ness")) {
                        if (word.substring(0, word.length() -4)
                        .endsWith("ive") 
                        || word.substring(0, word.length() -4)
                        .endsWith("ful")
                        || word.substring(0, word.length() - 4)
                        .endsWith("ous")) {
                            int m = getMeasure(word, word.length() - 4);
                            if (m > 0) {
                                return word.substring(0, word.length() - 4);
                            }
                        }
                    }
                    break;
                case 'I' :
                    if (word.endsWith("iti")) {
                        if (word.substring(0, word.length() - 3)
                        .endsWith("al")
                        || word.substring(0, word.length() -3)
                        .endsWith("iv")
                        || word.substring(0, word.length() - 3)
                        .endsWith("bil")) {
                           int m = getMeasure(word, word.length() - 3);
                           if (m > 0) {
                             truncatedWord = word.substring(0, word.length() - 3);
                            
                            truncatedWord +=  truncatedWord.endsWith("iv") ||
                             truncatedWord.endsWith("bi")?
                             "e" : "";
                             return truncatedWord;
                           }
                         }
                    }                    
             }
           }
        
           return truncatedWord;
    }
    private static String applyStep1c (String word) {
        String tag = getTag(word, word.length() -1);
        System.out.println("Tag is " + tag);
        if (tag.contains("V")) {
            word = word.substring(0, word.length() -1);
            word += 'i';
        }
        return word;
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

    private static String getTag (String word, int length_lim) {
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
       for (int i = 1; i<length_lim; ++i) {
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

         return cleanedForm;
    }
    private static int getMeasure (String word, int length_lim) {
        int measure = 0;  
        String cleanedForm = getTag(word, length_lim); //tag
        System.out.println("Cleaned form tag is :  " + cleanedForm);
         if (cleanedForm.length() == 2) { //trivial case
            if (cleanedForm.charAt(0) == 'V' && cleanedForm.charAt(1) == 'C') {
                //measure is "1"
                measure = 1; 
            }
         } 
         else { //non trivial case
            //
            for (int  i = (cleanedForm.charAt(0) == 'V' ? 0 : 1); i<cleanedForm.length() - 1; ++i) {
                //if ((i+1) != (cleanedForm.length() - 1)) {
                    if (cleanedForm.charAt(i) == 'V' && cleanedForm.charAt(i+1) == 'C')
                        measure +=1;                    
                //}
            }
         }


        return measure;
    }
    public static String applyStep1b (String word) {

        String truncatedWord = word;
        Boolean containsVowel = false;
        Boolean thirdStep = false;
        if ((word.charAt(word.length() - 1) == 'd') && word.charAt(word.length()-2) == 'e') {

            // if (measure > 0) {
            //     if (word.charAt(word.length() -3) == 'e')
            //      truncatedWord = word.substring(0, word.length() - 1);
            // }
            //case 1
            if (word.charAt(word.length() - 3) == 'e') {
                System.out.println("About to apply Case 1");
                int measure = getMeasure(word, word.length() - 2);  
                System.out.println("Measure is  " + measure);

                if (measure > 0)  {
                    System.out.println("About to truncate the word "+ word  + " with measure : " + measure);
                    truncatedWord = word.substring(0, word.length() - 1); //agree[d] -> //agree
                                                                                  //if this was -2 , the window would be //agre[ed] 
                    System.out.println("Word truncated to "+ truncatedWord);
                    
                }
            }
            else {
            //case 2
             System.out.println("About to apply Case 2");
            for (int i =0; i<word.length() - 2; ++i) {
                if (isLetterVowel(i, word)) {
                    containsVowel = true;
                    //truncate word
                    truncatedWord = word.substring(0, word.length() - 2);
                    break;
                }
            }
        }


        }
       
       else if ((word.charAt(word.length() - 1) == 'g') && (word.charAt(word.length() - 2) == 'n') && word.charAt(word.length()- 3) == 'i') {
            //case 3
            for (int i = 0; i<word.length() - 3; ++i) {
                if (isLetterVowel(i, word))  {
                    containsVowel = true;
                    truncatedWord = word.substring(0, word.length() - 3);
                    System.out.println("yes, contains vowel!");
                    break;
                }
            }
            thirdStep = true;
        }
    
     if(containsVowel) { //indicating that 2nd or 3rd step were successful
            System.out.println("Current Word is : " + truncatedWord);
            String suffix = truncatedWord.substring(truncatedWord.length()-2, truncatedWord.length());
           if (suffix.equals("at") || suffix.equals("bl")
              || suffix.equals("iz")) {
                suffix += "e";
                truncatedWord = truncatedWord.substring(0, truncatedWord.length()-2);
                truncatedWord += suffix;
                return truncatedWord;
            }
            //if it hasn't returned until now, that is an indicated that the previous cleanup wans't applied, so we move on to the proceeding cleanup
            System.out.println("About to perform further cleanup");
            if (suffix.charAt(0) == (suffix.charAt(1))) {
                containsVowel = false;
                
                
                for (int i =0; i<suffix.length(); ++i) {
                    char tmpTag = getTag(i, suffix);
                    if (tmpTag == 'V')
                        containsVowel = true;
                }


                if (!containsVowel) {
                    if (suffix.charAt(suffix.length() - 1) != 'l'
                        && suffix.charAt(suffix.length() - 1) != 's'
                        && suffix.charAt(suffix.length() - 1) != 'z') {

                            truncatedWord = truncatedWord.substring(0, truncatedWord.length()-2);
                            truncatedWord += suffix.charAt(0);
                            System.out.println("No vowel and yet truncated word is : " + truncatedWord);
                        }
                
                    }
        }
                        //stem ends with cvc where the 2nd c is not 
             if (thirdStep && truncatedWord.length() == 3) {           
                String tmpTag = getTag(truncatedWord, truncatedWord.length());
                System.out.println("Second tag is : " + tmpTag);
                if (tmpTag.equalsIgnoreCase("cvc")) {
                    Character lastChar = tmpTag.charAt(tmpTag.length() - 1);
                    if (((lastChar) != 'w')
                        || (lastChar != 'x') 
                        || (lastChar != 'y')) {
                            truncatedWord += 'e';
                        }
                }
            }


        }
 
 
        return truncatedWord; //temporarily returning this. But this is to be continued. WE need to follow up the cleaning up steps after 1b
    }
    
    public static char getTag (int indexOfLetter, String word) {

        return isLetterVowel(indexOfLetter, word)? 'V' : 'C';
    }

    public static boolean isLetterVowel(int indexOfLetter, String word) { //because in case of "Y", it can function as both depending on the context    
          return UtilityClass.isLetterVowel(indexOfLetter, word);
    }

    
}
