package org.example.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestDragAndDrop {
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions actions;

    @FindBy(id = "trash")
    private WebElement trashBin;
    @FindBy(xpath = "//iframe[@class='demo-frame lazyloaded']")
    private WebElement iframe;

    @BeforeClass
    public void setUpClass() {
        driver= new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        actions=new Actions(driver);
        driver.manage().window().maximize();
        driver.get("https://www.globalsqa.com/demo-site/draganddrop/#google_vignette");
        PageFactory.initElements(driver, this);
    }
    @Test
    public void testDragAndDrop() {
        driver.switchTo().frame(iframe);
        wait.until(ExpectedConditions.visibilityOf(trashBin));
        for(int i=1;i<5;i++){
            WebElement dragEle=driver.findElement(By.xpath("(//li[@class='ui-widget-content ui-corner-tr ui-draggable ui-draggable-handle'])["+i+"]"));
            wait.until(ExpectedConditions.visibilityOf(dragEle));
            Actions actions1 = actions.dragAndDrop(dragEle, trashBin);
            actions1.perform();
        }
    }

    @AfterClass
    public void tearDownClass() {
        driver.quit();
    }
}
