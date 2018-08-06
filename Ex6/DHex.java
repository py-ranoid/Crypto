import java.util.Arrays;
import java.util.Random;;
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

    Random r = new Random();
    int Xa = r.nextInt(q);
    int Xb = r.nextInt(q);
    System.out.println("Xa = "+Xa+"  Xb = "+Xb);
    // A & B randomly generate secret keys

    int Ya = modmul(alpha, Xa, q);
    int Yb = modmul(alpha, Xb, q);
    System.out.println("Ya = "+Ya+"  Yb = "+Yb);
    // A and B generate public keys and share them with each other

    int Kb = modmul(Ya, Xb, q);
    int Ka = modmul(Yb, Xa, q);
    // A and B generate shared session key
    // using other's private key and its own secret key
    System.out.println("Ka = "+Ka+"  Kb = "+Kb);
    scanner.close();
  }
}
