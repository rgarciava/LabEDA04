package LabEDA04;

public class Ejercicio1 {

    public static void main(String[] args) {
        int[] mundoNormal = { 2, 4, 2, 0, 2, 3, 2, 4, 3, 3, 4 };
        int[] mundoReves = { 4, 3, 3, 4, 2, 3, 2, 0, 1, 4, 2 };

        int saltosMinimos = encontrarSaltosMinimos(mundoNormal, mundoReves);
        if (saltosMinimos == -1) {
            System.out.println("El comisario Hopper no puede llegar al final en el mundo normal.");
        } else {
            System.out.println(
                    "El comisario Hopper puede llegar al final en el mundo normal en " + saltosMinimos + " saltos.");
        }
    }

    public static int encontrarSaltosMinimos(int[] mundoNormal, int[] mundoReves) {
        int n = mundoNormal.length;
        int[] dp = new int[n];
        for (int i = 0; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;
        }

        if (mundoNormal[0] == 0) {
            return -1;
        }

        dp[0] = 1;

        for (int i = 1; i < n; i++) {
            if (mundoNormal[i] == 0) {
                continue;
            }

            for (int j = 1; j <= mundoNormal[i]; j++) {
                if (i - j >= 0 && dp[i - j] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - j] + 1);
                }
            }
        }
        return dp[n - 1] == Integer.MAX_VALUE ? -1 : dp[n - 1];
    }
}
