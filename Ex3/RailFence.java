package rail;
import static java.lang.Math.abs;
import java.util.Scanner;

public class Rail {
    public static void main(String[] args) {
        int key = 0;int i = 0; int j = 0; int len = 0;
        
        Scanner reader = new Scanner(System.in);
        Scanner keyread = new Scanner(System.in);
        
        System.out.println("\nEnter the key: ");
        key = keyread.nextInt();
        
        System.out.println("\nEnter the plain text: ");
        String message = reader.nextLine();
        len = message.length();
        System.out.println(len);
        System.out.println(key);
        char matrix [][] = new char [key][len];
        for (i=0;i<len;i++){
            for (j=0;j<key;j++)
                matrix[j][i]='*';
        }
        char[] a = message.toCharArray();
        int ind;
        int tkey = key - 1;
        for (i=0;i<len;i++){
            ind = tkey - Math.abs(tkey-i%(2*tkey));
            matrix[ind][i]=a[i];
            System.out.println(i+","+ind+","+a[i]);
        }
        for (i=0;i<key;i++){
            for (j=0;j<len;j++)
                System.out.print(matrix[i][j]);
            System.out.print("\n");
        }
        System.out.println("ENCRYPTED :");
        for (i=0;i<key;i++){
            for (j=0;j<len;j++)
                System.out.print(matrix[i][j]);
        }
        System.out.println("\n\nDECRYPTION :");
        System.out.println("\nEnter the key for decryption: ");
        int dec_key = keyread.nextInt();
        tkey = dec_key -1;
        for (i=0;i<len;i++){
            ind = tkey - Math.abs(tkey-i%(2*tkey));
            System.out.print(matrix[ind][i]);
        }        
    }
    
}
