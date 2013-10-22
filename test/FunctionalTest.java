import org.junit.*;

import java.util.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;
import com.google.common.collect.ImmutableMap;
import models.Product;
import com.avaje.ebean.*;

public class FunctionalTest {

 @Test
 public void notAuthenticated() {
  running(fakeApplication(), new Runnable() {
    public void run() {
      Result result = callAction(
        controllers.routes.ref.Products.index(),
        fakeRequest()
        );
      assertThat(status(result)).isEqualTo(SEE_OTHER);
      assertThat(redirectLocation(result)).isEqualTo("/login");
    }
  });
}

@Test
public void authenticateSuccess() {
 running(fakeApplication(), new Runnable() {
  public void run() {

    Result result = callAction(
      controllers.routes.ref.Application.authenticate(),
      fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
        "email", "nicolas",
        "password", "nicolas"))
      );
    assertThat(status(result)).isEqualTo(SEE_OTHER);
    assertThat(redirectLocation(result)).isEqualTo("/");
    assertThat(session(result).get("email")).isEqualTo("nicolas");
  }
});
}


 @Test
 public void authenticateFailure() {
   running(fakeApplication(), new Runnable() {
    public void run() {
     
      Result result = callAction(
        controllers.routes.ref.Application.authenticate(),
        fakeRequest().withFormUrlEncodedBody(ImmutableMap.of(
          "email", "x",
          "password", "x"))
        );
      assertThat(status(result)).isEqualTo(FORBIDDEN);
      assertThat(session(result).get("email")).isNullOrEmpty();
    }
  });
 }

  

@Test
public void redirectHomePage() {
  running(fakeApplication(), new Runnable() {
   public void run() {
     Result result = callAction(controllers.routes.ref.Products.index(), fakeRequest().withSession("email", "nicolas"));

     assertThat(status(result)).isEqualTo(SEE_OTHER);
     assertThat(redirectLocation(result)).isEqualTo("/products/");
   }
 });
}

@Test
public void listProductsOnTheFirstPage() {
  running(fakeApplication(), new Runnable() {
   public void run() {
     Result result = callAction(controllers.routes.ref.Products.list(0), fakeRequest().withSession("email", "nicolas"));

     assertThat(status(result)).isEqualTo(OK);
     assertThat("text/html").isEqualTo(contentType(result));
     String content = contentAsString(result);
     assertThat(content).contains("1 - 10 / 50");

     Page<Product> allProducts = Product.find(0);
      for(Product p : allProducts.getList()) {
        assertThat(content).contains(p.name);
     }
   }
 });
}

@Test
public void listProductsOnTheFirstPageWithRouter() {
  running(fakeApplication(), new Runnable() {
   public void run() {
     Result result = routeAndCall(fakeRequest(GET, "/products/").withSession("email", "nicolas"));

     assertThat(status(result)).isEqualTo(OK);
     assertThat("text/html").isEqualTo(contentType(result));
     String content = contentAsString(result);
     assertThat(content).contains("1 - 10 / 50");

     Page<Product> allProducts = Product.find(0);
      for(Product p : allProducts.getList()) {
        assertThat(content).contains(p.name);
     }
   }
 });
  
}

@Test
public void displaysAllProducts() {
  running(fakeApplication(), new Runnable() {
   public void run() {
    Page<Product> allProducts = Product.find(0);
    Content rendered = views.html.catalog.render(allProducts);
    assertThat("text/html").isEqualTo(rendered.contentType());
    for(Product p : allProducts.getList()) {
      assertThat(rendered.body()).contains(p.name);
    }
   }
  });
}


@Test
public void createANewProduct() {
  running(fakeApplication(), new Runnable() {
    public void run() {
                // Result result = callAction(controllers.routes.ref.Application.save());

                // assertThat(status(result)).isEqualTo(BAD_REQUEST);

                // Map<String,String> data = new HashMap<String,String>();
                // data.put("name", "FooBar");
                // data.put("introduced", "badbadbad");
                // data.put("company.id", "1");

                // result = callAction(
                //     controllers.routes.ref.Application.save(), 
                //     fakeRequest().withFormUrlEncodedBody(data)
                // );

                // assertThat(status(result)).isEqualTo(BAD_REQUEST);
                // assertThat(contentAsString(result)).contains("<option value=\"1\" selected>Apple Inc.</option>");
                // assertThat(contentAsString(result)).contains("<input type=\"text\" id=\"introduced\" name=\"introduced\" value=\"badbadbad\" >");
                // assertThat(contentAsString(result)).contains("<input type=\"text\" id=\"name\" name=\"name\" value=\"FooBar\" >");

                // data.put("introduced", "2011-12-24");

                // result = callAction(
                //     controllers.routes.ref.Application.save(), 
                //     fakeRequest().withFormUrlEncodedBody(data)
                // );

                // assertThat(status(result)).isEqualTo(SEE_OTHER);
                // assertThat(redirectLocation(result)).isEqualTo("/computers");
                // assertThat(flash(result).get("success")).isEqualTo("Computer FooBar has been created");

                // result = callAction(controllers.routes.ref.Application.list(0, "name", "asc", "FooBar"));
                // assertThat(status(result)).isEqualTo(OK);
                // assertThat(contentAsString(result)).contains("One computer found");

    }
  });
}

}
