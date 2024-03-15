package util;

import methods.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

public class MainController {
    private static final String FIRST_F_STRING= "x^2";
    private static final String SECOND_F_STRING= "(x^4)/10 + (x^2)/5 - 7";
    private static final String THIRD_F_STRING= "(x)/(x^2 - 1)";

    private static final Function<Double, Double> firstF = x -> Math.pow(x, 2);
    private static final Function<Double, Double> secondF = x -> Math.pow(x, 4) / 10 + Math.pow(x,2) / 5 - 7;
    private static final Function<Double, Double> thirdF = x -> x/(Math.pow(x,2) - 1);
    private static final ArrayList<Function<Double, Double>> functions =  new ArrayList<>(Arrays.asList(firstF, secondF, thirdF));
    private static final ArrayList<String> nameFunctions =  new ArrayList<>(Arrays.asList(FIRST_F_STRING, SECOND_F_STRING, THIRD_F_STRING));

    private static final ArrayList<Double[]> arrayOfSuffer = new ArrayList<>(Arrays.asList(
            new Double[]{},
            new Double[]{},
            new Double[]{-1.0, 1.0}
    ));



    private static final Method[] methods = {
            new MethodLeftRect(),
            new MethodCenterRect(),
            new MethodRightRect(),
            new MethodSimpson(),
            new MethodTrapezoids()
    };

    public static void mainHandler(){
        System.out.println("Список доступных функций:");
        for (int i = 1; i <= nameFunctions.size(); i++)
            System.out.println(i + " --> " + nameFunctions.get(i - 1));

        System.out.println(" ");
        InputReader inputReader = new InputReader();
        int selectedFunction = inputReader.readIndex("Введите номер функции: ", "Функции под таким номером не существует.", MainController.functions.size());
        double a, b;
        while (true) {
            a = inputReader.readDouble("Введите левую границу интервала: ");
            b = inputReader.readDouble("Введите правую границу интервала: ");
            if (a < b)
                break;
            System.out.println("Левая граница должна быть меньше правой.");
        }

        double accuracy;
        int digitsAfterComma;
        while (true) {
            accuracy = inputReader.readDouble("Введите точность: ");
            if (accuracy <= 0){
                System.out.println("Точность должна быть больше 0");
            }
            digitsAfterComma = String.valueOf(accuracy - (double)((long) accuracy) + 1).length() - 2;
            if (digitsAfterComma < 6)
                break;
            else{
                System.out.println("Точность слишком большая программа умрёт");
            }
        }


        List<String> row;
        for (Method method: methods) {
            row = new ArrayList<>();
            Result result = computeRes(method, arrayOfSuffer.get(selectedFunction),a, b, accuracy,selectedFunction);
            row.add(method.toString());
            row.add(String.format("%." + digitsAfterComma + "f", result.getResult()));
            row.add(String.format("%d", result.getPartition()));
            System.out.println(row);
        }
        System.out.println("-----------------------------------\n\n");
    }
    private static Result computeRes(Method method, Double[] interrupts,
                                     double a, double b, double accuracy, int selectedFunction){
        double finalRes=0;
        long finalN = 0;
        Result buff;
        double previousPoint = a;
        if(interrupts.length == 0) {
            return method.compute(functions.get(selectedFunction), a, b, accuracy, "test");
        }else{
            for(Double point: interrupts){
                if(point == a){
                    previousPoint+= Math.pow(10, -6);
                }else if(point == b){
                    b = b - Math.pow(10, -6);
                    break;
                } else if(point > previousPoint && point < b){
                    buff = method.compute(functions.get(selectedFunction), previousPoint, point - Math.pow(10, -6), accuracy, "test");
                    previousPoint = point + Math.pow(10, -6);
                    finalRes += buff.getResult();
                    finalN += buff.getPartition();
                }else if(point > b){
                    break;
                }
            }
            buff = method.compute(functions.get(selectedFunction), previousPoint, b, accuracy, "test");
            finalRes += buff.getResult();
            finalN += buff.getPartition();
        }
        return new Result(finalRes, finalN);
    }
}
