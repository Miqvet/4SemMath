package lab1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Reader {
    public void readData() throws Exception {
        Scanner scanner = new Scanner(System.in);
        int isReadFromConsole;
        try{
            System.out.println("Введите имя файла или слово cons или gen для автоматической генерации: ");
            String choice = scanner.next();

            if (Objects.equals(choice, "cons")) {
                isReadFromConsole = 1;
            }else if(Objects.equals(choice, "gen")){
                isReadFromConsole = 2;
            }else{
                File file = new File("./src/main/java/lab1/" + choice);
                scanner = new Scanner(file);
                isReadFromConsole = 3;
            }
            if(isReadFromConsole == 1 || isReadFromConsole == 3){
                if (isReadFromConsole == 1){
                    System.out.print("Введите размерность квадратной матрицы(целое число): ");
                }
                Matrix.setDimension(scanner.nextInt());
                if (Matrix.getDimension() <= 0){
                    throw new NegativeArraySizeException();
                }
                Matrix.setData(new double[Matrix.getDimension()][Matrix.getDimension()]);
                Matrix.setA(new double[Matrix.getDimension()][Matrix.getDimension()]);
                if(isReadFromConsole == 1) {
                    System.out.println("Введите коэфициенты уравнения:");
                }
                for (int i = 0; i < Matrix.getDimension(); i++) {
                    for (int j = 0; j < Matrix.getDimension(); j++) {
                        String buff =scanner.next().replace(',', '.');
                        Matrix.getData()[i][j] = Double.parseDouble(buff);
                        Matrix.getA()[i][j] = Double.parseDouble(buff);
                    }
                }
                Matrix.setResults(new double[Matrix.getDimension()]);

                for (int i = 0; i < Matrix.getDimension(); i++) {
                    String buff =scanner.next().replace(',', '.');
                    Matrix.getResults()[i]= Double.parseDouble(buff);
                }
                Matrix.showMatrix(Matrix.getData(), Matrix.getResults());
                Matrix.setB(Matrix.getResults().clone());
                scanner.close();
            }else{
                System.out.println("Введите размерность квадратной матрицы(целое число): ");
                Matrix.setDimension(scanner.nextInt());
                if (Matrix.getDimension() <= 0){
                    throw new NegativeArraySizeException();
                }
                Matrix.setData(new double[Matrix.getDimension()][Matrix.getDimension()]);
                Matrix.setA(new double[Matrix.getDimension()][Matrix.getDimension()]);

                Random rand = new Random();
                double buffer;
                for (int i = 0; i < Matrix.getDimension(); i++) {
                    for (int j = 0; j < Matrix.getDimension(); j++) {
                        buffer = rand.nextDouble();
                        Matrix.getData()[i][j] = buffer;
                        Matrix.getA()[i][j] = buffer;
                    }
                }
                Matrix.setResults(new double[Matrix.getDimension()]);

                for (int i = 0; i < Matrix.getDimension(); i++) {
                    Matrix.getResults()[i]= rand.nextDouble();
                }
                Matrix.showMatrix(Matrix.getData(), Matrix.getResults());
                Matrix.setB(Matrix.getResults().clone());
            }
        } catch (InputMismatchException e){
            if (Matrix.getDimension() > 0){
                System.out.println("Размерность матрицы - целое число > 0");
            }else{
                System.out.println("Для ввода допускаются только числа");
            }
            throw new Exception();
        } catch (NegativeArraySizeException e){
            System.out.println("Размерность матрицы должна быть целым числом > 0");
            throw new Exception();
        } catch (NoSuchElementException e) {
            System.out.println("Не надо баловаться с cntrl + D");
            throw new Exception();
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
            throw new Exception();
        }
    }
}
