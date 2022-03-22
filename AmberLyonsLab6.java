import java.io.*;
import java.util.Scanner;
import java.util.InputMismatchException;
class MyCipher{
    private int key;
    public MyCipher(){
        key = 0;
    }
    public void setKey(int key){
        this.key = key;
    }
    public int getKey(){
        return key;
    }
    public String encode(String s){
        String en = "";
        for(int i = 0; i < s.length(); i++){
            char temp = s.charAt(i);
            //Converts the character at i into an ascii number
            int asc = (int)temp;
            //If the number we get from adding the key to it is larger than the ascii values for lowercase letters change it
            if(asc + key > 122){
                //If the number we get from subtracting 26 - the two values added together is greater than 97 (the value of a) convert it to a and add the difference.
                if((26 - (asc + key)) <= -96){
                    asc = (((26 - (asc + key))*-1) - (key-(122-asc))+1);
                }
                else{
                    //Takes 26 because there are 26 letters and finds the difference then converts it to a positive number
                    asc = (26 - asc)*-1;
                }
            }
            //If the character is a space
            else if(asc + key <= 96){
                asc = 96 + key;
            }
            //Adds the key to the ascii number
            else{
                asc = asc + key;
            }
            //Converts the ascii number back to a character
            temp = (char)asc;
            //Adds the letter to the string
            en = en + temp;
        }
        //Returns the string back to the user
        return en;
    }
}
public class AmberLyonsLab6 {
    public static void main(String[] args) throws IOException{
        try{
            //See if an encoded file already exists, and quit program if it does.
            File encoded = new File("c:\\java\\encoded.txt");
            if(encoded.exists()){
                System.out.println("The cipher file already exists.");
                System.exit(0);
            }
            //Create new cipher object
            MyCipher cipher = new MyCipher();
            
            //Get key from user and make sure the data entered by the user is a positive integer
            boolean continueInput = true;
            Scanner enkey = new Scanner(System.in);
        do{
            try{
                
                System.out.println("Please enter a key to encode your cipher with: ");
                int key = enkey.nextInt();
                while(key < 0){
                    System.out.println("You must enter a number 0 or greater.");
                    System.out.println("Enter a positive integer: ");
                    key = enkey.nextInt();
                }
                continueInput=false;
                cipher.setKey(key);
            //Make sure data is an integer
            } catch(InputMismatchException ex){
                System.out.println("Try again. Incorrect input: an integer is required.");
                enkey.nextLine();
            }
        } while(continueInput);
        
            //Get name of txt file from user
            Scanner usertxt = new Scanner(System.in);
            System.out.println("Enter a the name of the text file you want to cipher (Example: \"file.txt\"): ");
            String fileName = ("c:\\java\\"+usertxt.nextLine()+"");
            File userTxt = new File(fileName);
        
            //Make it so we can add outputs to the new txt file
            FileWriter fEncoded = new FileWriter(encoded);
            PrintWriter outputEncode = new PrintWriter(fEncoded);
            
            //Scans the user created file
            Scanner in = new Scanner(userTxt);   
            
            //Takes each line from the user created file and encodes it
            while(in.hasNextLine()){
                String s = in.nextLine();
                outputEncode.println(cipher.encode(s));
            }
            //Stops the scanner and the print writer methods
            in.close();
            outputEncode.close();
        //Tells the user if there is something wrong when executing the program    
        }catch(IOException ex){
            System.out.println("ERROR: Could not find the file with the specified name.");
        }
    }
}
