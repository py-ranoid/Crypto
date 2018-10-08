// Refer http://practicalcryptography.com/ciphers/rail-fence-cipher/
import static java.lang.Math.abs;
import java.util.Scanner;

class RailFence {
    public static void main(String[] args) {
        int key = 0;int i = 0; int j = 0; int len = 0;
        
        Scanner reader = new Scanner(System.in);
        Scanner keyread = new Scanner(System.in);
        
        System.out.println("Enter the key : ");
        key = keyread.nextInt();
        
        System.out.println("\nEnter the message : ");
        String message = reader.nextLine();
        len = message.length();

        // Initializing RailFence Cipher matrix
        char matrix [][] = new char [key][len];
        for (i=0;i<len;i++){
            for (j=0;j<key;j++)
                matrix[j][i]='*';
        }
        
        // Setting elements of RailFence matrix
        char[] a = message.toCharArray();
        int ind;
        int tkey = key - 1;
        for (i=0;i<len;i++){
            // Col = array index. Row needs to be calculated.
            ind = tkey - Math.abs(tkey-i%(2*tkey));
            matrix[ind][i]=a[i];
        }

        System.out.println("\nRailFence Matrix :");
        for (i=0;i<key;i++){
            for (j=0;j<len;j++)
                System.out.print(matrix[i][j]);
            System.out.print("\n");
        }

        // Print Encrypted message
        System.out.println("\nENCRYPTED :");
        for (i=0;i<key;i++){
            for (j=0;j<len;j++)
                if (matrix[i][j]!='*')
                    System.out.print(matrix[i][j]);
        }        
        
        System.out.println("\n\nDECRYPTED :");
        tkey = key -1;
        for (i=0;i<len;i++){
            ind = tkey - Math.abs(tkey-i%(2*tkey));
            System.out.print(matrix[ind][i]);
        }
        System.out.println();
    }
    
}
