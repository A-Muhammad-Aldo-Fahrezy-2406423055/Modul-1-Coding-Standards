package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class EditProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void editProduct_isCorrect(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        driver.findElement(By.id("nameInput")).sendKeys("Sampo Awal");
        driver.findElement(By.id("quantityInput")).sendKeys("100");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        WebElement editButton = driver.findElement(By.xpath("//tr[td[contains(text(), 'Sampo Awal')]]//a[contains(@href, '/product/edit/')]"));
        editButton.click();

        WebElement nameInput = driver.findElement(By.id("nameInput"));
        nameInput.clear();
        nameInput.sendKeys("Sampo Edit");

        WebElement quantityInput = driver.findElement(By.id("quantityInput"));
        quantityInput.clear();
        quantityInput.sendKeys("200");

        driver.findElement(By.cssSelector("button[type='submit']")).click();

        String pageSource = driver.getPageSource();
        assertTrue(pageSource.contains("Sampo Edit"));
        assertTrue(pageSource.contains("200"));
    }
}