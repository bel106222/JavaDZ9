public class ArraySum extends Thread {
    private final ArrayFiller filler;
    private int sum = 0;

    public ArraySum(ArrayFiller filler) {
        this.filler = filler;
    }

    @Override
    public void run() {
        System.out.println("Поток вычисления суммы ожидает готовности массива...");
        try {
            int[] arr = filler.getArray(); // Ожидает заполнения
            System.out.println("Запущен поток вычисления суммы...");
            for (int num : arr) {
                this.sum += num;
            }
            System.out.println("Сумма элементов: " + this.sum);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public int getSum() {
        return this.sum;
    }
}