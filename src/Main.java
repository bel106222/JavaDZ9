/*
Задание 1
При старте приложения запускаются три потока. Первый поток заполняет
массив случайными числами. Два других потока ожидают заполнения.
Когда массив заполнен оба потока запускаются. Первый поток находит сумму
элементов массива, второй поток среднеарифметическое значение в массиве.
Полученный массив, сумма и среднеарифметическое возвращаются в метод
main, где должны быть отображены.
Задание 2
Пользователь с клавиатуры вводит путь к файлу. После чего запускаются
три потока. Первый поток заполняет файл случайными числами. Два других
потока ожидают заполнения. Когда файл заполнен оба потока стартуют.
Первый поток находит все простые числа, второй поток - факториал каждого
числа в файле. Результаты поиска каждый поток должен записать в новый файл.
В методе main необходимо отобразить статистику выполненных операций.
Задание 3
Пользователь с клавиатуры вводит путь к существующей директории и к
новой директории. После чего запускается поток, который должен
скопировать содержимое директории в новое место. Необходимо сохранить
структуру директории. В методе main необходимо отобразить статистику
выполненных операций.
Задание 4
Пользователь с клавиатуры вводит путь к существующей директории и слово
для поиска. После чего запускаются два потока. Первый должен найти файлы,
содержащие искомое слово и слить их содержимое в один файл. Второй
поток ожидает завершения работы первого потока. После чего проводит
вырезание всех запрещенных слов (список этих слов нужно считать из файла
с запрещенными словами) из полученного файла. В методе main необходимо
отобразить статистику выполненных операций.
*/

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("ЗАДАНИЕ 1:");
        task1();
        System.out.println("ЗАДАНИЕ 2:");
        task2();
    }

    public static void task1() throws InterruptedException {

        final int ARRAY_SIZE = 10;
        // Создаем один экземпляр ArrayFiller и передаем его обоим рабочим потокам.
        // Это гарантирует, что они работают с одним и тем же массивом.
        ArrayFiller filler = new ArrayFiller(ARRAY_SIZE);
        ArraySum sumThread = new ArraySum(filler);
        ArrayAverage avgThread = new ArrayAverage(filler);
        // Запускаем потоки. Порядок запуска sum и avg не важен, они все равно будут ждать.
        sumThread.start();
        avgThread.start();
        // Запускаем поток заполнения. Он выполнится первым и "разбудит" остальные.
        filler.start();
        // Ждем завершения всех потоков, чтобы получить финальные результаты.
        filler.join();
        sumThread.join();
        avgThread.join();

        int[] finalArray = filler.getArray(); // На этом этапе getArray() не будет ждать, так как массив уже заполнен.
        System.out.println("Массив: " + Arrays.toString(finalArray));
        // Получаем и выводим сумму и среднее арифметическое из соответствующих объектов
        System.out.println("Сумма элементов: " + sumThread.getSum());
        System.out.println("Среднее арифметическое: " + avgThread.getAvg());
    }

    public static void task2() throws InterruptedException {
        final int ARRAY_SIZE = 10;
        final int MAX_RANDOM = 20;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите путь к файлу: ");
        //String filePath = scanner.nextLine();
        String filePath = "randoms.txt";
        System.out.println(filePath);

        // Поток для заполнения файла
        Thread fillerThread = new Thread(new FileFiller(filePath, ARRAY_SIZE, MAX_RANDOM));
        fillerThread.start();

        // Потоки для обработки
        Thread primeThread = new Thread(new PrimeFinder(filePath));
        Thread factorialThread = new Thread(new FactorialCalc(filePath));
        primeThread.start();
        factorialThread.start();

        // Ожидаем завершения всех потоков
        fillerThread.join();
        primeThread.join();
        factorialThread.join();

        System.out.println("Простые числа сохранены в: primes.txt");
        System.out.println("Факториалы сохранены в: factorials.txt");
    }
}