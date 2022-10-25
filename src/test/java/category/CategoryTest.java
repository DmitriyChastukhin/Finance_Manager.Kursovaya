package category;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import purchase.Category;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CategoryTest {
    private Category catTest;

    @BeforeEach
    void setUp() {
        List<String> studies = new ArrayList<>();
        studies.add("автозапчасти");
        catTest = new Category("автозапчасти", studies);
    }

    @Test
    @DisplayName("Одна покупка за день")
    void addSaleTest_singleShop() {
        Date date1 = new Date(20220909);
        Date date2 = new Date(20200909);
        catTest.addSale(date1, 1550L);
        catTest.addSale(date2, 4599L);
        long currentResult = catTest.getPurchaseData().get(date1);
        Assertions.assertEquals(1550, currentResult);
    }

    @Test
    @DisplayName("Несколько покупок за день")
    void addSaleTest() {
        Date date1 = new Date(20220909);
        catTest.addSale(date1, 1550L);
        catTest.addSale(date1, 2500L);
        long currentResult = catTest.getPurchaseData().get(date1);
        Assertions.assertEquals(4050, currentResult);
    }

    @Test
    @DisplayName("Подсчет общей суммы покупок в категории")
    void totalSumTest() {
        Date date1 = new Date(20220909);
        Date date2 = new Date(20221009);
        Date date3 = new Date(20221109);
        catTest.addSale(date1, 200L);
        catTest.addSale(date2, 200L);
        catTest.addSale(date3, 200L);
        Assertions.assertEquals(600, catTest.totalSum());
    }
}