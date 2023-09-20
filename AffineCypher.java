import java.util.*;
public class AffineCypher extends Encryption {
  public static ArrayList<String> list;

  // This method constructs the encryption map 
  public static void setMap(String map) {
    AffineCypher.list = new ArrayList<String>();
    for(int i = 0; i < map.length(); i++) {
      list.add(map.substring(i, i+1));
    }    
  }

  // This method encrypts the word using the 
  // custom user map 
  public static String affine(String input) {
    input =  input.toUpperCase();
    String affined = "";
    for(int i = 0; i < input.length(); i++) {
      int index = (int)(input.charAt(i)) - 65;
      if(input.substring(i, i + 1).equals(" ")) 
        affined += " ";
      else {
        affined += list.get(index);
      }
    }
    return affined;
  }

  
  // deffine = opposite of affine
  // This method decrypts a word using the custom 
  // map defined by the user 
  public static String deffine(String input) {
    input =  input.toUpperCase();
    String deffine = "";
    for(int i = 0; i < input.length(); i++) {
        int indicie = list.indexOf(input.substring(i, i + 1));
        // check space because then you need to decrypt with a space
        if(input.substring(i, i + 1).equals(" ")) 
        deffine += " ";
        else {
        deffine += ((char)(65 + indicie));
      }
    }
    return deffine;
  }
  

  
} 