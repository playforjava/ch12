import org.junit.*;

import play.mvc.*;
import play.test.*;
import play.libs.F.*;

import static play.test.Helpers.*;
import static org.fest.assertions.Assertions.*;

import static org.fluentlenium.core.filter.FilterConstructor.*;

public class IntegrationTest {

    /**
     * add your integration test here
     * in this example we just check if the welcome page is being shown
     */   
    @Test
    public void productListingTest() {
        running(testServer(3333, fakeApplication()), HTMLUNIT, new Callback<TestBrowser>() {
            public void invoke(TestBrowser browser) {
                browser.goTo("http://localhost:3333");
                // login
                browser.fill("input[name='email']").with("nicolas");
                browser.fill("input[name='password']").with("nicolas");
                browser.submit("button[type='submit']");
                assertThat(browser.$("table td a")).isNotEmpty();
                browser.$("table td a").first().click();
        assertThat("http://localhost:3333/products/0000000000000").isEqualTo(
            browser.url());
        assertThat("Product (Paperclip 0)").isEqualTo(
            browser.$("legend").getText());
            }
        });
    }
  
}
