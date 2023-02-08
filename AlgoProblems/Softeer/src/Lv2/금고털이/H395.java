package Lv2.금고털이;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class H395 {
    static int N, W;
    static Product[] list;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] tmp = br.readLine().split(" ");
        W = Integer.parseInt(tmp[0]);
        N = Integer.parseInt(tmp[1]);
        list = new Product[N];
        for (int i = 0; i < N; i++) {
            tmp = br.readLine().split(" ");
            list[i] = new Product(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[1]));
        }
        Arrays.sort(list);
        System.out.println(Arrays.toString(list));
        int w = 0;
        int p = 0;
        int idx = 0;
        while (w < W && idx < N) {
            if (w + list[idx].w <= W) {
                w += list[idx].w;
                p += list[idx].w * list[idx].p;
            } else {
                int ww = (W - w);
                w += ww;
                p += list[idx].p * ww;
            }
            System.out.println(p);
            idx++;
        }
        System.out.println(p);
    }

    static class Product implements Comparable<Product> {
        int w, p;

        public Product(int w, int p) {
            this.w = w;
            this.p = p;
        }

        @Override
        public int compareTo(Product o) {
            return -Integer.compare(this.p, o.p);
        }

        @Override
        public String toString() {
            return "Product{" +
                    "w=" + w +
                    ", p=" + p +
                    '}';
        }
    }
}
