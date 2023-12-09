# PorterStemmer

Note: This Module is a work in progress. The goal is to write a Porter Stemmer in Java that will systematically generate stems from a given corpus. This is intended to be a small portion of a larger (NLP Related) framework in Java. 


Hello Fellow devs, this project is a mini passion project that I started over a weekend and then iteratively improved upon. With this you can retrieve the stem of a word based on the rules of a porter stemming algorithm (akin to the one from libs & frameworks like nltk). 

***How to use it***
1. git clone the project
2. run with the following command   :
     java ./src/Main.java
3. Feel free to experiment with it and tweak it as per your needs.

Class "PStemmer" in the package "src.Stemmer" is a self-contained file of the Porter Stemming algorithm. The method "RealPorterStemmer" inside this class is responsible for returning the stem of a word. 
With little modifications, you can retrieve all the stems from a given corpus (**make sure to remove the punctuation before stemming**). 
I shall keep updating this repo because there is actually a visual tool planned to assist the user in making it easy to understand the intuitiveness behind the Porter stemming algorithm.

Happy Coding! 

~ Hassaan Atif
