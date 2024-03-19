package lab1;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();
        try{
            while(true){
                reader.readData();
                MatrixConverter.matrixToTriangularView(Matrix.getData(), Matrix.getResults(), Matrix.getDimension());
                MatrixConverter.printErrors(Matrix.getA(), Matrix.getB(), Matrix.getXValues());
            }
        } catch (Exception e) {
            System.out.println("-----------------------------");
        }
    }
}
