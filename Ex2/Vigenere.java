import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class Vigenere{
	public static char findCharacter(char array[], char ch){
		int i = 0;
		for(i = 0; i < 26; i ++){
			if(array[i] == ch)
				return (char)(65 + i);
		}
		return 0;
	}
	public static void main(String[] args){
		String input, key;
		Scanner sc = new Scanner(System.in);
		int inputLen = 0, i = 0, j = 0, keyLen = 0, k = 0;
		System.out.println("Enter input message: ");
		input = sc.nextLine();
		inputLen = input.length();
		
		System.out.println("Enter key: ");
		key = sc.nextLine();
		keyLen = key.length();
		
		k = keyLen; i = 0;
		while(k < inputLen){
			key += key.charAt(i%keyLen);
			i++;
			k++;
		}
		System.out.println("Key repeated to form: "+key);
		char VigenereMatrix[][] = new char[26][26];
		for(i = 0; i < 26; i ++){
			for(j = 0; j < 26; j ++){
				VigenereMatrix[i][j] = (char)(65 + ((i+j)%26));
				System.out.print(VigenereMatrix[i][j] + " " );
			}
			System.out.println();
		}

		String encryptedMessage = "";
		for(i = 0; i < inputLen; i ++){
			encryptedMessage += VigenereMatrix[input.charAt(i) -'A'][key.charAt(i) - 'A'];
		}
		System.out.println("Encrypted Message = "+encryptedMessage);

		String decryptedMessage = "";
		for(i = 0; i < inputLen; i ++){
			decryptedMessage += findCharacter(VigenereMatrix[key.charAt(i) - 'A'], encryptedMessage.charAt(i));
		}
		System.out.println("Decrypted Message = "+decryptedMessage);


	}
}