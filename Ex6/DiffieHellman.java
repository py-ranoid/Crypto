import java.math.BigInteger;
import java.util.*;
import java.security.SecureRandom;

public class DiffieHellman {
    public static boolean isPrime(BigInteger b) {
        int iterations=-1;
        if ((b.intValue()==0)||(b.intValue()==1))
            return false;
            /** base case - 2 is prime **/
        if (b.intValue()==2)
            return true;
        /** an even number other than 2 is composite **/
        if (b.mod(BigInteger.valueOf(2)).intValue()==0)
            return false;
        BigInteger s = null;
         s=b.subtract(BigInteger.valueOf(1));
        while (s.mod(BigInteger.valueOf(2)).intValue()==0){
            s=s.divide(BigInteger.valueOf(2));
            iterations++;
        }
        SecureRandom rand = new SecureRandom();
        for (int i = 0; i <=iterations; i++){
            BigInteger r = new BigInteger(14,rand);            
            BigInteger a = (r.mod(b.subtract(BigInteger.valueOf(1)))).add(BigInteger.valueOf(1));
            BigInteger temp = s;
            BigInteger mod = a.modPow(temp, b);
            while (temp.intValue() != b.intValue()-1 && mod.intValue() != 1 && mod.intValue() != b.intValue()-1){
                mod = mulMod(mod, mod, b);
                temp=temp.multiply(BigInteger.valueOf(2));
            }
            if (mod.intValue() != b.intValue()-1 && (temp.mod(BigInteger.valueOf(2))).intValue() == 0)
                return false;
        }
        return true;
    }

    public static BigInteger sqrt(BigInteger n) {
        BigInteger a = BigInteger.ONE;
        BigInteger b = new BigInteger(n.shiftRight(5).add(new BigInteger("8")).toString());
        while(b.compareTo(a) >= 0) {
          BigInteger mid = new BigInteger(a.add(b).shiftRight(1).toString());
          if(mid.multiply(mid).compareTo(n) > 0) b = mid.subtract(BigInteger.ONE);
          else a = mid.add(BigInteger.ONE);
        }
        return a.subtract(BigInteger.ONE);
      }
    public static void findPrimefactors( ArrayList<BigInteger> s, BigInteger n)
    {
        while (n.mod(BigInteger.valueOf(2)) .intValue()== 0){
            s.add(BigInteger.valueOf(2));
            n = n.divide(BigInteger.valueOf(2));
        }
        BigInteger i=new BigInteger("3");
        for ( ; i.compareTo(sqrt(n))==-1||i.compareTo(sqrt(n))==0; i = i.add(BigInteger.valueOf(2))){
            while (n.mod(i).intValue() == 0){
                s.add(i);
                n = n.divide(i);
            }
        }

        if (n.compareTo(BigInteger.valueOf(2))==1)
            s.add(n);
    }
    
    public static BigInteger findPrimitive(BigInteger n) {
        ArrayList<BigInteger> s=new ArrayList<BigInteger>();
        if (isPrime(n)==false)
            return BigInteger.valueOf(-1);
        BigInteger phi = n.subtract(BigInteger.valueOf(1));
        findPrimefactors(s, phi);
        SecureRandom rand=new SecureRandom();
        BigInteger r=new BigInteger(8,rand);
        for (; r.compareTo(phi)==0||r.compareTo(phi)==-1; r=r.add(BigInteger.valueOf(1))){
            boolean flag = false;
            for (int i = 0; i<s.size(); i++){
                if(r.modPow(phi.divide(s.get(i)), n).intValue()==1){
                    flag = true;
                    break;
                }
             }

             if (flag == false)
               return r;
        }

        return BigInteger.valueOf(-1);
    }
    
    public static BigInteger mulMod(BigInteger a, BigInteger b, BigInteger mod) {
         return a.multiply(b).mod(mod);
    }

    public static void main(String[] args) {
        BigInteger p_big;
        SecureRandom random=new SecureRandom();
        int flag=0;
        do{
            p_big=new BigInteger(15,random);
            if(isPrime(p_big)){
                flag=1;
                break;
            }     
        }while(flag!=1);
        BigInteger r=findPrimitive(p_big);
        SecureRandom rand = new SecureRandom();
        System.out.println("Prime p: "+p_big);
        System.out.println("Random primitive root: "+r);
        BigInteger  xa=new BigInteger(15,rand);
        BigInteger  ya=r.modPow(xa, p_big);
        BigInteger  xb=new BigInteger(15,rand);
        BigInteger  yb=r.modPow(xb, p_big);
        System.out.println("Xa: "+xa+" Xb: "+xb);
        System.out.println("Ya: "+ya+" Yb: "+yb);
        BigInteger  ka=yb.modPow(xa, p_big);
        BigInteger  kb=ya.modPow(xb, p_big);
        System.out.println("Kab: " +ka+" Kab: "+kb);
    }
}