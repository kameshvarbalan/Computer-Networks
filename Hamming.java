import java.util.*;
class Hamming{
    private static int[][] transpose(int[][] HT){
        int[][] H = new int[HT[0].length][HT.length];
        for(int i=0; i<HT.length; i++){
            for(int j=0; j<HT[0].length; j++){
                H[j][i] = HT[i][j];
            }
        }
        return H;
    }

    private static int[][] generatorMatrix(int[][] H) {
        int rows = H.length;
        int cols = H[0].length;
        int k = cols-rows;
        int[][] G = new int[k][cols];
        for (int i=0; i<k; i++) {
            G[i][i] = 1;
        }
        for (int i=0; i<k; i++) {
            for (int j=0; j<rows; j++) {
                G[i][k+j] = H[j][i];
            }
        }
        return G;
    }

    private static List<int[]> generateCodewords(int[][] G) {
        int rows = G.length;
        int cols = G[0].length;
        List<int[]> codewords = new ArrayList<>();
        for (int i=0; i<(1<<rows); i++) {
            int[] message = new int[rows];
            for (int j=0; j<rows; j++) {
                message[j] = (i>>j) & 1;
            }
            int[] codeword = new int[cols];
            for (int j=0; j<cols; j++) {
                int sum = 0;
                for (int k=0; k<rows; k++) {
                    sum += G[k][j] * message[k];
                }
                codeword[j] = sum % 2;
            }
            codewords.add(codeword);
        }
        return codewords;
    }

    private static int weight(int[] codeword) {
        int count = 0;
        for (int bit : codeword) {
            if (bit == 1) {
                count++;
            }
        }
        return count;
    }

    private static int calculateMinHammingDistance(List<int[]> codewords) {
        int minDistance = Integer.MAX_VALUE;
        for (int i=0; i<codewords.size(); i++) {
            for (int j=i+1; j<codewords.size(); j++) {
                int distance = hammingDistance(codewords.get(i), codewords.get(j));
                if (distance < minDistance) {
                    minDistance = distance;
                }
            }
        }
        return minDistance;
    }

    private static int hammingDistance(int[] codeword1, int[] codeword2) {
        int distance = 0;
        for (int i=0; i<codeword1.length; i++) {
            if (codeword1[i] != codeword2[i]) {
                distance++;
            }
        }
        return distance;
    }

    public static void main(String[] args) {
        int[][] HT = {{1,0,1}, {1,1,1}, {1,1,0}, {0,1,1}, {1,0,0}, {0,1,0}, {0,0,1}};
        int[][] H = transpose(HT);
        int[][] G = generatorMatrix(H);
        System.out.println("\nGenerator Matrix");
        for(int i=0; i<G.length; i++){
            for(int j=0; j<G[0].length; j++){
                System.out.print(G[i][j] + " ");
            }
            System.out.println();
        }
        List<int[]> codewords = generateCodewords(G);
        System.out.println("\nCode Vectors (4 msg bits + 3 parity bits) - Weight");
        for (int[] codeword: codewords) {
            for (int bit: codeword) {
                System.out.print(bit);
            }
            System.out.println(" - " + weight(codeword));
        }
        int minHammingDistance = calculateMinHammingDistance(codewords);
        System.out.println("\nMinimum Hamming Distance: " + minHammingDistance);
    }
}
