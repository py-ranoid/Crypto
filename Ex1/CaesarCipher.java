import java.util.Scanner;

class CaeserCipher {
    static char[] enc(String msg,int key){
        char[] crypt=new  char[msg.length()];
        for(int i=0;i<msg.length();i++)
        {
            if (msg.charAt(i)==' '){
                crypt[i]=' ';
                continue;
            }
            if(msg.charAt(i)+key>122)
                crypt[i]=(char)(96+key%26);
            else
                crypt[i]=(char)(msg.charAt(i)+key);
        }
        return crypt;
    }
    public static void main(String[] args) {
        Scanner scan=new Scanner(System.in);
        String words[] = {"hey","hello","hi"};
        System.out.println("Enter the message (lower Case, without spaces): ");
        String msg=scan.nextLine();
        char[] crypt=new  char[msg.length()];

        System.out.println("Enter the key value (displacement): ");
        int key=scan.nextInt();
        System.out.println("ENCRYPTED :");
        char[] encrypted = enc(msg,key);
        System.out.println(encrypted);

        System.out.println("\nDECRYPTED : ");
        char[] decrypted = enc(new String(encrypted),-1* key);
        System.out.println(decrypted);

        Scanner scan2=new Scanner(System.in);
        System.out.println("Enter the encrypted string");
        String msg2=scan2.nextLine();
        String message_parts[] = msg2.split(" ");
        int final_key = 0;
        boolean flag = false;
        for (int i=0;i<message_parts.length;i++){
            for (int j=1;j<27;j++){
                String dec_word = new String(enc(message_parts[i],-1*j));
                for (int k=0;k<words.length;k++){
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
        if (flag){
            String decrypted_message = new String(enc(msg2,-1*final_key));
            System.out.println("DECRYPTED :"+decrypted_message);
        }

    }

    private static boolean Equals(char charAt, String x) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
