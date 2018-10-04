import java.util.*;

public class SHA {
    
    public static String message;
    public static StringBuilder answer=new StringBuilder();
    public static final int INIT_A=(int) 0x67452301;
    public static final int INIT_B=(int) 0xEFCDAB89;
    public static final int INIT_C=(int) 0x98BADCFE;
    public static final int INIT_D=(int) 0x10325476;
    public static final int INIT_E=(int) 0xC3D2E1F0;
    
    private static int rol(int num, int cnt) {
        return (num << cnt) | (num >>> (32 - cnt));
    }
    
    public static String SHA(byte[] message)
    {
        int i,j;
        int[] blks = new int[(((message.length + 8) >> 6) + 1) * 16];
       

        for(i = 0; i < message.length; i++) {
            blks[i >> 2] |= message[i] << (24 - (i % 4) * 8);
        }

        blks[i >> 2] |= 0x80 << (24 - (i % 4) * 8);
        blks[blks.length - 1] = message.length * 8;
        int a=INIT_A;
        int b=INIT_B;
        int c=INIT_C;
        int d=INIT_D;
        int e=INIT_E;
        for( j=0;j<blks.length;j+=16)
        {
           
            int originalA = a;
            int originalB = b;
            int originalC = c;
            int originalD = d;
            int originalE = e;
            int k = 0;
            int[] w = new int[80];
            for( i=0;i<80;i++)
            {
                w[i] = (i < 16) ? blks[i + j] :
                       ( rol(w[i-3] ^ w[i-8] ^ w[i-14] ^ w[i-16], 1) );
                int f=0;
                if(i>=0 && i<=19)
                {
                    f=(b & c)|((~ b)& d);
                    k=(int) 0x5A827999;
                    
                }
                else if(i>=20 && i<=39)
                {
                    f=b^c^d;
                    k=(int) 0x6ED9EBA1;
                }
                else if(i>=40 && i<=59)
                {
                    f=(b&c)|(b&d)|(c&d);
                    k=(int) 0x8F1BBCDC;
                }
                else if(i>=60 && i<=79)
                {
                    f=b^c^d;
                    k=(int) 0xCA62C1D6;
                }
                int temp=(rol(a,5))+f+e+k+w[i];
                e=d;
                d=c;
                c=rol(b,30);
                b=a;
                a=temp;
            }
            a+= originalA;
            b+= originalB;
            c+= originalC;
            d+= originalD;
            e+= originalE;
        }

        byte[] sha1 = new byte[25];
        int count = 0;
        for ( i = 0; i < 5; i++)
        {
            int n = (i == 0) ? a : ((i == 1) ? b : ((i == 2) ? c : ((i == 3) ? d : e)));
            answer.append(Integer.toHexString(n));
            for ( j = 0; j < 5; j++)
            {               
                sha1[count++] = (byte) n;
                n >>>= 16;
            }
        }
        return answer.toString();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        Scanner s = new Scanner(System.in).useDelimiter("\n");
        System.out.println("Enter the message:");
        message = s.next();
        String sha1 = SHA(message.getBytes());
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<40-sha1.length(); i++)
            sb.append("0");
        System.out.println("SHA Hash: " + sb.toString() + sha1);
    }
}
