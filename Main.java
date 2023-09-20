import java.util.*;
// https://www.101computing.net/enigma-machine-emulator/
// Checkpoint #1 commands:
//   >C text
//   <C text
//   >C8 text
//   <C8 text
//
// Checkpoint #2 commands:
//   >A text
//   <A text
//   amap = CBDFEGHKJILMNOPQRSTUVWXYZA   // the affine map
//
// Checkpoint #3 commands:
//   >R text  // encrypt text using Rotor I only, 1-way, toward reflector
//   <R text  // decrpyt text using Rotor I only, 1-way, toward plugboard
//   >RC text // encrypt text, Rotor I, with an offset of C
//   <RK test // decrypt text, Rotor I, with an offset of K
//
// Checkpoint #4 commands:
//   >E text     // encrypt 1-way, toward refelctor, using Rotors I,II,III. No offset.
//   <E text     // encrypt 1-way, toward plugboard, using Rotors I,II,III. No offset.
//   >EABC text  // encrypt 1-way, toward refelctor, using Rotors I,II,III. offset = A B C
//   <EABC text  // encrypt 1-way, toward plugboard, using Rotors I,II,III. offset = A B C
//
//   rotors = II II I
//   offset = N D O
//   plugboard = AZ BY CX DW EV FU GT HS IR JQ
//
//  if command has no '='', and does not start with '>' or '<' 
//     then just use Enigma Machine to encrypt/decrypt
//
//   BARRELROLL    // encrpt the text using settings
//   RQGMLJZFCE    // encrpt the text using settings
//   Hello World   // Will also removes spaces. converts to upper case.
//   Hello, World  // removes punctuation, too.
//
// Finally, if there are two sections of text, assume one is plain text and the other
// is Enigma Text. The goal is to crack the machine and identify the rotors, offsets and plugboard.
//   VIMQUICKBROWNFOXESJUMPEDOVERTHELAZYDOG = QHYVRDMEIDSCMKCJNUFBYRPFADZSGROMGDZGBX

public class Main {

    public static Scanner console = new Scanner(System.in);
   
    public static void main(String[] args) {
        Main program = new Main();
        program.runInputLoop();
    }

    public String processCommand(String command) {
      command = command.toUpperCase();
       Encryption e = new Encryption();
       Checkpoint3 rotors = new Checkpoint3(command);
        String result = "";
         if(command.startsWith(">C"))
            result =  e.encrypt(command);
         else if(command.startsWith("<C"))
             result = e.decrypt(command);
         else if(command.startsWith("AMAP")) {
          AffineCypher.setMap(command.substring(command.indexOf("=") + 2));
         }
         else if(command.startsWith(">A"))
           result = AffineCypher.affine(command.substring(command.indexOf(">A") + 3));
        else if(command.startsWith("<A"))
          result = AffineCypher.deffine(command.substring(command.indexOf("<A") + 3));
        else if(command.startsWith(">R")) {
          result = rotors.encryptIt(command, "EKMFLGDQVZNTOWYHXUSPAIBRCJ");
        }
        else if(command.startsWith(">E")) {
          result = rotors.threeRotorEncryption(command);
        }
        else if(command.startsWith("<R")) {
          result = rotors.decryptIt(command);
        }
        else if(command.startsWith("<E")) {
          result = rotors.threeRotorDecryption(command);
        }
        else if(!command.startsWith("PLUGBOARD") && !command.startsWith("OFFSET") && !command.startsWith("ROTORS")) {
          result = rotors.fullEnigma(command);
        }
        return result;
    }

    private void runInputLoop() {
        boolean done = false;
        while (!done) {
            System.out.print("Enter: ");
            String input = console.nextLine();
               input = input.trim();
            if (input.equalsIgnoreCase("quit")) {
                done = true;
            } else if (input.length() > 0 && !UnitTestRunner.processCommand(input, this::processCommand)) {
                String result = processCommand(input);
                System.out.println(result);
            }
        }
    }
}