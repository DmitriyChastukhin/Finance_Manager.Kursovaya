import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import purchase.Category;
import purchase.MaxCategory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;

public class Main {

    static final File TSVFILE = new File("categories.tsv");

    public static void main(String[] args) {
        try {
            // считывание tsv-файла и создание категории товаров
            HashSet<Category> categories = Category.createCategories(TSVFILE);

            // класс для анализа суммы покупок
            MaxCategory manager = new MaxCategory();

            try (ServerSocket serverSocket = new ServerSocket(8989)) { // старт сервера
                while (true) { // принять подключения
                    try (Socket socket = serverSocket.accept();
                         BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                         PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
                        String input = in.readLine();

                        //запись входящих данных о покупках в одну из категорий
                        getReguest(input, categories);

                        //вывод максимальной суммы покупок
                        out.println(manager.maxCategory(categories));

                    }
                }
            } catch (IOException e) {
                System.out.println("Не могу стартовать сервер");
                e.printStackTrace();
            } catch (java.text.ParseException | ParseException e) {
                System.out.println("Неправильный формат запроса");
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл categories.tsv");
            e.printStackTrace();
        }
    }

    //чтение и добавление данных в каждую категорию
    static void getReguest(String input, HashSet<Category> categories) throws ParseException, java.text.ParseException {
        String incomeProduct;
        Date incomeDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
        Long incomeSum;
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(input);
        incomeProduct = (String) json.get("title");
        String date = (String) json.get("date");
        incomeDate = sdf.parse(date);
        incomeSum = (Long) json.get("sum");
        if (categories.stream().noneMatch(category -> category.getItems().contains(incomeProduct))) {
            categories.stream().filter(category -> category.getType().contains("другое"))
                    .forEach(category -> category.addSale(incomeDate, incomeSum));
        }
        categories.stream().filter(category -> category.getItems().contains(incomeProduct))
                .forEach(category -> category.addSale(incomeDate, incomeSum));
    }
}