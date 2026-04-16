import java.util.Arrays;
import java.util.Random;

public class ArrayFiller extends Thread{
    private int[] array;
    private int size;

    public ArrayFiller(int size) {
        this.size = size;
    }

    @Override
    public synchronized void run() {
        System.out.println("Запущен поток заполнения массива...");
        Random random = new Random();
        this.array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(100);
        }
        System.out.println("Сгенерирован массив: " + Arrays.toString(array));
        this.notifyAll(); // Уведомляем все ожидающие потоки, что массив готов
    }

    // Синхронизированный метод для безопасного доступа к массиву
    public synchronized int[] getArray() throws InterruptedException {
        // Поток будет ждать, пока массив не будет заполнен
        while (array == null) {
            this.wait();
        }
        return array;
    }
}