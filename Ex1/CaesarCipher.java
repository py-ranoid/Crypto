
package caeser_cipher;
import java.util.Scanner;

public class CaeserCipher {
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
          
        System.out.println("Enter the message (lower Case, without spaces): ");
        String msg=scan.nextLine(); 
        char[] crypt=new  char[msg.length()];
           
        System.out.println("Enter the key value (displacement): ");  
        int key=scan.nextInt();
        System.out.println("ENCRYPTED :");  
        for(int i=0;i<msg.length();i++)
        {
            if(msg.charAt(i)+key>122)
                crypt[i]=(char)(96+key%26);
            else
                crypt[i]=(char)(msg.charAt(i)+key);
            System.out.print(crypt[i]);                
        }
        System.out.println("\nDECRYPTED : "); 
        for(int i=0;i<msg.length();i++)
        {           
            if(crypt[i]-key<97)
                crypt[i]=(char)(crypt[i]+26-key);
            else
                crypt[i]=(char) (crypt[i]-key);
            System.out.print(crypt[i]);
        }
    }

    private static boolean Equals(char charAt, String _) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
