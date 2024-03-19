package lab1;

public class MatrixConverter {
    public static void matrixToTriangularView(double[][] matrix,double[] rightPart, int dimensionMatrix) {
        int counter = 0;
        if (matrix != null) {
            for (int i = 0; i < dimensionMatrix; i++) {
                if(matrix[i][i] == 0){
                    for (int j= i + 1;j < dimensionMatrix; j++) {
                        if (matrix[j][i] != 0) {
                            matrixSwapRows(matrix, rightPart, i, j);
                            counter++;
                        }
                    }
                }
                double c;
                for (int k = i + 1; k < dimensionMatrix; k++) {
                    c = Double.parseDouble(String.format("%.5f",matrix[k][i] / matrix[i][i]).replace(',', '.'));
                    for (int j = 0; j < dimensionMatrix; j++) {
                        matrix[k][j] -= Double.parseDouble(String.format("%.5f",c * matrix[i][j]).replace(',', '.'));
                        matrix[k][j] = Double.parseDouble(String.format("%.5f",matrix[k][j]).replace(',','.'));
                    }
                    matrix[k][i] = 0;
                    rightPart[k] -= Double.parseDouble(String.format("%.5f", c * rightPart[i]).replace(',','.'));
                    rightPart[k] = Double.parseDouble(String.format("%.5f",rightPart[k]).replace(',','.'));
                }
            }
            Matrix.showMatrix(matrix, rightPart);
            Matrix.setDeterminant(MatrixConverter.calculateDeterminant(matrix,rightPart, counter));
            if (Matrix.getDeterminant() == 0) {
                System.out.println("Детерминант равен 0!\nСЛАУ имеет бесконечное множество решений или не имеет вовсе\n");
            }else{
                matrixInversePart(matrix,rightPart, dimensionMatrix);
            }
        } else {
            System.out.println("matrix_to_triangular_view: передан NULL указатель.");
        }
    }

    public static double calculateDeterminant(double[][] matrix,double[] results, int dimensionMatrix){
        double buff = 1;
        for(int i = 0; i < results.length; i++){
            buff *= matrix[i][i];
        }
        buff = buff * Math.pow(-1, dimensionMatrix);
        return buff;
    }
    public static void matrixInversePart(double[][] matrix,double[] results, int dimensionMatrix) {
        double[] xValues = new double[dimensionMatrix];
        Matrix.setXValues(new double[dimensionMatrix]);
        System.out.println("Determinant: " + Matrix.getDeterminant());
        for(int i = dimensionMatrix - 1; i >= 0; i--){
            double s = 0;
            for(int j = i + 1; j < dimensionMatrix; j++){
                s +=Double.parseDouble(String.format("%.5f",matrix[i][j] * xValues[j]).replace(',','.'));

            }
            System.out.println("X" + (i + 1) + ": " +Double.parseDouble(String.format("%.5f",(results[i] - s)/(matrix[i][i])).replace(',', '.')));
            xValues[i] = Double.parseDouble(String.format("%.5f",(results[i] - s)/(matrix[i][i])).replace(',', '.'));
            Matrix.getXValues()[i] = Double.parseDouble(String.format("%.5f",(results[i] - s)/(matrix[i][i])).replace(',', '.'));
        }
    }
    public static void printErrors(double[][] matrix, double[] rightPart, double[] x) {
        double[] error = new double[rightPart.length];
        Matrix.showMatrix(matrix, rightPart);
        for (int i = 0; i < matrix.length; i++) {
            double ax = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                ax += matrix[i][j] * x[j];
            }
            error[i] = ax - rightPart[i];
        }

        System.out.println("Вектор невязок:");
        for (int i = 0; i < error.length; i++) {
            System.out.printf("e%d = %.5f\n", i + 1, error[i]);
        }
    }

    private static void matrixSwapRows(double[][] array, double[] result, int i, int j){
        double[] temp = array[i];
        array[i] = array[j];
        array[j] = temp;
        Matrix.setData(array);
        double buff = result[i];
        result[i] = result[j];
        result[j] = buff;
        Matrix.setResults(result);
    }
}
