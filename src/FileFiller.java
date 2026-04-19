import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class FileFiller implements Runnable {
    private final String filePath;
    private final int size;
    private final int max;
    // Флаг готовности файла. volatile гарантирует, что изменения видны всем потокам сразу.
    public static volatile boolean fileReady = false;
    // Объект для синхронизации потоков (монитор)
    public static final Object lock = new Object();

    FileFiller(String filePath, int size, int max) {
        this.filePath = filePath;
        this.size = size;
        this.max = max;
    }

    @Override
    public synchronized void run() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            Random random = new Random();
            for (int i = 0; i < this.size; i++) {
                int num = random.nextInt(this.max) + 1;
                writer.println(num);
            }
            System.out.println("Файл " + filePath + " заполнен случайными числами.");
            synchronized (this.lock) {
                this.fileReady = true;
                this.lock.notifyAll(); // Оповещаем ожидающие потоки
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}