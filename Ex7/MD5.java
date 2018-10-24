import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Scanner;

public class md5 {

    private static int[] H = new int[4];
    private static final int[] SHIFT = {7, 12, 17, 22, 5, 9, 14, 20, 4, 11, 16, 23, 6, 10, 15, 21};
    private static final int[] K = new int[64];

    static {
        for (int i = 0; i < 64; i++)
            K[i] = (int) (long) ((1L << 32) * Math.abs(Math.sin(i + 1)));
    }

    public static void process(byte[] block) {
        int[] M = new int[16];

        //separating 32 bits
        for (int i = 0; i < 16; i++) {
            byte[] temp = new byte[4];
            System.arraycopy(block, 4 * i, temp, 0, 4);
            int start = 0, end = 3;
            while (start < end) {
                byte t = temp[start];
                temp[start] = temp[end];
                temp[end] = t;
                start++;
                end--;
            }
            //from 32 bits
            M[i] = ByteBuffer.wrap(temp).getInt();
            System.out.println(Integer.toUnsignedString(M[i]));
        }

        int a = H[0], b = H[1], c = H[2], d = H[3];
        int i;
        for (i = 0; i < 64; i++) {
            int f, g;
            if (i < 16) {
                f = (b & c) | (~b & d);
                g = i;
            } else if (i < 32) {
                f = (d & b) | (~d & c);
                g = (5 * i + 1) % 16;
            } else if (i < 48) {
                f = b ^ c ^ d;
                g = (3 * i + 5) % 16;
            } else {
                f = c ^ (b | ~d);
                g = (7 * i) % 16;
            }
            f = f + a + K[i] + M[g];
            a = d;
            d = c;
            c = b;
            b = b + Integer.rotateLeft(f, SHIFT[4 * (i / 16) + (i % 4)]);

            System.out.print("Round " + (i + 1) + ": ");
            printBuffer(a,b,c,d);
        }
        H[0] += a;
        H[1] += b;
        H[2] += c;
        H[3] += d;
        System.out.print("HASH : ");
        printBuffer(H);
    }

    public static void getMD5(byte[] m) {
        H[0] = 0x67452301;
        H[1] = 0xefcdab89;
        H[2] = 0x98badcfe;
        H[3] = 0x10325476;
        int byte_length = m.length;
        int bits_length = byte_length * 8;
        int append_bits = 448 - (bits_length % 512);
        if (append_bits < 0)
            append_bits += 512;
        if (append_bits == 0)
            append_bits = 512;
        int append_bytes = append_bits / 8;
        byte[] pad = new byte[append_bytes];

        pad[0] = (byte) 0x80;
        for (int i = 1; i < append_bytes; i++)
            pad[i] = 0x00;
        byte[] l_pad = new byte[8];
        for (int i = 0; i < 8; i++) {
            l_pad[i] = (byte) bits_length;
            bits_length = bits_length / 256;
        }
        byte[] padded_m = new byte[byte_length + append_bytes + 8];
        System.arraycopy(m, 0, padded_m, 0, m.length);
        System.arraycopy(pad, 0, padded_m, m.length, pad.length);
        System.arraycopy(l_pad, 0, padded_m, m.length + pad.length, 8);

        System.out.println("The padded message is ");

        for (int i = 0; i < 64; i++) {
            System.out.print(padded_m[i] + " ");
        }
        System.out.println();

        int no = padded_m.length / 64;

        for (int i = 0; i < no; i++) {
            byte[] block = new byte[64];
            System.arraycopy(padded_m, i * 64, block, 0, 64);
            process(block);
        }

    }

    public static void main(String args[]) {
        String hash = "";
        String text = "";
        System.out.println("Enter the text to hash :");
        Scanner sc = new Scanner(System.in);
        text = sc.nextLine();
        System.out.println("The hash is ");
        getMD5(text.getBytes());
    }

    private static void printBuffer(int[] H) {
        for (int i = 0; i < 4; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            byteBuffer.putInt(H[i]);
            int littleEndian = ByteBuffer.wrap(byteBuffer.array()).getInt();
            System.out.print(Integer.toHexString(littleEndian));
        }
        System.out.println();
    }

    private static void printBuffer(int a,int b,int c,int d) {
        for (int i = 0; i < 4; i++) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(4);
            byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
            if(i==0)
            byteBuffer.putInt(a);
            if(i==1)
            byteBuffer.putInt(b);
            if(i==2)
            byteBuffer.putInt(c);
            if(i==3)
            byteBuffer.putInt(d);
            int littleEndian = ByteBuffer.wrap(byteBuffer.array()).getInt();
            System.out.print(Integer.toHexString(littleEndian));
        }
        System.out.println();
    }
}


