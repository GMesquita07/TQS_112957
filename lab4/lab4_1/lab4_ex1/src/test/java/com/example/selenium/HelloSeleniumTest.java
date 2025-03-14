package com.example.selenium;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.seljup.SeleniumJupiter;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SeleniumJupiter.class) // Selenium-Jupiter gerencia o WebDriver automaticamente
class HelloSeleniumTest {
    private WebDriver driver;

    @BeforeEach
    void setup() {
        driver = new ChromeDriver();
    }

    @Test
    void testWikipedia() {
        // 1️⃣ Acessar o site da Wikipedia
        String sutUrl = "https://www.wikipedia.org/";
        driver.get(sutUrl);

        // 2️⃣ Obter o título da página
        String title = driver.getTitle();
        System.out.printf("O título da página %s é: %s%n", sutUrl, title);

        // 3️⃣ Verificar se o título contém "Wikipedia"
        assertThat(title).contains("Wikipedia");
    }

    @Test
    void testSlowCalculatorNavigation() {
        // 1️⃣ Abrir a página inicial do site do exercício
        String sutUrl = "https://bonigarcia.dev/selenium-webdriver-java/";
        driver.get(sutUrl);

        // 2️⃣ Encontrar e clicar no link "Slow calculator"
        WebElement slowCalculatorLink = driver.findElement(By.linkText("Slow calculator"));
        slowCalculatorLink.click();

        // 3️⃣ Verificar se a URL está correta
        String expectedUrl = "https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html";
        assertThat(driver.getCurrentUrl()).isEqualTo(expectedUrl);
    }

    @AfterEach
    void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
