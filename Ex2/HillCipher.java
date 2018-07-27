import java.io.*;
import java.util.*;
import java.lang.Math.*;

public class HillCipher {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nHILL CIPHER\n");
        System.out.println("1. Encryption\n2. Decryption\n3. Exit");
        int choice = -1;
        Methods method= new Methods();

        while(choice != 3) {
            System.out.print("\nEnter an option : ");
            choice = scanner.nextInt();
            switch(choice) {
                case 1:
                    method.Encrypt();
                    break;
                case 2:
                    method.Decrypt();
                    break;
                case 3:
                    break;
            }
        }
    }
}

class Methods {

    int GCD(int m,int n){
        if(m==0)
            return n;
        return GCD(n%m,m);
    }

    boolean Invertible(int[][] A) {
        int det = 0;
        for(int i=0;i<3;i++) {
            int a = 1;
            int b = (i+1)%3;
            int partial = (A[a][b] * A[(a+1)%3][(b+1)%3]);
            partial -= (A[a][(b+1)%3] * A[(a+1)%3][b]);
            partial *= A[0][i];
            det += partial;
        }

        if(det == 0){
            System.out.println("The given key matrix is not Invertible");
            return false;
        }

        // Have to find d^-1
        // d^-1 does not exist if gcd(d,26)<> 1
        // In that case find a different key
        if(GCD(det,26) != 1){
            System.out.println("The inverse key does not exist for the given key matrix");
            return false;
        }
        System.out.println("The given key matrix is Invertible");
        return true;
    }

    int[][] Inverse(int[][] A) {

        int det = 0;
        for(int i=0;i<3;i++) {
            int a = 1;
            int b = (i+1)%3;
            int partial = (A[a][b] * A[(a+1)%3][(b+1)%3]);
            partial -= (A[a][(b+1)%3] * A[(a+1)%3][b]);
            partial *= A[0][i];
            det += partial;
        }

        //Find determinant modulo 26
        while(det<0 || det>25){
            if(det<0)
                det+=26;
            else det%=26;
        }

        //Find inverse determinant
        int inverseDet=0;
        for(int i=1;i<=25;i++) {
            if((det*i)%26 == 1){
                inverseDet = i;
                break;
            }
        }

        //transpose
        for(int i=0;i<3;i++)
            for(int j=0;j<i;j++){
                int temp = A[i][j];
                A[i][j] = A[j][i];
                A[j][i] = temp;
            }

        int[][] inverseMatrix = new int[3][3];

        for(int i=0;i<3;i++) {
            int minorDet = 0;
            for(int j=0;j<3;j++) {
                int a = (i+1)%3;
                int b = (j+1)%3;
                minorDet = (A[a][b] * A[(a+1)%3][(b+1)%3]);
                minorDet -= (A[a][(b+1)%3] * A[(a+1)%3][b]);
                minorDet*=inverseDet; // d^-1 * Adj(A)
                inverseMatrix[i][j] = minorDet;
            }
        }
        return inverseMatrix;
    }

    int[] MatrixMultiply(int A[],int B[][]) {
        int sum[] = new int[3];
        for(int i=0;i<3;i++) {
            sum[i] = 0;
            for(int j=0;j<3;j++)

                sum[i] += (A[j]*B[j][i]);
            sum[i] = sum[i]%26;
        }
        return sum;
    }

    public void Encrypt() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nENCRYPTION");
        System.out.print("\nEnter the plain text : ");
        String plainText = scanner.next();
        System.out.println("Enter the key matrix : ");
        int key[][] = new int[3][3];
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                key[i][j] = scanner.nextInt();

        if(!Invertible(key))
            return;

        int len = plainText.length();
        String cipherText = "";
        for(int i=0;i<len;) {
            int[] pair = new int[3];
            for(int j=0;j<3;j++) {
                if(i<len)
                    pair[j] = plainText.charAt(i++) - 65;
                else pair[j] = 0;
            }

            pair = MatrixMultiply(pair,key);
            for(int j=0;j<3;j++) {
                cipherText +=(char)(pair[j] + 65);
            }
        }

        System.out.println("\nThe cipher text is : " + cipherText);
    }

    public void Decrypt() {

        Scanner scanner = new Scanner(System.in);
        System.out.println("\nDECRYPTION");
        System.out.print("\nEnter the cipher text : ");
        String cipherText = scanner.next();
        System.out.println("Enter the key matrix : ");
        int key[][] = new int[3][3];
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                key[i][j] = scanner.nextInt();

        if(!Invertible(key))
            return;

        int[][] inverseKey = new int[3][3];

        inverseKey = Inverse(key);

        int len = cipherText.length();
        String plainText = "";
        for(int i=0;i<len;) {
            int[] pair = new int[3];
            for(int j=0;j<3;j++) {
                if(i<len)
                    pair[j] = cipherText.charAt(i++) - 65;
                else pair[j] = 0;
            }

            pair = MatrixMultiply(pair,inverseKey);
            for(int j=0;j<3;j++) {
                if(pair[j] >=0)
                    plainText +=(char)(pair[j] + 65);
                else plainText += (char)(65 + pair[j] + 26);
            }
        }

        System.out.println("\nThe plain text is : " + plainText);

    }
}
