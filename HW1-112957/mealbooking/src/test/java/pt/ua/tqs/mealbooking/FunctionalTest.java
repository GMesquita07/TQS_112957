package pt.ua.tqs.mealbooking;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class FunctionalTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterAll
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    void testReservaECancelamento() throws InterruptedException {
        driver.get("http://localhost:5173");

        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("restaurant-select")));
        Select select = new Select(dropdown);
        select.selectByIndex(0); // Cantina Central

        WebElement dateInput = driver.findElement(By.cssSelector("input[type='date']"));
        dateInput.clear();
        dateInput.sendKeys("09-04-2025");
        dateInput.sendKeys(Keys.TAB);

        WebElement procurarBtn = driver.findElement(By.xpath("//button[text()='Procurar']"));
        procurarBtn.click();

        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("meal-row"), 0));

        List<WebElement> buttons = driver.findElements(By.className("reservation-btn-almoco"));
        Assertions.assertFalse(buttons.isEmpty(), "Não há botões de reserva ALMOÇO");
        WebElement reservarBtn = buttons.get(0);
        reservarBtn.click();

        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String token = alert.getText().replace("Token: ", "").trim();
        alert.accept();
        Assertions.assertFalse(token.isEmpty(), "Token vazio!");

        WebElement reservasLink = driver.findElement(By.linkText("As Minhas Reservas"));
        reservasLink.click();

        // Aguarda até que uma reservation com o token esteja visível via className
        wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.className("reservation-token"), 0));
        List<WebElement> tokensVisiveis = driver.findElements(By.className("reservation-token"));
        boolean encontrada = tokensVisiveis.stream().anyMatch(el -> el.getText().contains(token));
        Assertions.assertTrue(encontrada, "Reserva com token não encontrada");

        List<WebElement> cancelarBotoes = driver.findElements(By.className("cancel-btn"));
        Assertions.assertFalse(cancelarBotoes.isEmpty(), "Botão de cancelar não encontrado");
        cancelarBotoes.get(0).click();

        Thread.sleep(1000);
        driver.navigate().refresh();
        Thread.sleep(500);

        List<WebElement> posCancelamento = driver.findElements(By.className("reservation-token"));
        boolean aindaExiste = posCancelamento.stream().anyMatch(el -> el.getText().contains(token));
        Assertions.assertFalse(aindaExiste, "Reserva ainda aparece após cancelamento!");
    }
}
