import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class FactorialCalc implements Runnable{
    private final String filePath;

    FactorialCalc(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void run() {
        waitForFile();
        try (Scanner scanner = new Scanner(new File(filePath));
             PrintWriter writer = new PrintWriter(new FileWriter("factorials.txt"))) {
            int counter = 0;
            while (scanner.hasNextInt()) {
                int num = scanner.nextInt();
                long fact = factorial(num);
                writer.println(num + "! = " + fact);
                counter++;
            }
            System.out.println("Вычислено " + counter + " факториалов.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private long factorial(int n) {
        long result = 1;
        for (int i = 2; i <= n; i++) result *= i;
        return result;
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