package com.jc.products;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.jc.products.entity.Product;
import com.jc.products.repository.ProductRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(OrderAnnotation.class)
public class ProductTest {
	
	@Autowired
	private ProductRepository productRepository;
	
	
	@Test
	@Rollback(false)
	@Order(1)
	public void testCreateProduct() {
		Product product = new Product("Xiaomi Poco X3 Pro", 7100, 10);
		Product productCreated = productRepository.save(product);
		
		assertNotNull(productCreated);
	}
	
	@Test
	@Order(2)
	public void testFindProductByName() {
		String name = "Xiaomi Poco X3 Pro";
		Product product = productRepository.findByName(name);
		
		assertThat(product.getName()).isEqualTo(name);
	}
	
	 @Test
	 @Order(3)
	 public void testFindProductByNameNoExist() {
		String name = "Xiaomi Redmi Note 10S";
		Product product = productRepository.findByName(name);
		
		assertNull(product);
	 }
	 
	@Test
	@Rollback(false)
	@Order(4)
	public void testUpdateProduct() {
		
		String productName = "Xiaomi Poco X3 Pro"; 
		float newPrice = 7099;
		Integer newStock = 5;
		
		Product product = new Product(productName, newPrice, newStock);
		product.setId(7);
		
		productRepository.save(product);
		
		Product productUpdated = productRepository.findByName(productName);
		
		assertThat(product.equals(productUpdated));
	}
	
	@Test
	@Order(5)
	public void testFindAllProducts() {
		List<Product> productsLst = (List<Product>) productRepository.findAll();
		
		productsLst.forEach(product -> System.out.println(product));
		
		assertThat(productsLst).size().isGreaterThan(0);
	}
	
	@Test
	@Rollback(false)
	@Order(6)
	public void testDeleteProduct() {
		Integer id = 6;
		
		boolean productExistBefore = productRepository.findById(id).isPresent();
		
		productRepository.deleteById(id);
		
		boolean productExistAfter = productRepository.findById(id).isPresent();
		
		assertTrue(productExistBefore);
		assertFalse(productExistAfter);
	}
}