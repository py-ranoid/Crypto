import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
class PlayFair{
	public static void main(String[] args){
		Scanner s=new Scanner(System.in);		
		int k=0, keylen = 0, i = 0, j = 0;
		char ch;
		String key;
		char[][] mat=new char[5][5];
		System.out.println("HIII");
		System.out.println("Enter key: ");
		key = s.nextLine();	
		keylen = key.length();			
		Map<Character, Integer> map = new HashMap<>();
		i = j = k = 0;
		for(k = 0; k < keylen; k ++){
				ch = key.charAt(k);
				if(!map.containsKey(ch)){
					map.put(ch, 1);
					mat[i][j++]=ch;
					if(j==5)
					{
						j=0;
						i++;
					}
				}
		}
		int newi = i, newj = j;
		ch = 'A';
		for(ch = 'A'; ch <= 'Z'; ch ++){			
				if(!map.containsKey(ch)){
					if(ch == 'I' || ch == 'J')
						if(map.containsKey('I') || map.containsKey('J'))
							continue;
					map.put(ch, 1);

					if(newj == 5){
						if(newi == 4)
							break;
						newi ++;
						newj = 0;
					}
					mat[newi][newj++]=ch;					
				}			
		}
		for(i=0;i<5;i++){
			for(j=0;j<5;j++){
				System.out.print(mat[i][j] + " ");
			}
			System.out.println();
		}

		System.out.println("Enter message to encrypt: ");
		String message = s.nextLine(), cipherText = "";
		int msgLen = message.length(), row1, col1, row2, col2, row, col;
		char ch1, ch2;
		boolean flag1, flag2;
		for(i = 0; i < msgLen; i ++){
			ch1 = message.charAt(i++);
			if(i < msgLen)
				ch2 = message.charAt(i);
			else
				ch2 = 'X';
			if(ch1 == ch2 || (ch1 == 'I' && ch2 == 'J') || (ch1 == 'J' && ch2 == 'I')){
				ch2 = 'X';
				i--;
			}
			flag1 = flag2 = false;
			row1 = col1 = row2 = col2 = -1;
			for(row = 0; row < 5; row ++){
				for(col = 0; col < 5; col ++){
					if(flag1 && flag2)
						break;
					if(mat[row][col] == ch1 || (ch1 == 'I' && mat[row][col] == 'J') || (ch1 == 'J' && mat[row][col] == 'I')){
						row1 = row;
						col1 = col;
						flag1 = true;
					}
					else if(mat[row][col] == ch2 || (ch2 == 'I' && mat[row][col] == 'J') || (ch2 == 'J' && mat[row][col] == 'I')){
						row2 = row;
						col2 = col;
						flag2 = true;
					}
				}
				if(flag1 && flag2)
					break;
			}

			if(row1 == row2){
				cipherText += (char)mat[row1][(col1+1)%5] +""+ (char)mat[row2][(col2+1)%5];
			}
			else if(col1 == col2){
				cipherText += (char)mat[(row1+1)%5][col1] +""+ (char)mat[(row2+1)%5][col2];
			}
			else{
				cipherText += (char)mat[row1][col2] + ""+(char)mat[row2][col1];
			}
		}
		System.out.println("cipherText = "+cipherText);

		int cipherLen = cipherText.length();
		String decipheredText = "";
		for(i = 0; i < cipherLen; i ++){
			ch1 = cipherText.charAt(i++);
			ch2 = cipherText.charAt(i);
			flag1 = flag2 = false;
			row1 = col1 = row2 = col2 = -1;
			for(row = 0; row < 5; row ++){
				for(col = 0; col < 5; col ++){
					if(flag1 && flag2)
						break;
					if(mat[row][col] == ch1 || (ch1 == 'I' && mat[row][col] == 'J') || (ch1 == 'J' && mat[row][col] == 'I')){
						row1 = row;
						col1 = col;
						flag1 = true;
					}
					else if(mat[row][col] == ch2 || (ch2 == 'I' && mat[row][col] == 'J') || (ch2 == 'J' && mat[row][col] == 'I')){
						row2 = row;
						col2 = col;
						flag2 = true;
					}
				}
				if(flag1 && flag2)
					break;
			}

			if(row1 == row2){
				if(col1 - 1 < 0)
					col1 = 5;
				if(col2 - 1 < 0)
					col2 = 5;

				decipheredText += (char)mat[row1][(col1-1)] +""+ (char)mat[row2][(col2-1)];
			}
			else if(col1 == col2){
				if(row1 - 1 < 0)
					row1 = 5;
				if(row2 - 1 < 0)
					row2 = 5;
				decipheredText += (char)mat[(row1-1)][col1] +""+ (char)mat[(row2-1)][col2];
			}
			else{
				decipheredText += (char)mat[row1][col2] + ""+(char)mat[row2][col1];
			}
		}
		System.out.println("decipheredText = "+decipheredText);
	}
}
