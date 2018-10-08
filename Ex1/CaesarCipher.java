// Refer http://practicalcryptography.com/ciphers/caesar-cipher/
import java.util.Scanner;
class CaeserCipher {
    
    static char[] enc(String msg,int key){
        char[] crypt=new char[msg.length()];
        // Converting key to a +ve number less than 26
        key = key%26;
        if (key<0)
            key += 26;
        for(int i=0;i<msg.length();i++)
        {
            if (msg.charAt(i)==' ')
                // Ignoring Spaces
                crypt[i]=' ';
            else if(msg.charAt(i)+key>'z')
                // Shift character by "key" letters
                // Subtract 26 if it exceeds z
                crypt[i]=(char)(msg.charAt(i)+key-26);
            else
                // Shift character by "key" letters
                crypt[i]=(char)(msg.charAt(i)+key);
        }
        return crypt;
    }
    
    static int crypt_analysis(String enc_message){
        String words[] = {"hey","hello","hi"};
        String message_parts[] = enc_message.split(" ");
        int final_key = 0;
        boolean flag = false;
        for (int i=0;i<message_parts.length;i++){
            // Iterating over words
            for (int j=1;j<27;j++){
                // Iterating key value between 1 and 26
                String dec_word = new String(enc(message_parts[i],-1*j));
                for (int k=0;k<words.length;k++){
                    // Check if decrypted word for key is in words
                    if (dec_word.matches(words[k])){
                        System.out.println("Key Matched :"+j);
                        final_key = j;
                        flag = true;
                    }
                    if (flag)
                        break;
                }
                if (flag)
                    break;
            }
            if (flag)
                break;
        }
        if (flag)
            return final_key;
        else
            return -1;
    }
    

    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        
        System.out.println("Enter the message (lower Case): ");
        String msg=scan.nextLine().toLowerCase();
        
        System.out.println("Enter the key value (displacement): ");
        int key=scan.nextInt();

        char[] encrypted = enc(msg,key);
        System.out.println("Encrypted : "+String.valueOf(encrypted));

        char[] decrypted = enc(new String(encrypted),-key);
        System.out.println("Decrypted : "+String.valueOf(decrypted));

        // Cryptographic analysis
        Scanner scan2=new Scanner(System.in);
        System.out.println("\nEnter the encrypted string : ");
        String msg2=scan2.nextLine();
        
        int crypt_key = crypt_analysis(msg2);
        if (crypt_key>=0){
            String decrypted_message = new String(enc(msg2,-crypt_key));
            System.out.println("DECRYPTED :"+decrypted_message);
        }
        else{
            System.out.println("Key couldn't be found");
        }
    }
}
