package service;

import com.education.entity.Product;
import com.education.entity.Type;
import com.education.exceptions.EntityAlreadyExistsException;
import com.education.exceptions.EntityIllegalArgumentException;
import com.education.exceptions.EntityNotFoundException;
import com.education.repositories.ProductRepository;
import com.education.service.impl.DefaultProductService;
import config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@Transactional
@SpringBootTest
@ContextConfiguration(classes = {TestConfig.class})
public class DefaultProductServiceTest {
    @Autowired
    private DefaultProductService defaultProductService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void findAllTest() {
        List<Product> exp = List.of(
                new Product(1, "Эскимо_тест",
                        Date.valueOf("2020-03-25"), 10, new Type(3, "Мороженое")),
                new Product(2, "Сыр Пермский_тест",
                        Date.valueOf("2020-04-25"), 12, new Type(1, "Сыр")),
                new Product(3, "Молоко нытвенское_тест",
                        Date.valueOf("2020-05-25"), 1, new Type(2, "Молоко"))
        );
        assertTrue(defaultProductService.findAll().containsAll(exp));
    }

    @Test
    public void findByIdTest() {
        Product product = new Product(1, "Эскимо_тест",
                Date.valueOf("2020-03-25"), 10, new Type(3, "Мороженое"));
        assertEquals(product, defaultProductService.findById(1));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByNullIdTest() {
        defaultProductService.findById(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByWrongFormatIdTest() {
        defaultProductService.findById("wrong id");
    }

    @Test(expected = EntityNotFoundException.class)
    public void findByNotExistedIdTest() {
        defaultProductService.findById(-1);
    }

    @Test
    public void createProductTest() {
        Product product = new Product(4, "test1",
                Date.valueOf("2020-06-07"), 1, new Type(1, "Сыр"));
        defaultProductService.create(product);
        assertEquals(product, productRepository.findById(4).get());
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNUllProductTest() {
        defaultProductService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createProductWithNullIdTest() {
        defaultProductService.create(new Product(null, "Эскимо_тест",
                Date.valueOf("2020-03-25"), 10, new Type(3, "Мороженое")));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createProductWithNullTypeTest() {
        defaultProductService.create(new Product(1, "Эскимо_тест",
                Date.valueOf("2020-03-25"), 10, null));
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createProductWithNullTypeIdTest() {
        defaultProductService.create(new Product(1, "Эскимо_тест",
                Date.valueOf("2020-03-25"), 10, new Type(null, "Мороженое")));
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void createProductAlreadyExistsTest() {
        defaultProductService.create(new Product(1, "тест",
                Date.valueOf("2020-03-25"), 10, new Type(3, "Мороженое")));
    }

    @Test
    public void deleteTest() {
        defaultProductService.delete(1);
        assertTrue(productRepository.findById(1).isEmpty());
    }
}


