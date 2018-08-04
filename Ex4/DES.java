import java.util.Arrays;

class DES {

    public static boolean[] int2bits(int input,int num_bits){
        boolean[] bits = new boolean[num_bits];
        for (int i = num_bits-1; i >= 0; i--) {
            bits[(num_bits-1)-i] = (input & (1 << i)) != 0;
        }
        return bits;
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

    public static boolean[] bits2str(boolean[] bits){
        System.out.println(bits.length);
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]){
                System.out.print("1");
            }
            else{
                System.out.print("0");
            }
        }
        System.out.println();
        return bits;
    }

    public static boolean[] permute(boolean[] in_bits,int[][] P) {
        int P_len = P.length*P[0].length;
        boolean[] out_bits = new boolean[P_len];
        int counter = 0;
        int index;
        for (int i = 0;i<P.length;i++){
            for(int j = 0;j<P[0].length;j++){
                index = P[i][j]-1;
                out_bits[counter++] = in_bits[index];
            }
        }
        return out_bits;
    }

    public static boolean[] leftShift(boolean[] in_bits,int n) {
        int index;
        int in_length = in_bits.length;
        boolean[] out_bits = new boolean[in_length];
        for (int i = 0;i<in_length;i++){
            index = (i+n)%in_length;
            out_bits[i] = in_bits[index];
        }
        return out_bits;
    }

    public static boolean [] concat(boolean [] first, boolean [] second) {
      boolean[] result = Arrays.copyOf(first, first.length + second.length);
      System.arraycopy(second, 0, result, first.length, second.length);
      return result;
    }

    public static boolean[][] keyGen(String init_key) {
      int[][] PC1 = new int[][] {
          {57,49,41,33,25,17,9},
          {1,58,50,42,34,26,18},
          {10,2,59,51,43,35,27},
          {19,11,3,60,52,44,36},
          {63,55,47,39,31,23,15},
          {7,62,54,46,38,30,22},
          {14,6,61,53,45,37,29},
          {21,13,5,28,20,12,4}
      };
      int[][] PC2 = new int[][] {
          {14,17,11,24,1,5},
          {3,28,15,6,21,10},
          {23,19,12,4,26,8},
          {16,7,27,20,13,2},
          {41,52,31,37,47,55},
          {30,40,51,45,33,48},
          {44,49,39,56,34,53},
          {46,42,50,36,29,32}
      };
      boolean[] K = binstr2bits(init_key);
      boolean[] K_plus = permute(K, PC1);
      boolean[][] C = new boolean[17][28];
      boolean[][] D = new boolean[17][28];
      boolean[][] Keys = new boolean[16][48];

      C[0] = Arrays.copyOfRange(K_plus, 0, 28);
      D[0] = Arrays.copyOfRange(K_plus, 28, K_plus.length);;

      int[] shiftNums = {1,1,2,2,2,2,2,2,1,2,2,2,2,2,2,1};
      for (int i=1;i<17;i++){
          C[i] = leftShift(C[i-1], shiftNums[i-1]);
          D[i] = leftShift(D[i-1], shiftNums[i-1]);
          Keys[i-1] = permute(concat(C[i], D[i]), PC2);
      }
      return Keys;
    }
    public static boolean[] arrayXOR(boolean [] a, boolean [] b) {
      boolean[] bits = new boolean[a.length];
      for (int i =0;i<a.length;i++){
        bits[i] = a[i]^ b[i];
      }
      return bits;
    }
    public static boolean[] feistel(boolean[] R, boolean[] K){

      int[][] E = new int[][] {
        {32,1,2,3,4,5},
        {4,5,6,7,8,9},
        {8,9,10,11,12,13},
        {12,13,14,15,16,17},
        {16,17,18,19,20,21},
        {20,21,22,23,24,25},
        {24,25,26,27,28,29},
        {28,29,30,31,32,1}
      };
      int[][] P = new int[][] {
        {16,7,20,21},
        {29,12,28,17},
        {1,15,23,26},
        {5,18,31,10},
        {2,8,24,14},
        {32,27,3,9},
        {19,13,30,6},
        {22,11,4,25}
      };
      boolean[] ER = permute(R, E);
      boolean[] temp = arrayXOR(ER,K);
      boolean[][] B = new boolean[8][6];
      boolean[][] SB = new boolean[8][4];
      boolean[] pre_fin = new boolean[32];
      for (int i =0;i<8;i++){
        B[i] = Arrays.copyOfRange(temp, i*6, (i+1)*6);
        SB[i] = get_SboxVal(B[i], i);
        for(int j=i*4;j<(i+1)*4;j++){
            pre_fin[j]=SB[i][j%4];
        }
      }
      boolean[] fin = permute(pre_fin, P);
      return fin;
    }
    public static boolean[] get_SboxVal(boolean[] bits, int n){
      int s_boxes[][][] = new int[][][] {
        {
        {14,4,13,1,2,15,11,8,3,10,6,12,5,9,0,7},
        {0,15,7,4,14,2,13,1,10,6,12,11,9,5,3,8},
        {4,1,14,8,13,6,2,11,15,12,9,7,3,10,5,0},
        {15,12,8,2,4,9,1,7,5,11,3,14,10,0,6,13},
        },{
        {15,1,8,14,6,11,3,4,9,7,2,13,12,0,5,10},
        {3,13,4,7,15,2,8,14,12,0,1,10,6,9,11,5},
        {0,14,7,11,10,4,13,1,5,8,12,6,9,3,2,15},
        {13,8,10,1,3,15,4,2,11,6,7,12,0,5,14,9}
        },{
        {10,0,9,14,6,3,15,5,1,13,12,7,11,4,2,8},
        {13,7,0,9,3,4,6,10,2,8,5,14,12,11,15,1},
        {13,6,4,9,8,15,3,0,11,1,2,12,5,10,14,7},
        {1,10,13,0,6,9,8,7,4,15,14,3,11,5,2,12}
        },{
        {7,13,14,3,0,6,9,10,1,2,8,5,11,12,4,15},
        {13,8,11,5,6,15,0,3,4,7,2,12,1,10,14,9},
        {10,6,9,0,12,11,7,13,15,1,3,14,5,2,8,4},
        {3,15,0,6,10,1,13,8,9,4,5,11,12,7,2,14}
        },{
        {2,12,4,1,7,10,11,6,8,5,3,15,13,0,14,9},
        {14,11,2,12,4,7,13,1,5,0,15,10,3,9,8,6},
        {4,2,1,11,10,13,7,8,15,9,12,5,6,3,0,14},
        {11,8,12,7,1,14,2,13,6,15,0,9,10,4,5,3}
        },{
        {12,1,10,15,9,2,6,8,0,13,3,4,14,7,5,11},
        {10,15,4,2,7,12,9,5,6,1,13,14,0,11,3,8},
        {9,14,15,5,2,8,12,3,7,0,4,10,1,13,11,6},
        {4,3,2,12,9,5,15,10,11,14,1,7,6,0,8,13}
        },{
        {4,11,2,14,15,0,8,13,3,12,9,7,5,10,6,1},
        {13,0,11,7,4,9,1,10,14,3,5,12,2,15,8,6},
        {1,4,11,13,12,3,7,14,10,15,6,8,0,5,9,2},
        {6,11,13,8,1,4,10,7,9,5,0,15,14,2,3,12}
        },{
        {13,2,8,4,6,15,11,1,10,9,3,14,5,0,12,7},
        {1,15,13,8,10,3,7,4,12,5,6,11,0,14,9,2},
        {7,11,4,1,9,12,14,2,0,6,10,13,15,3,5,8},
        {2,1,14,7,4,10,8,13,15,12,9,0,3,5,6,11}
        }
      };
      int[][] chosed_S = s_boxes[n];
      int row_num = bits2int(new boolean[] {bits[0],bits[5]});
      int col_num = bits2int(new boolean[] {bits[1],bits[2],bits[3],bits[4]});
      int chosen_num = chosed_S[row_num][col_num];
      return int2bits(chosen_num, 4);
    }
    public static String encrypt(String hexMessage, boolean[][] keys) {


      int[][] IP_matrix = new int[][] {
        {58,50,42,34,26,18,10,2},
        {60,52,44,36,28,20,12,4},
        {62,54,46,38,30,22,14,6},
        {64,56,48,40,32,24,16,8},
        {57,49,41,33,25,17,9,1},
        {59,51,43,35,27,19,11,3},
        {61,53,45,37,29,21,13,5},
        {63,55,47,39,31,23,15,7}
      };
      int[][] IP_inv_matrix = new int[][]{
        {40,8,48,16,56,24,64,32},
        {39,7,47,15,55,23,63,31},
        {38,6,46,14,54,22,62,30},
        {37,5,45,13,53,21,61,29},
        {36,4,44,12,52,20,60,28},
        {35,3,43,11,51,19,59,27},
        {34,2,42,10,50,18,58,26},
        {33,1,41,9,49,17,57,25}
      };

      // boolean[] M = binstr2bits(message);
      boolean[] M = hex2bits(hexMessage);
      boolean[] IP = permute(M, IP_matrix);

      boolean[][] L = new boolean[17][32];
      boolean[][] R = new boolean[17][32];

      L[0] = Arrays.copyOfRange(IP, 0, 32);
      R[0] = Arrays.copyOfRange(IP, 32, IP.length);

      for (int i =1;i<17;i++){
          L[i] = R[i-1];
          R[i] = arrayXOR(L[i-1],feistel(R[i-1],keys[i-1]));
      }
      return bits2hex(permute(concat(R[16], L[16]),IP_inv_matrix));
    }
    public static void main(String[] args) {
        boolean[][] Keys = keyGen("00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001");        
        System.out.println(encrypt("0123456789ABCDEF",Keys));
    }
}
