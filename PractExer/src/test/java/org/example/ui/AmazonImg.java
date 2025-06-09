package org.example.ui;

import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AmazonImg {

    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;
    private Set<String> urls;


    @FindBy(xpath = "//a[@class='a-carousel-goto-nextpage']")
    private WebElement nextImg;

    @BeforeClass
    public void setUpClass() {
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        actions = new Actions(driver);
        driver.manage().window().maximize();
        driver.get("https://www.amazon.in/");
        PageFactory.initElements(driver, this);
    }

    @SneakyThrows
    @Test()
    public void goToNextImage() {
        for(int i=0;i<6;i++){
            nextImg.click();
        }
    }
    @Test
    public void loadImage(){
        List<WebElement> elements = driver.findElements(By.xpath("//li[@class='a-carousel-card']//img"));
        urls=new LinkedHashSet<>();
        for(WebElement e: elements){
            String src=e.getAttribute("src");
            urls.add(src);
            System.out.println(src);
        }
    }
    @SneakyThrows
    @Test
    public void openImage(){
        for(String s: urls){
            driver.get(s);
            Robot robot = new Robot();
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_S);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            Thread.sleep(2000);
        }
    }

    @AfterClass
    public void tearDownClass() {
        driver.quit();
    }
}
