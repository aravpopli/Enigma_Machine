import java.util.*;
public class Encryption  {
  public static char[] list;
  public Encryption() {
    Encryption.list = new char[26];
    loadList();
  }
  
  private void loadList() { 
   for(int i = 0; i < 26; i++) { 
      list[i] = (char) ('A' + i);
    } 
  } 
  // 
  public String encrypt(String word) { 
    word = removePunctuation(word);
    String code = word.substring(0, word.indexOf(" "));
     String[] wordArray = code.split(">C");
    int pad = 1;
    String encrypted = "";
    if(wordArray.length == 2) {
       pad = Integer.parseInt(wordArray[1]);
    }
      word = word.substring(word.indexOf(" ") + 1);
      for(int i = 0; i < word.length(); i++) { 
         if(word.substring(i, i + 1).equals(" "))
           encrypted += " ";
        else {
          encrypted += (char) (((word.charAt(i) - 'A' + pad) % 26) + 'A');
        }
      }
      return encrypted;    
  }     

  private String removePunctuation(String token) {
    token = token.replace(".", "");
    token = token.replace("!", "");
    token = token.replace(",", "");
    token = token.replace("(", "");
    token = token.replace(")", "");
    return token;
  }

  public String decrypt(String word) {
    String code = word.substring(0, word.indexOf(" "));
     String[] wordArray = code.split("<C");
    int pad = 1;
    String encrypted = "";
    if(wordArray.length == 2) {
       pad = Integer.parseInt(wordArray[1]);
    }
      word = word.substring(word.indexOf(" ") + 1);
      for(int i = 0; i < word.length(); i++) { 
         if(word.substring(i, i + 1).equals(" "))
           encrypted += " ";
        else {
          encrypted += (char) (((word.charAt(i) + 'A' - pad) % 26) + 'A');
        }
      }
      return encrypted;
  }

}

