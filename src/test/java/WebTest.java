import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WebTesting {

    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }


    @Test
    void shouldTestForm() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кирилл");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79015554433");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=order-success")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actual);
    }


    @Test
    void shouldTestForm1() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Kirill");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79015554433");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }


    @Test
    void shouldTestEmptyName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79015554433");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actual);
    }


    @Test
    void shouldTestInvalidName() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("1234");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79015554433");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=name].input_invalid .input__sub")).getText().trim();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actual);
    }


    @Test
    void shouldTestEmptyPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кирилл");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Поле обязательно для заполнения", actual);
    }


    @Test
    void shouldTestInvalidPhone() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кирилл");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+790155544");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actual = driver.findElement(By.cssSelector("[data-test-id=phone].input_invalid .input__sub")).getText().trim();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actual);
    }

    

    @Test
    void shouldTestFormWithoutAgreement() throws InterruptedException {
        WebElement form = driver.findElement(By.className("form_theme_alfa-on-white"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Кирилл");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79105554433");
        form.findElement(By.className("button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid .checkbox__text")).getText().trim();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text);
    }




}
