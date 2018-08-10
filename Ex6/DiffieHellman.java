import java.util.Arrays;
import java.util.Random;;
import java.util.Scanner;

class DiffieHellman{
    
  public static int modmul(int n,int exp, int mod){
    int result = n%mod;
    for (int i =1;i<exp;i++){
      result = (result*n)%mod;
    }
    return result;
  }
 
    // Utility function to do modular exponentiation. It returns (x^y) % p
    static int power(int x, int y, int p) {
         
        int res = 1; // Initialize result
         
        //Update x if it is more than or equal to p
        x = x % p; 
 
        while (y > 0) {
             
            // If y is odd, multiply x with result
            if ((y & 1) == 1)
                res = (res * x) % p;
         
            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }
         
        return res;
    }
     
    /* This function is called for all k trials. It returns false if n is composite and returns false if n is probably prime. 
       d is an odd number such that d*2<sup>r</sup> = n-1 for some r >= 1 */
    static boolean miillerTest(int d, int n) {
         
        // Pick a random number in [2..n-2] Corner cases make sure that n > 4
        int a = 2 + (int)(Math.random() % (n - 4));
     
        // Compute a^d % n
        int x = power(a, d, n);
     
        if (x == 1 || x == n - 1)
            return true;
     
        /* Keep squaring x while one of the
         following doesn't happen
         (i) d does not reach n-1
         (ii) (x^2) % n is not 1
         (iii) (x^2) % n is not n-1
        */
        while (d != n - 1) {
            x = (x * x) % n;
            d *= 2;
         
            if (x == 1)
                return false;
            if (x == n - 1)
                return true;
        }
     
        // Return composite
        return false;
    }
     
    /* It returns false if n is composite and returns true if n is probably prime. k is an input parameter that 
     determines accuracy level. Higher value of k indicates more accuracy.
    */
    static boolean isPrime(int n, int k) {
         
        // Corner cases
        if (n <= 1 || n == 4)
            return false;
        if (n <= 3)
            return true;
     
        // Find r such that n = 2^d * r + 1 for some r >= 1
        int d = n - 1;
         
        while (d % 2 == 0)
            d /= 2;
     
        // Iterate given number of 'k' times
        for (int i = 0; i < k; i++)
            if (miillerTest(d, n) == false)
                return false;
     
        return true;
    }
    
  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter q : ");
    int q = scanner.nextInt();
    //Check the primality of 'q' usig Rabin-Miller.
    /* k denotes the number of random values to test for. */
    boolean val = isPrime(q,10);
    if (!val){
        System.err.println("Not a prime !!");
        System.exit(0);
    }
    else{
        System.out.println("q is a prime !!");
    }
    System.out.print("Enter alpha : ");
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