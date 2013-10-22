import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.data.DynamicForm;
import play.data.validation.ValidationError;
import play.data.validation.Constraints.RequiredValidator;
import play.i18n.Lang;
import play.libs.F;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import org.junit.Test;
import static org.junit.Assert.*;

import models.*;
import com.avaje.ebean.*;

/**
*
* Simple (JUnit) tests that can call all parts of a play app.
* If you are interested in mocking a whole application, see the wiki for more details.
*
*/
public class ApplicationTest {

  @Test
  public void passingTest() {
    String first = "OK";
    String second = "OK";
    assertEquals("This test will pass", first, second);
  }

  @Test
  public void failingTest() {
    String first = "OK";
    String second = "NOT OK";
    assertEquals("This test will fail", first, second);
  }

  @Test
  public void findByEan() {
    running(fakeApplication(), new Runnable() {
     public void run() {
         Product product = Product.findByEan("1111111111111");
         assertThat(product.name).isEqualTo("Paperclip 1111111111111");
     }
    });
  }
    
    @Test
    public void pagination() {
        running(fakeApplication(), new Runnable() {
           public void run() {
               Page<Product> products = Product.find(1);
               assertThat(products.getTotalRowCount()).isEqualTo(50);
               assertThat(products.getList().size()).isEqualTo(10);
           }
        });
    }
   
}
