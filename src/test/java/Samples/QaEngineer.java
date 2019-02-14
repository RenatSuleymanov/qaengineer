package Samples;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import java.util.concurrent.TimeUnit;

public class QaEngineer {
    private WebDriver driver;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Selenium\\chromedriver\\chromedriver.exe");
        //System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver\\geckodriver.exe");
        //System.setProperty("webdriver.ie.driver", "C:\\Selenium\\IEDriverServer\\IEDriverServer.exe");
        // Create a new instance of the Firefox driver
        driver = new ChromeDriver();
        //driver = new FirefoxDriver();
        //driver = new InternetExplorerDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("http://blog.csssr.ru/qa-engineer/");
    }

    @Test
    public void test1() {
        //1. Ширина контентной части должна быть равна 1000px
        WebElement element = driver.findElement(By.className("wrap"));
        Assert.assertEquals("(1000, 1757)",element.getSize().toString());
    }

    @Test
    public void test2() {
        //2. При преключении столбики с процентами должны заполняются красным паттерном снизу вверх
        driver.findElement(By.linkText("НАХОДИТЬ НЕСОВЕРШЕНСТВА")).click();
        WebElement element = driver.findElement(By.className("graph-active"));
        int y = element.getRect().y;
        while (element.getSize().getHeight() < 98) {   //98px - graphs-errors graph-active max-height
            //System.out.println("y - " + element.getRect().y + " Height - " + element.getSize().getHeight());
        }
        Assert.assertTrue(y > element.getRect().y);
    }

    @Test
    public void test3() {
        //3. ссылка "Софт для быстрого создания скриншотов"
        driver.findElement(By.linkText("ВНИКАТЬ В ДЕТАЛИ ПРОЕКТОВ")).click();
        driver.findElement(By.linkText("НАХОДИТЬ НЕСОВЕРШЕНСТВА")).click();
        Assert.assertTrue(driver.findElement(By.linkText("Софт для быстрого создания скриншотов")).getAttribute("href").equals("https://monosnap.com"));
    }

    @Test
    public void test4() {
        //4. Section.info не отображает информацию если выбрать любую вкладку дважды
        driver.findElement(By.linkText("ВНИКАТЬ В ДЕТАЛИ ПРОЕКТОВ")).click();
        WebElement element = driver.findElement(By.className("info-details"));

        while (!element.getCssValue("opacity").contentEquals("1")) {
            //System.out.println(element.getCssValue("opacity"));
        }
        driver.findElement(By.linkText("ВНИКАТЬ В ДЕТАЛИ ПРОЕКТОВ")).click();
        while (!element.getCssValue("opacity").contentEquals("1")) {
            //System.out.println(element.getCssValue("opacity"));
        }
        Assert.assertTrue(element.getCssValue("display").equals("block"));
    }

    @Test
    public void test6() {
        //6. Чекбоксы beautiful, attention2, beautiful2 работают только один раз
        driver.findElement(By.linkText("НАХОДИТЬ НЕСОВЕРШЕНСТВА")).click();
        driver.findElement(By.xpath("//label[@for='beautiful']")).click();
        driver.findElement(By.xpath("//label[@for='beautiful']")).click();
        Assert.assertTrue(driver.findElement(By.id("beautiful")).isSelected());

        driver.findElement(By.linkText("СОПРОВОЖДАТЬ ПРОЕКТЫ")).click();
        driver.findElement(By.xpath("//label[@for='attention2']")).click();
        driver.findElement(By.xpath("//label[@for='attention2']")).click();
        Assert.assertTrue(driver.findElement(By.id("attention2")).isSelected());

        driver.findElement(By.linkText("РАБОТАТЬ С ФАЙЛАМИ ПРОЕКТОВ")).click();
        driver.findElement(By.xpath("//label[@for='beautiful2']")).click();
        driver.findElement(By.xpath("//label[@for='beautiful2']")).click();
        Assert.assertTrue(driver.findElement(By.id("beautiful2")).isSelected());
    }

    @Test
    public void test8() {
        //8. Столбцы диаграммы имеют разную ширину
        WebElement element = driver.findElement(By.className("graphs-details"));
        String bar1 = element.getCssValue("width");
        element = driver.findElement(By.className("graphs-errors"));
        String bar2 = element.getCssValue("width");
        element = driver.findElement(By.className("graphs-support"));
        String bar3 = element.getCssValue("width");
        element = driver.findElement(By.className("graphs-files"));
        String bar4 = element.getCssValue("width");
        Assert.assertTrue(bar1.equals(bar2) && bar1.equals(bar3) && bar1.equals(bar4));
    }

    @Test
    public void test10() {
        //10. Popup сообщение “Поздравляем, вы только что достигли 100500 уровень в тестировании!”
        driver.findElement(By.linkText("ВНИКАТЬ В ДЕТАЛИ ПРОЕКТОВ")).click();
        driver.findElement(By.linkText("СОПРОВОЖДАТЬ ПРОЕКТЫ")).click();
        driver.findElement(By.linkText("НАХОДИТЬ НЕСОВЕРШЕНСТВА")).click();
        driver.findElement(By.linkText("РАБОТАТЬ С ФАЙЛАМИ ПРОЕКТОВ")).click();
        driver.findElement(By.linkText("ВНИКАТЬ В ДЕТАЛИ ПРОЕКТОВ")).click();
        driver.findElement(By.linkText("НАХОДИТЬ НЕСОВЕРШЕНСТВА")).click();
        WebElement element = driver.findElement(By.className("egg"));
        boolean egg = element.getText().isEmpty();
        element.click();
        Assert.assertTrue(egg);
    }

    @AfterTest
    public void tearDown() {
        //Close the browser
        driver.quit();
    }
}
