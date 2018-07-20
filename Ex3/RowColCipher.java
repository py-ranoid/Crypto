import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class RowColCipher{
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		int n = 0, i = 0, j = 0, k = 0, inputLen = 0;
		String inputMsg = "";
		char ch = ' ';
		System.out.println("Enter the message: ");
		inputMsg = sc.nextLine();
		System.out.println("Enter the number of columns: ");
		n = sc.nextInt();
		inputLen = inputMsg.length();

		/**********************************************ENCRYPTION******************************************/
		char Matrix[][] = new char[100][n];
		i = j = k = 0;
		for(i = 0; k < inputLen; i ++){
			for(j = 0; j < n; j ++){
				while(k < inputLen && (ch = inputMsg.charAt(k)) == ' ')
					k ++;
				if(k < inputLen){
					Matrix[i][j] = ch;
					System.out.print(Matrix[i][j] + " ");
				}
				else{
					while(j < n){
						Matrix[i][j++] = 'x';
						System.out.print(Matrix[i][j-1] + " ");
					}
				}
				k++;				
			}
			System.out.println();
		}

		int m = i;

		Map<Integer, Integer> keyMap = new HashMap<>();
		String cipherText = "";
		int temp, key[] = new int[n];
		System.out.println("Enter key: ");
		for(i = 0; i < n; i ++){
			temp = sc.nextInt();
			key[i] = temp-1;
			keyMap.put(temp-1, i);
		}
		for(i = 0; i < n; i ++){
			for(j = 0; j < m; j ++){
				cipherText += Matrix[j][keyMap.get(i)];
			}
		}
		System.out.println("Cipher Text = "+cipherText);

		/******************************************DECRYPTION***************************************************/
		char decipherMatrix[][] = new char[n][m];
		k = 0;
		for(i = 0; i < n; i ++){
			for(j = 0; j < m; j ++){
				decipherMatrix[i][j] = cipherText.charAt(k++);
				System.out.print(decipherMatrix[i][j] + " ");
			}
			System.out.println();
		}

		String decipheredText = "";
		int row = 0, col = 0;
		for(col = 0; col < m; col ++){
			for(row = 0; row < n; row ++){
				decipheredText += decipherMatrix[key[row]][col];
			}
		}

		System.out.println("decipheredText = "+decipheredText);
	}
}