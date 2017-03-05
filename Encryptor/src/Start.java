import java.io.*;
import java.util.Scanner;
/**
 * Created by Vlad on 10.08.2016.
 */
public class Start {
    public static void main(String[] args) {
        Coder starter = new Coder();
        Scanner sc = new Scanner(System.in);
        System.out.println("Шифратор или дешифратор?");
        if (sc.nextLine().equalsIgnoreCase("шифратор")) {
            System.out.println("Случайный ключ?");
            String answer = sc.nextLine();
            String key;
            if (answer.equalsIgnoreCase("нет")) {
                System.out.println("Введите ключ для шифрования.");
                key = sc.nextLine();
            } else {
                System.out.println("Введите длину ключа");
                int length = Integer.parseInt(sc.nextLine());
                key = starter.getRandomKey(length);
            }
            char[] chKey = key.toCharArray();
            System.out.println("Введите способ ввода-ввывода (клавиатура или файл).");
            if (sc.nextLine().equalsIgnoreCase("файл")) {
                try {
                    Scanner fl = new Scanner(new File("input.txt"));
                    PrintWriter out = new PrintWriter(new File("output.txt"));
                    while (fl.hasNext()) {
                        out.println(starter.coder(fl.nextLine().toCharArray(), chKey));
                    }
                    fl.close();
                    out.close();
                    System.out.println("Исполнено!");
                } catch (IOException e) {
                    System.out.println("Файл не найден");
                }
            } else {
                System.out.println("Введите текст для шифрования");
                System.out.println("Зашифрованный текст - " + starter.coder(sc.nextLine().toCharArray(), chKey));
                System.out.println("Ваш ключ - " + key);
            }
        } else  {
            System.out.println("Введите ключ для дешифрования");
            char[] chKey = sc.nextLine().toCharArray();
            System.out.println("Введите способ ввода-вывода (клавиатура или файл).");
            if (sc.nextLine().equalsIgnoreCase("файл")) {
                try {
                    Scanner fl = new Scanner(new File("input.txt"));
                    PrintWriter out = new PrintWriter(new File("output.txt"));
                    while (fl.hasNext()) {
                        out.println(starter.decoder(fl.nextLine().toCharArray(), chKey));
                    }
                    fl.close();
                    out.close();
                    System.out.println("Исполнено!");
                } catch (IOException e) {
                    System.out.println("Файл не найден");
                }
            } else {
                System.out.println("Введите текст для дешифрования");
                System.out.println(starter.decoder(sc.nextLine().toCharArray(), chKey));
            }
        }
        System.out.println("Удачного дня!");
    }
}