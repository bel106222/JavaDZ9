public class ArrayAverage extends Thread{
    private final ArrayFiller filler;
    private double avgSum = 0;

    public ArrayAverage(ArrayFiller filler) {
        this.filler = filler;
    }

    @Override
    public void run() {
        System.out.println("Поток вычисления среднего ожидает готовности массива...");
        try {
            int[] arr = filler.getArray(); // Ожидает заполнения
            System.out.println("Запущен поток вычисления среднего арифметического...");
            int sum = 0;
            for (int num : arr) {
                sum += num;
            }
            this.avgSum = (double) sum / arr.length;
            System.out.println("Среднее арифметическое: " + this.avgSum);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public double getAvg() {
        return this.avgSum;
    }
}