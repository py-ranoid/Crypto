// Refer http://practicalcryptography.com/ciphers/playfair-cipher/
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class PlayFair{
	
	public static Map<Character, Integer> mat_chars = new HashMap<>();
	public static char[][] mat=new char[5][5];
		
	public static char[][] gen_key_sq(String key){
		char ch;		
		// Filling key square with letters from key
		int char_iter = 0;
		for(int k = 0; k < key.length(); k ++){
				ch = key.charAt(k);
				if(!mat_chars.containsKey(ch)){
					mat_chars.put(ch, char_iter);
					mat[char_iter/5][char_iter%5]=ch;
					char_iter++;
				}
		}
		for(ch = 'A'; ch <= 'Z'; ch ++){			
				if(!mat_chars.containsKey(ch)){
					// If alphabet is not present in key square
					if(ch == 'I' || ch == 'J')
						// If alphabet is I or J, having either of them is sufficient
						if(mat_chars.containsKey('I') || mat_chars.containsKey('J'))
							continue;
					
					// Add character to matrix
					mat_chars.put(ch, char_iter);
					mat[char_iter/5][char_iter%5]=ch;
					char_iter++;		
				}			
		}
		return mat;
	}

	public static void print_key_sq(char[][] mat){
		for(int i=0;i<5;i++){
			for(int j=0;j<5;j++){
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}
	}

	public static String encrypt(String message){
		char ch1,ch2;
		int row1,row2,col1,col2;
		String cipherText="";
		for(int i = 0; i < message.length(); i+=2){
			ch1 = message.charAt(i);
			if(i == message.length()-1)
				ch2 = 'X';
			else
				ch2 = message.charAt(i+1);
			// Set second charater to X if they are the same or if either chars is I/J
			if(ch1 == ch2 || (ch1 == 'I' && ch2 == 'J') || (ch1 == 'J' && ch2 == 'I')){
				ch2 = 'X';
			}
			row1 = mat_chars.get(ch1)/5;	col1 = mat_chars.get(ch1)%5;
			row2 = mat_chars.get(ch2)/5;	col2 = mat_chars.get(ch2)%5;
			
			if(row1 == row2){
				// If the 2 characters are in the same row of the key square matrix
				cipherText += (char)mat[row1][(col1+1)%5] +""+ (char)mat[row2][(col2+1)%5];
			}
			else if(col1 == col2){
				// If the 2 characters are in the same column of the key square matrix
				cipherText += (char)mat[(row1+1)%5][col1] +""+ (char)mat[(row2+1)%5][col2];
			}
			else{
				// If the 2 characters are in the different rows and columns
				cipherText += (char)mat[row1][col2] + ""+(char)mat[row2][col1];
			}
		}
		return cipherText;
	}

	public static String decrypt(String cipherText){
		char ch1,ch2;
		int row1,row2,col1,col2;
		String decipheredText = "";
		for(int i = 0; i < cipherText.length(); i +=2){
			ch1 = cipherText.charAt(i);
			ch2 = cipherText.charAt(i+1);
			row1 = mat_chars.get(ch1)/5;	col1 = mat_chars.get(ch1)%5;
			row2 = mat_chars.get(ch2)/5;	col2 = mat_chars.get(ch2)%5;
			// Reverse the rules followed for encryption
			if(row1 == row2){
				// Adding 5 so that -1 becomes 4. 4%5 = 4
				col1 +=5;	col2 += 5;
				decipheredText += (char)mat[row1][(col1-1)%5] +""+ (char)mat[row2][(col2-1)%5];
			}
			else if(col1 == col2){
				// Adding 5 so that -1 becomes 4. 4%5 = 4
				row1 +=5;	row2 += 5;
				decipheredText += (char)mat[(row1-1)%5][col1] +""+ (char)mat[(row2-1)%5][col2];
			}
			else{
				decipheredText += (char)mat[row1][col2] + ""+(char)mat[row2][col1];
			}
		}
		return decipheredText;
	}

	public static void main(String[] args){
		Scanner s=new Scanner(System.in);		
		int i = 0, j = 0;
		char ch;
		String key;		
		System.out.println("Enter key: ");
		key = s.nextLine().toUpperCase();	
		
		char[][] mat = gen_key_sq(key);
		print_key_sq(mat);

		System.out.println("Enter message to encrypt: ");
		String message = s.nextLine().toUpperCase();
		
		String cipherText = encrypt(message);
		System.out.println("Encrypted : "+cipherText);

		String decipherText = decrypt(cipherText);
		System.out.println("Decrypted : "+decipherText);

		s.close();
	}
}
