


public class Lab {

    public static void main(String[] args) {
        final int COUNT = (16 - 4) / 2 + 1; //количество четных чисел от 4 до 16 включительно
        short a[] = new short[COUNT];
        for (int i = 0; i < COUNT; i++) {
            a[i] = (short) (16 - i * 2);
        }

        double[] x = new double[17];
        for (int i = 0; i < 17; i++) {
            x[i] = Math.random() * 15 - 10;
        }
	/*
	Для одномерного массива используется латинская а, для двумерного - кириллическая
	*/
        double[][] а = new double[COUNT][17];
        for (int i = 0; i < COUNT; i++) {
            if (a[i] == 4) {
                for (int j = 0; j < 17; j++) {
                    а[i][j] = Math.tan(Math.pow(Math.E, (Math.pow(1.0 / 3 * (x[j] - 1), 2))));
                    System.out.printf("%.4f ", а[i][j]);
                }
            } else if (a[i] == 8 || a[i] == 10 || a[i] == 12) {
                for (int j = 0; j < 17; j++) {
                    а[i][j] = Math.pow(Math.sin(Math.asin((x[j] - 2.5) / 15)), Math.tan(Math.pow(3 /
                            (4 * (x[j] - 2)), 2)) * (Math.cbrt(x[j]) * (1 - Math.asin((x[j] - 2.5) / 15)) - 1));
                    System.out.printf("%.4f ", а[i][j]);
                }
            } else {
                for (int j = 0; j < 17; j++) {
                    а[i][j] = Math.cbrt(Math.pow(Math.tan(Math.pow(x[j], x[j])) /
                            (Math.pow(1.0 / 4 * Math.cos(x[j]), 2) - 2.0 / 3), 3));
                    System.out.printf("%.4f ", а[i][j]);
                }
            }
            System.out.println();
        }
    }
}
