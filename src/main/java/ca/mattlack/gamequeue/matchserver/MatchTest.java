package ca.mattlack.gamequeue.matchserver;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MatchTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        int minArrSize = 20;

        long s = 0;
        Random random = new Random(System.currentTimeMillis());
       // for(int i = 0; i < 100; i++) {
            boolean failed = false;
            for (int iteration = 0; iteration < 10000000; iteration++) {
                //Create array
                int sum = random.nextInt(100) + 1; //1-100
                int c = sum;

                List<Integer> nums = new ArrayList<>();

                while (c != 0) {
                    int n = random.nextInt(c) + 1; //1-c
                    c -= n;
                    nums.add(n);
                }

                int[] sol = new int[nums.size()];
                for (int i = 0; i < nums.size(); i++) {
                    sol[i] = nums.get(i);
                }

                while (nums.size() < minArrSize) {
                    nums.add(random.nextInt(Math.max(1, sum-1)) + 1); //1-100
                }

                int[] arr = new int[nums.size()];
                for (int i1 = 0, numsSize = nums.size(); i1 < numsSize; i1++) {
                    int num = nums.get(i1);
                    arr[i1] = num;
                }

                if (!hasSum(arr, sum)) {
                    System.out.println("FAILED! (iteration: " + iteration + ")");
                    Arrays.sort(arr);
                    System.out.println("Sum: " + sum + " Arr: " + Arrays.toString(arr));
                    System.out.println("Solution: " + Arrays.toString(sol));
                    s += iteration;
                    failed = true;
                    break;
                }
            }
            if(!failed) {
                s += 100000;
            }
     //   }
        System.out.println("Average iterations needed for failure: " + (s / 100L));
    }

    public static boolean hasSum(int[] arr, int target) {
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; i++) {
            int sum = 0;
            for (int j = 0; j < arr.length; j++) {
                int n = arr[arr.length - (i + j) % arr.length - 1];
                if (sum + n == target)
                    return true;
                if (sum + n > target)
                    continue;
                sum += n;
            }
        }
        return false;
    }
}
