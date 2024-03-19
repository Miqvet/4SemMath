package lab1;

import lombok.Getter;
import lombok.Setter;


public class Matrix {
    @Getter
    @Setter
    private static double[] b;
    @Getter
    @Setter
    private static double[][] a;
    @Getter
    @Setter
    private static int dimension;
    @Getter
    @Setter
    private static double[][] data;
    @Getter
    @Setter
    private static double[] results;
    @Getter
    @Setter
    private static double determinant;
    @Getter
    @Setter
    private static double[] xValues;
    public static void showMatrix(double[][] a, double[] b ){
        System.out.println("Матрица:");
        for (int i = 0 ; i < b.length; i++) {
            for (int j = 0; j < b.length; j++) {
                System.out.printf("%15s", Double.parseDouble(String.format("%.5f",a[i][j]).replace(',','.')));
            }
            System.out.println("| " + Double.parseDouble(String.format("%.5f",b[i]).replace(',','.')));
        }
    }
}
