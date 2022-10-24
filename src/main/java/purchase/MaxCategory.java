package purchase;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


public class MaxCategory {

    public String maxCategory(HashSet<Category> categories) {

        // категория с наибольшей  суммой покупок
        List<Category> comparableCategories = categories.stream()
                .sorted(comparatorMaxSum)
                .collect(Collectors.toList());
        Category maxCategory = comparableCategories.get(comparableCategories.size() - 1);

        String message = "{\"maxCategory\": {\"category\":\""
                + maxCategory.getType()
                + "\",\"sum\":" + maxCategory.totalSum() + "}}";
        return message;
    }

    //сортировка категорий по максимальной  сумме покупок
    Comparator<Category> comparatorMaxSum = (c1, c2) -> {
        long sum1 = c1.totalSum();
        long sum2 = c2.totalSum();
        return Long.compare(sum1, sum2);
    };
}
