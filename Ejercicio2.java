package LabEDA04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ejercicio2 {

    private static final int nProp = 8;
    private static final int nRiesgos = 5;

    private static final int[][] contribuciones = {
            { -1, 1, -1, -1, 0 }, // Conocimientos equipo
            { -1, 1, 0, 0, -1 }, // Plazo
            { 1, 1, 1, 1, 0 }, // Escalabilidad
            { 0, 1, 0, 0, -1 }, // Seguridad
            { -1, 0, -1, -1, 0 }, // Recursos para calidad
            { 0, 1, 0, 0, 1 }, // Complejidad
            { 1, 1, 0, -1, -1 }, // Fiabilidad
            { 0, 0, 1, 0, -1 } // Interoperabilidad
    };

    private static final int maxPropAFavorecer = 4;
    private static final int sumaMaxPorRiesgo = 2;

    public static void main(String[] args) {
        List<Integer> solucion = obtenerSolucionOptima();
        System.out.println("Solución óptima: " + solucion);
        System.out.println("Contribución total: " + calcularContribucion(solucion));
        System.out.println("Cumple restricciones: " + cumpleRestricciones(solucion));
    }

    public static List<Integer> obtenerSolucionOptima() {
        int[][] dp = new int[nProp + 1][maxPropAFavorecer + 1];
        for (int[] row : dp) {
            Arrays.fill(row, Integer.MAX_VALUE / 2);
        }
        dp[0][0] = 0;

        for (int i = 1; i <= nProp; i++) {
            for (int j = 0; j <= maxPropAFavorecer; j++) {
                dp[i][j] = dp[i - 1][j];
                if (j > 0) {
                    dp[i][j] = Math.min(dp[i][j], dp[i - 1][j - 1] + calcularContribucionIndividual(i - 1));
                }
            }
        }

        List<Integer> solucion = new ArrayList<>();
        for (int i = nProp, j = maxPropAFavorecer; i > 0; i--) {
            if (j > 0 && dp[i][j] == dp[i - 1][j - 1] + calcularContribucionIndividual(i - 1)) {
                solucion.add(i - 1);
                j--;
            }
        }

        return solucion;
    }

    public static int calcularContribucionIndividual(int prop) {
        int contribucion = 0;
        for (int r = 0; r < nRiesgos; r++) {
            contribucion += contribuciones[prop][r];
        }
        return contribucion;
    }

    public static int calcularContribucion(List<Integer> propiedades) {
        int contribucionTotal = 0;
        for (int prop : propiedades) {
            for (int r = 0; r < nRiesgos; r++) {
                contribucionTotal += contribuciones[prop][r];
            }
        }
        return contribucionTotal;
    }

    public static boolean cumpleRestricciones(List<Integer> propiedades) {
        int[] sumaRiesgos = new int[nRiesgos];
        for (int prop : propiedades) {
            for (int r = 0; r < nRiesgos; r++) {
                sumaRiesgos[r] += contribuciones[prop][r];
            }
        }
        for (int sumaRiesgo : sumaRiesgos) {
            if (Math.abs(sumaRiesgo) > sumaMaxPorRiesgo) {
                return false;
            }
        }
        return true;
    }
}