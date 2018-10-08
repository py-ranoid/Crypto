import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class RowColCipher{

	public static char[][] gen_enc_matrix(String message, int n,int m){
		char Matrix[][] = new char[m][n];
		int char_iter=0;
		for (int i=0;i<m;i++){
			for (int j = 0; j < n; j ++){
				if (char_iter < message.length())
					Matrix[i][j]=message.charAt(char_iter);
				else
					Matrix[i][j]='x';
				char_iter++;
			} 
		}		
		return Matrix;
	}
	public static char[][] gen_dec_matrix(String cipherText, int n,int m){
		char decipherMatrix[][] = new char[n][m];
		int i,j,k = 0;
		for(i = 0; i < n; i ++)
			for(j = 0; j < m; j ++)
				decipherMatrix[i][j] = cipherText.charAt(k++);
		return decipherMatrix;
	}
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = 0, i = 0, j = 0, k = 0;
		String message = "";
		char ch = ' ';
		
		System.out.println("Enter the message: ");
		message = sc.nextLine();
		System.out.println("Enter the number of columns: ");
		n = sc.nextInt();
		
		message = message.replace(" ", "");
		int m = (int) Math.ceil((float) message.length()/n);
		
		// Generate matrix with n columns and given message 
		char Matrix[][] = gen_enc_matrix(message,n,m);
		
		// Get key to transpose columns
		Map<Integer, Integer> keyMap = new HashMap<>();
		String cipherText = "";
		int temp, key[] = new int[n];
		System.out.println("Enter key: ");
		for(i = 0; i < n; i ++){
			temp = sc.nextInt();
			key[i] = temp-1;
			keyMap.put(temp-1, i);
		}
		
		// Encrypt message by transposing columns
		for(i = 0; i < n; i ++){
			for(j = 0; j < m; j ++){
				cipherText += Matrix[j][keyMap.get(i)];
			}
		}
		System.out.println("Encrypted : "+cipherText);

		// Reconstruct matrix from encrypted message
		m = cipherText.length()/n;
		char[][] decipherMatrix = gen_dec_matrix(cipherText, n, m);
		
		// Decrypt message by ordering columns by key
		String decipheredText = "";
		int row = 0, col = 0;
		for(col = 0; col < m; col ++){
			for(row = 0; row < n; row ++){
				decipheredText += decipherMatrix[key[row]][col];
			}
		}
		System.out.println("Decrypted : "+decipheredText);
	}
}