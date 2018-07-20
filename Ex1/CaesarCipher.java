
package caeser_cipher;
import java.util.Scanner;

public class Caeser_cipher {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
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
            {
                crypt[i]=(char)(96+key%26);
            }
            else
            {
            crypt[i]=(char)(msg.charAt(i)+key);
            }
            System.out.print(crypt[i]);
                
        }
        System.out.println();
        System.out.println("DECRYPTED : "); 
          for(int i=0;i<msg.length();i++)
        {
           
            if(crypt[i]-key<97)
            {crypt[i]=(char)(crypt[i]+26-key);}
            else
            crypt[i]=(char) (crypt[i]-key);
            System.out.print(crypt[i]);
        }
    }

    private static boolean Equals(char charAt, String _) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
