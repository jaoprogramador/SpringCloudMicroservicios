package academy.digitallab.store.product;

import academy.digitallab.store.product.entity.Category;
import academy.digitallab.store.product.entity.Product;
import academy.digitallab.store.product.repository.ProductRepository;
import academy.digitallab.store.product.service.ProductService;
import academy.digitallab.store.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach//se tiene que ejecutar antes de comenzar nuestro test para crear un "contexto" de prueba
    public void setup(){
    	System.out.print("setup::INI");
        MockitoAnnotations.initMocks(this);
        productService =  new ProductServiceImpl( productRepository);
        Product computer =  Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));
        Mockito.when(productRepository.save(computer)).thenReturn(computer);
        System.out.print("setup::FIN");

    }

    @Test
   public void whenValidGetID_ThenReturnProduct(){
       System.out.print("whenValidGetID_ThenReturnProduct::INI");
       Product found = productService.getProduct(1L);
       Assertions.assertThat(found.getName()).isEqualTo("computer");
       System.out.print("whenValidGetID_ThenReturnProduct::FIN");

   }

   @Test
   public void whenValidUpdateStock_ThenReturnNewStock(){
	   System.out.print("whenValidUpdateStock_ThenReturnNewStock::INI");
       Product newStock = productService.updateStock(1L,Double.parseDouble("8"));
       Assertions.assertThat(newStock.getStock()).isEqualTo(13);
       System.out.print("whenValidUpdateStock_ThenReturnNewStock::FIN");
   }
}
