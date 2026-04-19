import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrimeFinder implements Runnable {
    private final String filePath;

    PrimeFinder(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        waitForFile();
        List<Integer> primes = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filePath))) {
            while (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                if (isPrime(num)) primes.add(num);
            }
            // Запись результатов
            try (PrintWriter writer = new PrintWriter(new FileWriter("primes.txt"))) {
                for (int prime : primes) writer.println(prime);
            }
            System.out.println("Найдено " + primes.size() + " простых чисел.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isPrime(int n) {
        if (n < 2) return false;
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) return false;
        }
        return true;
    }

    private void waitForFile() {
        synchronized (FileFiller.lock) {
            while (!FileFiller.fileReady) {
                try {
                    FileFiller.lock.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}