import java.util.*;
import Stemmer.*;

public class Main  {


    public static void main (String args []) {
        String word = "Consultantative";
        System.out.println(Stemmer.PorterStemmer(word));
    }
}