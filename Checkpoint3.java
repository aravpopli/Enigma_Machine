import java.util.*;

public class Checkpoint3 extends AffineCypher {
  private static ArrayList<String> alphabet;
  private static String reflectorMap = "YRUHQSLDPXNGOKMIEBFZCWVJAT";
  private static ArrayList<String> plugboard;
  private static String rightR = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
  private static String middleR = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
  private static String leftR = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
  private String r1 = "EKMFLGDQVZNTOWYHXUSPAIBRCJ";
  private String r2 = "AJDKSIRUXBLHWTMCQGZNPYFVOE";
  private String r3 = "BDFHJLCPRTXVZNYEIWGAKMUSQO";
  private String r4 = "ESOVPZJAYQUIRHXLNFTGKDCMWB";
  private String r5 = "VZBRGITYUPSDNHLXAWMJQOFECK";
  private int rotorOffset1 = 0;
  private int rotorOffset2 = 0;
  private int rotorOffset3 = 0;

  // encrypts goes right to left
  public Checkpoint3(String command) {
      plugboard = new ArrayList<>();
      plugboard.add("AA");
    if (command.startsWith("ROTORS")) {
      changeRotors(command.substring(command.indexOf("=") + 2));
    } else if (command.startsWith("PLUGBOARD")) {
      command = command.substring(command.indexOf("=") + 2);
      Scanner parser = new Scanner(command);
      while (parser.hasNext()) {
        plugboard.add(parser.next());
      }
    } else if (command.startsWith("OFFSET")) {
      command = command.substring(command.indexOf("=") + 2);
      if (command.length() == 3) {
        this.rotorOffset1 = command.charAt(0) - 65;
        this.rotorOffset2 = command.charAt(1) - 65;
        this.rotorOffset3 = command.charAt(2) - 65;
      } else {
        this.rotorOffset1 = command.charAt(0) - 65;
        this.rotorOffset2 = command.charAt(2) - 65;
        this.rotorOffset3 = command.charAt(4) - 65;
      }
    }
  }

  // swap letter on plugboard
  // implement rotor shifting
  // go through rotors
  // find index of final letter on rotor and map to reflector
  // go through again with reflectin and swap plugboard again

  private void changeRotors(String command) {
    Scanner parser = new Scanner(command);
    String s = "";
    String[] array = {leftR, middleR, rightR };
    for (int rotor = 0; rotor < 3; rotor++) {
      s = parser.next();
      if (s.equals("I")) {
        array[rotor] = r1;
      } else if (s.equals("II")) {
        array[rotor] = r2;

      } else if (s.equals("III")) {
        array[rotor] = r3;

      } else if (s.equals("IV")) {
        array[rotor] = r4;

      } else {
        array[rotor] = r5;

      }
    }
  }

  public String encryptIt(String prompt, String rotor) {
    String encryptedText = "";
    setMap(r1);
    int offSet = prompt.charAt(prompt.indexOf("R") + 1) - 'A';
    String text = prompt.substring(prompt.indexOf(" ") + 1);
    if (prompt.charAt(prompt.indexOf("R") + 1) == ' ') {
      return affine(text);
    } else {
      // take character move by pad (plus 5) A goes to F
      // now find 5th index in rotor map which is L
      // L - 5 which is G
      for (int x = 0; x < text.length(); x++) {
        int index = caesarCipher(text.charAt(x), offSet) - 65;
        String a = list.get(index);
        char z = (char) (((a.charAt(0) + 'A' - offSet) % 26) + 'A');
        encryptedText += z + "";
      }
      return encryptedText;
    }
  }

  public String decryptIt(String prompt) {
    String decryptedText = "";
    setMap(r1);
    int offSet = prompt.charAt(prompt.indexOf("R") + 1) - 'A';
    String text = prompt.substring(prompt.indexOf(" ") + 1);
    if (offSet == -33) {
      return deffine(text);
    } else {
      for (int i = 0; i < text.length(); i++) {
        char s = text.charAt(i);
        s += offSet;
        if (s - 0 > 90) {
          s = (char) (((text.charAt(i) - 'A' + offSet) % 26) + 'A');
        }
        int z = list.indexOf(s + "");
        int a = z + 65;
        decryptedText += (char) (a - offSet);
      }
      return decryptedText;
    }
  }

  private char caesarCipher(char a, int pad) {
    return (char) (((a - 'A' + pad) % 26) + 'A');
  }


// plugboard
// rotors 
// reflect 
// rotors 
// plugboard
  private String plugboardCharacter(String command) {
    for (int plugs = 0; plugs < plugboard.size(); plugs++) {
      if (command.equals((plugboard.get(plugs).charAt(0) + ""))) {
        return plugboard.get(plugs).charAt(1) + "";
      } else if (command.equals((plugboard.get(plugs).charAt(1)) + ""))
        return plugboard.get(plugs).charAt(0) + "";
    }
    return command;
  }

  public String threeRotorDecryption(String text) {
    String decryptedText = "";
    boolean flag = text.contains("E");
    if (text.charAt(text.indexOf("E") + 1) != ' ') {
      this.rotorOffset1 = text.charAt(2) - 65;
      this.rotorOffset2 = text.charAt(3) - 65;
      this.rotorOffset3 = text.charAt(4) - 65;
      text = text.substring(text.indexOf("E") + 5);
      for (int i = 0; i < text.length(); i++) {
        String a = threeRotorDecryptIt(text.charAt(i) + "", leftR, rotorOffset1);
        a = threeRotorDecryptIt(a, middleR, rotorOffset2);
        a = threeRotorDecryptIt(a, rightR, rotorOffset3);
        decryptedText += a;
      }
    } else {
      text = text.substring(text.indexOf(" ") + 1);
      for (int x = 0; x < text.length(); x++) {
        setMap(leftR);
        String a = deffine(text.charAt(x) + "");
        setMap(middleR);
        a = deffine(a);
        setMap(rightR);
        a = deffine(a);
        if(!flag) 
         a = plugboardCharacter(a);
        decryptedText += a;
      }
    }
    return decryptedText;
  }
  public String fullEnigma(String command) {
    String word = threeRotorEncryption(command);
    word = threeRotorEncryption(word);
    return word;
  }
  public String threeRotorEncryption(String text) {
    boolean flag = text.contains(">E");
    String encryptedText = "";
    if (flag && text.charAt(text.indexOf("E") + 1) != ' ') {
      this.rotorOffset1 = text.charAt(2) - 65;
      this.rotorOffset2 = text.charAt(3) - 65;
      this.rotorOffset3 = text.charAt(4) - 65;
      text = text.substring(text.indexOf("E") + 5);
      for (int i = 0; i < text.length(); i++) {
        String a = threeRotorEncryptIt(text.charAt(i) + "", rightR, rotorOffset3);
        a = threeRotorEncryptIt(a, middleR, rotorOffset2);
        a = threeRotorEncryptIt(a, leftR, rotorOffset1);
        encryptedText += a;
      }
    } else {
      text = text.substring(text.indexOf(" ") + 1);
      for (int x = 0; x < text.length(); x++) {
        setMap(rightR);
        String a = affine(plugboardCharacter(text.charAt(x) + ""));
        a = a.replace(" ", "");
        setMap(middleR);
        a = affine(a);
        a = a.replace(" ", "");
        setMap(leftR);
        a = affine(a);
        a = a.replace(" ", "");
        int index = a.charAt(0) - 65;
        if(!flag)
         a = reflectorMap.charAt(index) + "";
         encryptedText += a;
      }
    }
    return encryptedText;
  }
  public String threeRotorDecryptIt(String text, String rotor, int offset) {
    String encryptedText = "";
    for (int x = 0; x < text.length(); x++) {
      String a = ((char) (((text.charAt(x) - 'A' + offset) % 26) + 'A')) + "";
      int index = rotor.indexOf(a);
      encryptedText += (char) (((((char) (index + 65)) + 'A' - offset) % 26) + 'A');
    }
    return encryptedText;
  }

  private String threeRotorEncryptIt(String prompt, String rotor, int offset) {
    String encryptedText = "";
    for (int x = 0; x < prompt.length(); x++) {
      int index = (char) (((prompt.charAt(0) - 'A' + offset) % 26) + 'A') - 65;
      String a = rotor.charAt(index) + "";
      char z = (char) (((a.charAt(0) + 'A' - offset) % 26) + 'A');
      encryptedText += z + "";
    }
    return encryptedText;
  }

}