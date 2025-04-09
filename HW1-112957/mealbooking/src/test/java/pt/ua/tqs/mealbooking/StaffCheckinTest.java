/* package pt.ua.tqs.mealbooking;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class StaffCheckinTest {

    private static WebDriver driver;
    private static WebDriverWait wait;

    @BeforeAll
    static void setup() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Aumenta o timeout
    }

    @AfterAll
    static void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    void criarReservaECheckin() throws InterruptedException {
        // Acede à página principal
        driver.get("http://localhost:5173");

        // Espera até aparecer a dropdown de restaurantes
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("restaurant-select")));
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(By.id("restaurant-select")));

        // Aguarda que opções estejam disponíveis
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#restaurant-select option[value]")));
        dropdown.findElement(By.cssSelector("option[value]")).click();

        // Espera que a refeição esteja visível
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("meal-row")));

        // Clica no botão de reserva de almoço
        WebElement reservarBtn = wait.until(ExpectedConditions.elementToBeClickable(By.className("reservation-btn-almoco")));
        reservarBtn.click();

        // Aceita o alerta e extrai o token
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = driver.switchTo().alert();
        String token = alert.getText().replace("Token: ", "").trim();
        alert.accept();

        Assertions.assertFalse(token.isEmpty(), "Token da reserva está vazio!");

        // Acede à página do staff
        driver.get("http://localhost:5173/staff");

        // Espera até aparecer a tabela de reservas
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("token-0")));

        // Extrai o botão de check-in da primeira reserva
        WebElement checkinBtn = wait.until(ExpectedConditions.elementToBeClickable(By.id("checkin-btn-0")));
        checkinBtn.click();

        // Espera pela mensagem de sucesso
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("success-message")));
        Assertions.assertTrue(successMessage.getText().toLowerCase().contains("sucesso"), "Mensagem de sucesso não foi encontrada!");
    }
}
 */