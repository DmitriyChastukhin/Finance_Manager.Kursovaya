package purchase;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;


public class MaxCategoryTest {
    private MaxCategory maxCategory = new MaxCategory();


    @Test
    @DisplayName("Тест: Категория с наибольшей суммой покупки")
    void maxCategoryTest() {
        List<String> materials = new ArrayList<>();
        materials.add("краска");
        Category c1 = new Category("Стройматериалы", materials);
        Date date1 = new Date(20220810);
        c1.addSale(date1, 750L);


        List<String> autoparts = new ArrayList<>();
        autoparts.add("амортизатор");
        Category c2 = new Category("автозапчасти", autoparts);
        Date date2 = new Date(20200909);
        c2.addSale(date2, 3500L);

        HashSet<Category> testCat = new HashSet<>();
        testCat.add(c1);
        testCat.add(c2);
        String result = "{\"maxCategory\": {\"category\":\"автозапчасти\",\"sum\":3500}}";

        Assertions.assertEquals(result, maxCategory.maxCategory(testCat));
    }
}
