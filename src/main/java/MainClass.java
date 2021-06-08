import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Locatable;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;


public class MainClass {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver",
                "C:\\Users\\LenaFedorinchik\\IdeaProjects\\FindAutoOnliner\\Drivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        driver.manage().window().maximize();

        driver.get("https://www.onliner.by/");

        WebElement baraholka =  driver.findElement(By.xpath("//span[@class = \"b-main-navigation__text\" and text()=\"Автобарахолка\"]"));
        Actions action = new Actions(driver);
        action.moveToElement(baraholka).build().perform();

        driver.findElement(By.xpath("//span[text()=\"Opel\"]")).click();

        driver.findElement(By.xpath("//div[@class = \"vehicle-form__line\"]")).click();
        driver.findElement(By.xpath("//div[text() = \"С пробегом\"]")).click();

        WebElement cost = driver.findElement(By.xpath("//input[@placeholder=\"до\"]"));
        cost.click();
        cost.sendKeys("15000");

        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("scroll(0, 250);");

        //select model
        SelectOption(driver,"//div[@class = \"input-style__faux\" and text()=\"Модель\" ]//following :: div ",
        "//div[@class = \"dropdown-style__checkbox-sign\" and text()=\"Corsa\" ]");

        //select year
        SelectOption(driver,"//div[@class = \"input-style__faux\" and text()=\"с\" ]//following::select",
        "//option[@value = \"2005\" ]");


        //select body's type
        SelectElement(driver, "Тип кузова", "Хетчбэк");

      //select type of engine
        SelectElement(driver, "Тип двигателя", "Бензин");

        jse.executeScript("scroll(250, 1000);");
        //select gearbox
        String gearbox = "//div[text() = \"Автоматическая\"]//parent::div//preceding-sibling::div[@class=\"i-checkbox__faux\"]"+
                "//parent::div//child::input[@class=\"i-checkbox__real\"]";
//        System.out.println(gearbox);
//        driver.findElement(By.xpath(gearbox)).click();

        SelectElement2(driver, "Объем двигателя", "от","1");
        SelectElement2(driver, "Объем двигателя", "до","2.6");
    }

    public static void SelectOption(WebDriver driver, String listName, String value)
    {
        driver.findElement(By.xpath(listName)).click();
        driver.findElement(By.xpath(value)).click();
    }

    public static void SelectElement(WebDriver driver, String parentName, String value)
    {
        String name = String.format(
        "//div[@class = \"vehicle-form__label-title\" and text()=\"%s\" ]"+
                "// ancestor::div[@class=\"vehicle-form__label vehicle-form__label_base\"]"+
                "// following-sibling::div[@class=\"vehicle-form__field\"]"+
                "//descendant::div[@class = \"input-style__real\"]",parentName
        );
        WebElement parent = driver.findElement(By.xpath(name));
        parent.click();

        String elementName = String.format("//div[text() = \"%s\" ]",value);
        driver.findElement(By.xpath(elementName)).click();

        parent.click();
    }

    public static void SelectElement2(WebDriver driver, String parentName, String childName,String value)
    {
        String name = String.format(
                "//div[@class = \"vehicle-form__label-title\" and text()=\"%s\" ]"+
                        "// ancestor::div[@class=\"vehicle-form__label vehicle-form__label_base\"]"+
                        "// following-sibling::div[@class=\"vehicle-form__field\"]"+
                        "//descendant::div[@class = \"input-style__faux\" and text()=\"%s\"]"+
                "//following-sibling::select[@class = \"input-style__real\"]",parentName, childName
        );

        System.out.println(name);
        WebElement parent = driver.findElement(By.xpath(name));
        parent.click();

        String elementName = String.format("//option[@value = \"%s\" ]",value);
        System.out.println(elementName);
        driver.findElement(By.xpath(elementName)).click();

        //parent.click();
    }
}
