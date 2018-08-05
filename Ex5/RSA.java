import java.util.Arrays;
import java.util.Scanner;

class RSA{
  public static int GCD(int a, int b) {
    if(b == 0)
        return a;
    return GCD(b, a%b);
  }

  public static int[][] getKeyNums(int p, int q){
    int n = p * q;
    int phi_n = (p-1)*(q-1);
    int e,d;
    for (e =5;e<phi_n;e++){
      if (GCD(e, phi_n)==1){
        break;
      }
    }
    for (d =3;d<phi_n;d++)
      if (e*d%phi_n==1){
        break;
      }
    return new int[][] {{e,n},{d,n}};
  }
  public static double encrypt(int[] key,int m){
    int step=5;
    int quo = key[0]/step;
    int rem = key[0]%step;
    double quo_part = Math.pow(Math.pow(m, step)%key[1],quo);
    double rem_part = Math.pow(m, rem)%key[1];
    return (int)((quo_part*rem_part)%key[1]);
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter p: ");
    int p = scanner.nextInt();
    System.out.print("Enter q: ");
    int q = scanner.nextInt();
    System.out.print("Enter message (number less than "+p*q+"): ");
    int msg = scanner.nextInt();
    int keys[][] = getKeyNums(p,q);
    int[] pub_key = keys[0];
    int[] pri_key = keys[1];
    System.out.println("\nPublic Key  :"+Arrays.toString(pub_key));
    System.out.println("Private Key :"+Arrays.toString(pri_key));

    int enc = (int)encrypt(pub_key, msg);
    System.out.println("\nEncrypted: "+enc);

    int dec = (int)encrypt(pri_key, enc);
    System.out.println("Decrypted: "+ dec);

    scanner.close();
  }
}
