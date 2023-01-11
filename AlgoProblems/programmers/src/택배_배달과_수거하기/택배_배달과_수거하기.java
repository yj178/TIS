package 택배_배달과_수거하기;

import java.util.LinkedList;
import java.util.Queue;

public class 택배_배달과_수거하기 {
    public static void main(String[] args) {
        int cap = 4;
        int n = 5;
        int[] deliveries = {1, 0, 3, 1, 2};
        int[] pickups = {0, 3, 0, 4, 0};
        택배_배달과_수거하기 problem = new 택배_배달과_수거하기();
        System.out.println(problem.solution(cap, n, deliveries, pickups));
    }

    static Queue<Box> deliveryInfo, pickupInfo;
    static int len;

    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        deliveryInfo = new LinkedList<>();
        pickupInfo = new LinkedList<>();
        len = deliveries.length;

        for (int idx = len - 1; idx >= 0; idx--) {
            if (deliveries[idx] != 0) {
                deliveryInfo.add(new Box(idx + 1, deliveries[idx]));
            }
            if (pickups[idx] != 0) {
                pickupInfo.add(new Box(idx + 1, pickups[idx]));
            }
        }
        long answer = 0;
        int dis = 0, dCap = 0, pCap = 0;
        while (!deliveryInfo.isEmpty() || !pickupInfo.isEmpty()) {
            int dDis = deliveryInfo.isEmpty() ? 0 : deliveryInfo.peek().dis;
            int pDis = pickupInfo.isEmpty() ? 0 : pickupInfo.peek().dis;
            dis = Math.max(dDis, pDis);


            while (!deliveryInfo.isEmpty() && dCap < cap) {
                if(dCap + deliveryInfo.peek().cap <= cap)
                    dCap += deliveryInfo.poll().cap;
                else{
                    deliveryInfo.peek().cap -= cap - dCap;
                    dCap = cap;
                }
            }
            while (!pickupInfo.isEmpty() && pCap < cap) {
                if(pCap + pickupInfo.peek().cap <= cap)
                    pCap += pickupInfo.poll().cap;
                else{
                    pickupInfo.peek().cap -= cap - pCap;
                    pCap = cap;
                }
            }

            answer += dis * 2;
            dCap = 0;
            pCap = 0;
        }

        return answer;
    }

    public static class Box implements Comparable<Box> {
        int dis, cap;

        public Box(int dis, int cap) {
            this.dis = dis;
            this.cap = cap;
        }

        @Override
        public int compareTo(Box o) {
            return -Integer.compare(this.dis, o.dis);
        }
    }
}
