import java.util.Arrays;
import java.util.Scanner;

class DHex{
  public static int modmul(int n,int exp, int mod){
    int result = n%mod;
    for (int i =1;i<exp;i++){
      result = (result*n)%mod;
    }
    return result;
  }

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter q: ");
    int q = scanner.nextInt();
    System.out.print("Enter alpha: ");
    int alpha = scanner.nextInt();
    // A & B agree upon q and alpha

    System.out.print("Enter Secret Key (A): ");
    int Xa = scanner.nextInt();
    System.out.print("Enter Secret Key (B): ");
    int Xb = scanner.nextInt();
    System.out.println("Xa = "+Xa+"\t\t Xb = "+Xb);
    // A & B randomly generate secret keys

    int Ya = modmul(alpha, Xa, q);
    int Yb = modmul(alpha, Xb, q);
    System.out.println("Ya = "+Ya+"\t\t Yb = "+Yb);
    // A and B generate public keys

    int Kb = modmul(Ya, Xb, q);
    int Ka = modmul(Yb, Xa, q);
    // A and B generate shared session key
    // using other's private key and its own secret key
    System.out.println("Ka = "+Ka+"\t Kb = "+Kb);
    scanner.close();
  }
}
