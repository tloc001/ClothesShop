package com.poly.services;

import com.poly.models.Product;
import com.poly.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestEntityManager;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;
    @Test
    public void testFillPublic() {
            Page<String[]> result = productService.fillPublic(0, 10, "", "");
          for (Object[] strings : result.getContent()) {
              System.out.println(Arrays.toString(strings));
          }
        assertNotNull(result);
    }
    @Test
    public void testFillPublicBySearch() {
        Page<String[]> result = productService.fillPublic(0, 10, "", "áo thun");
        for (Object[] strings : result.getContent()) {
            System.out.println(Arrays.toString(strings));
        }
        assertNotNull(result);
    }
    @Test
    public void testFillPublicBySearch2() {
        Page<String[]> result = productService.fillPublic(0, 10, "", "áo nam siêu đẹp");
        for (Object[] strings : result.getContent()) {
            System.out.println(Arrays.toString(strings));
        }
        assertTrue(result.getContent().isEmpty());
    }
    @Test
    public void testFillPublicByCategories() {
        Page<String[]> result = productService.fillPublic(0, 10, "áo thun", "");
        for (Object[] strings : result.getContent()) {
            System.out.println(Arrays.toString(strings));
        }
        assertNotNull(result);
    }
    @Test
    public void testFillPublicByCategoriesAndSearch() {
        Page<String[]> result = productService.fillPublic(0, 10, "áo thun", "áo thun nữ");
        for (Object[] strings : result.getContent()) {
            System.out.println(Arrays.toString(strings));
        }

        assertNotNull(result);
    }



    }
