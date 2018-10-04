/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package md5;

import java.util.Scanner;
import java.util.Arrays;

public class MD5 {
    public static boolean[] long2bits(long input,int num_bits){
        boolean[] bits = new boolean[num_bits];
        for (int i = num_bits-1; i >= 0; i--) {
            bits[(num_bits-1)-i] = (input & (1 << i)) != 0;
        }
        return bits;
    }
    static int[]s = new int[] { 
        7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22,  7, 12, 17, 22, 
        5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,  5,  9, 14, 20,
        4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,  4, 11, 16, 23,
        6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,  6, 10, 15, 21,
        };

    public static boolean[] int2bits(int input,int num_bits){
        boolean[] bits = new boolean[num_bits];
        for (int i = num_bits-1; i >= 0; i--) {
            bits[(num_bits-1)-i] = (input & (1 << i)) != 0;
        }
        return bits;
    }
    public static boolean[] AND(boolean [] a, boolean [] b) {
        boolean[] bits = new boolean[a.length];
        for (int i =0;i<a.length;i++){
          bits[i] = a[i] & b[i];
        }
        return bits;
    }
    public static boolean[] OR(boolean [] a, boolean [] b) {
        boolean[] bits = new boolean[a.length];
        for (int i =0;i<a.length;i++){
          bits[i] = a[i] | b[i];
        }
        return bits;
    }
    public static boolean[] NOT(boolean [] a) {
        boolean[] bits = new boolean[a.length];
        for (int i =0;i<a.length;i++){
          bits[i] = !a[i];
        }
        return bits;
    }
    public static boolean[] XOR(boolean [] a, boolean [] b) {
        boolean[] bits = new boolean[a.length];
        for (int i =0;i<a.length;i++){
          bits[i] = a[i]^ b[i];
        }
        return bits;
    }
    public static boolean [] concat(boolean [] first, boolean [] second) {
        boolean[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
      }
    public static boolean[] binstr2bits(String s){
        String[] stringparts = s.split(" ");
        String reduced_s = String.join("", stringparts);
        int num_bits = reduced_s.length();
        boolean[] bits = new boolean[num_bits];
        for (int i = 0; i < num_bits; i++) {
            if (reduced_s.charAt(i) == '1'){
                bits[i] = true;
            }
            else{
                bits[i] = false;
            }
        }
        return bits;
    }
    public static boolean[] hex2bits(String hexString){
        int num_hexes = hexString.length();
        boolean[] bits = new boolean [num_hexes*4];
        boolean[] temp;
        for (int i = 0; i < num_hexes; i++) {
          temp = int2bits(Integer.parseInt(String.valueOf(hexString.charAt(i)),16), 4);
          for (int j=0;j<4;j++){
            bits[i*4+j] = temp[j];
          }
        }
        return bits;
    }
    public static long[] K_gen(){
        long[] K = new long[64];
        long Two32 = (long)(Math.pow(2, 32));
        for (int i=0;i<64;i++){
            // System.out.println((long) (Math.abs(Math.sin(i+1)*Two32)));
            K[i] = (long) Math.floor(Math.abs(Math.sin(i+1)*Two32));
        }         
        return K;
    }
    public static int bits2int(boolean bits[]){
        int n=0;
        int len = bits.length;
        for (int i = len-1; i >= 0; i--) {
            // System.out.println(bits[i]+","+Math.pow(2, (len-1)-i));
            if (bits[i]){
              n += Math.pow(2, (len-1)-i);
            }
        }
        return n;
    }

    public static String bits2hex(boolean bits[]){
        int num_hexes = bits.length/4;
        String hex_parts = "";
        for (int i = 0; i < num_hexes; i++) {
          int temp = bits2int(Arrays.copyOfRange(bits, i*4, (i+1)*4));
          hex_parts += Integer.toHexString(temp);
        }
          return hex_parts.toUpperCase();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter message");
        String message = scanner.nextLine();
        boolean[] bin_message = binstr2bits(message);
        int m_len = bin_message.length;
        int left_over = 448 - (m_len%512);
        System.out.println(left_over);
        if (left_over > 0){
            message += "1";
            for (int i=1;i<left_over;i++){
                message += "0";
            }
        }
        else{
            message += "1";
            for (int i=(m_len%512+1);i<512;i++)
                message += "0";
            for (int i=0;i<448;i++)
                message += "0";
        }
        boolean[] fin = concat(binstr2bits(message), int2bits(message.length(), 64));
        System.out.println("Length of final string :"+fin.length);
        String[] ABCD = new String[] {"67452301","efcdab89","98badcfe","10325476"};
        int[] ABCD_val = {0x67452301,0xefcdab89,0x98badcfe,0x10325476};
//        int[] ABCD_val = new int[4];
//        for (int i=0;i<ABCD.length;i++){
//            ABCD_val[i] = bits2int(hex2bits(ABCD[i]));
//        }
        System.out.println("BEFORE");
        System.out.println(ABCD_val[0]);
        System.out.println(ABCD_val[1]);
        System.out.println(ABCD_val[2]);
        System.out.println(ABCD_val[3]);
        System.out.println("AFTER");
        long[] K = K_gen();
        for (int outer =0;outer<fin.length/512;outer++){
            boolean[] M_block = Arrays.copyOfRange(fin, outer*512, (outer+1)*512);
            int A = ABCD_val[0];
            int B = ABCD_val[1];
            int C = ABCD_val[2];
            int D = ABCD_val[3];

            for (int i=0;i<64;i++){
                int F,g;
                if (i>=0 && i<=15){
                    F = (B & C) | (~B & D);
                    g = i;
                }
                else if (i>15 && i <=31){
                    F = (D & B) | (~D & B);
                    g = (5*i+1)%16;
                }
                else if (i>31 && i <=47){
                    F = B ^ C ^ D;
                    g = (3*i+5)%16;
                }
                else{
                    F = C ^ (B | ~D);
                    g = (7*i)%16;
                }
                int int_message = bits2int(Arrays.copyOfRange(M_block, g*32, (g+1)*32));
                F = (int) (F + A +K[i] + (int)K[i] + int_message);
                A = D;
                D = C;
                C = B;          
                B = B + Integer.rotateLeft(F, s[i]);
                System.out.println(A+" - "+ B + " - "+ C +" - "+ D);
            }
            ABCD_val[0]+=A;
            ABCD_val[1]+=B;
            ABCD_val[2]+=C;
            ABCD_val[3]+=D;      


        }
        System.out.println(ABCD_val[0]);
        System.out.println(ABCD_val[1]);
        System.out.println(ABCD_val[2]);
        System.out.println(ABCD_val[3]);
        String hash = bits2hex(int2bits(ABCD_val[0], 32))+bits2hex(int2bits(ABCD_val[1], 32))+bits2hex(int2bits(ABCD_val[2], 32))+bits2hex(int2bits(ABCD_val[3], 32));
        System.out.println("HASH: "+hash);
    }   
    
}
