package methods;

import util.Result;

import java.util.function.Function;

public class MethodCenterRect extends Method{
    @Override
    public Result compute(Function<Double, Double> function, double a, double b, double accuracy, String modify) {
        long n = START_PARTITION * 2;

        double res1, res2;
        res1 = computeRes(function, a, b, START_PARTITION, modify);
        while (true) {
            res2 = computeRes(function, a, b, n, modify);
            if (Math.abs(res2 - res1) < accuracy)
                break;
            n *= 2;
            res1 = res2;
        }

        return new Result(res2, n);
    }

    @Override
    double computeRes(Function<Double, Double> function, double a, double b, long n, String modify) {
        double x, h, res;
        res = 0;
        h = (b - a) / n;
        for (int i = 0; i < n; i++) {
            x = a + h * i;
            res += h * function.apply(x + h / 2);
        }
        return res;
    }
    @Override
    public String toString() {
        return "Метод центральных прямоугольников";
    }
}
