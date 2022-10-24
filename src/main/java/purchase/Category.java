package purchase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;

public class Category {

    private String type;
    private List<String> items; // покупки из категории
    private HashMap<Date, Long> purchaseData; // дата и сумма покупки"

    public Category(String type, List<String> items) {
        this.type = type;
        this.items = items;
        this.purchaseData = new HashMap<>();
    }

    public String getType() {
        return type;
    }

    public List<String> getItems() {
        return items;
    }

    public HashMap<Date, Long> getPurchaseData() {
        return purchaseData;
    }

    //метод  даты и суммы покупки
    public void addSale(Date date, Long sum) {
        //покупок несколько
        if (purchaseData.containsKey(date)) {
            Long currentSum = purchaseData.get(date) + sum;
            purchaseData.put(date, currentSum);
        }

        // одна покупка
        if (!purchaseData.containsKey(date)) {
            purchaseData.put(date, sum);
        }
    }

    // общая сумма покупок в категории
    public long totalSum() {
        return purchaseData.values().stream().mapToLong(l -> l).sum();
    }

    //Создание категории из списка tsv-файла
    public static HashSet<Category> createCategories(File file) throws FileNotFoundException {
        HashMap<String, List<String>> categoriesFromFile = new HashMap<>();
        categoriesFromFile.put("другое", new ArrayList<>());
        try (Scanner scanner = new Scanner(new FileInputStream(file))) {
            while (scanner.hasNextLine()) {
                String[] txt = scanner.nextLine().split("\t");
                categoriesFromFile.computeIfAbsent(txt[1], item -> new ArrayList<>()).add(txt[0]);
            }
            HashSet<Category> categories = new HashSet<>();
            List<String> keyList = new ArrayList<>(categoriesFromFile.keySet());
            for (String key : keyList) {
                categories.add(new Category(key, categoriesFromFile.get(key)));
            }
            return categories;
        }
    }
}
