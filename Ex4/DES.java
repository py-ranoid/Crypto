import java.util.Arrays;

class DES {

    public boolean[] int2bits(int input){
        boolean[] bits = new boolean[7];
        for (int i = 6; i >= 0; i--) {
            bits[i] = (input & (1 << i)) != 0;
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
    
    public static void main(String[] args) {
        boolean[][] Keys = keyGen("00010011 00110100 01010111 01111001 10011011 10111100 11011111 11110001");
        bits2str(Keys[0]);
    }
}
