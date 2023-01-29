package 주행거리_비교하기;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class 주행거리_비교하기 {
    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] info = br.readLine().split(" ");
        int[] nums = new int[]{Integer.parseInt(info[0]), Integer.parseInt(info[1])};
        System.out.println(nums[0] == nums[1] ? "same" : nums[0] > nums[1] ? "A" : "B");
    }
}
