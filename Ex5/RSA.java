import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;

public class RSA {
    
    public static BigInteger[] get_pq(int start){
        BigInteger p = new BigInteger(1024, 99, new Random());
        BigInteger q = new BigInteger(1024, 99, new Random());
        System.out.println("p: " + p);
        System.out.println("q: " + q);
        BigInteger[] arr = {p,q};
        return arr;
    }
    
    public static void main(String[] args) {
        
        BigInteger p, q;
        BigInteger[] arr = get_pq(123);
        p = arr[0];
        q = arr[1];
        
        BigInteger n = p.multiply(q);
        BigInteger p1 = p.subtract(BigInteger.ONE);
        BigInteger q1 = q.subtract(BigInteger.ONE);
        BigInteger euler_fn = p1.multiply(q1);
        
        BigInteger e = new BigInteger(1024, 99, new Random());
        for( ; e.compareTo(n)==-1; e=e.add(BigInteger.ONE)){
            if(e.gcd(euler_fn).compareTo(BigInteger.ONE) == 0)
                break;
        }

        BigInteger d = e.modInverse(euler_fn);
        
        System.out.println("Enter a message (less than " + n + "): ");
        Scanner scan = new Scanner(System.in);
        BigInteger m = new BigInteger(scan.nextLine());
        System.out.print("Public key is ");
        System.out.println(e);
        System.out.print("Private key is ");
        System.out.println(d);
        
        BigInteger c = m.modPow(e, n);
        System.out.print("Encrypted message is ");
        System.out.println(c);
        BigInteger me = c.modPow(d, n);
        System.out.print("Decrypted message is ");
        System.out.println(me);
    }   
}