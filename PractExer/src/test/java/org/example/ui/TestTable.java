package org.example.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings({"unused"})
public class TestTable {
    private WebDriver driver;
    private WebDriverWait wait;
    @FindBy(xpath = "//div[@class='ih-pt-fb ng-binding' and count(.//span[@class='rf W ih-pt-g'])=5]//ancestor::tr//h2[@class='ih-pt-cont mb-0 ng-binding']")
    private WebElement team5wins;

    @BeforeTest
    public void setup() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(8));
        PageFactory.initElements(driver, this);
        driver.manage().window().maximize();
        driver.get("https://www.iplt20.com/points-table/men/2024");
    }

    @Test
    public void verifyTableHeaders() {
        wait.until(ExpectedConditions.visibilityOf(team5wins));
        System.out.println(team5wins.getText());
        assert team5wins.getText().contains("RCB");
    }

    @Test
    public void verifyTableRows() {
        int idf=0;
        for (int i = 1; i < 11; i++) {
            WebElement row = driver.findElement(By.xpath("//tr[@class='team0 ng-scope']"));
                wait.until(ExpectedConditions.visibilityOf(row));
                System.out.print(row.findElement(By.xpath("(//tr[@class='team0 ng-scope']//td[@class='table-qualified ng-binding ng-scope'])["+i+"]")).getText()+"  ");
                System.out.print(row.findElement(By.xpath("(//tr[@class='team0 ng-scope']//h2[@class='ih-pt-cont mb-0 ng-binding'])["+i+"]")).getText()+"  ");
                for(int k=1;k<8;k++){
                    System.out.print(row.findElement(By.xpath("(//tr[@class='team0 ng-scope']//td[@class='ng-binding'])["+(k+idf+"]"))).getText()+"  ");
                }
                idf=idf+7;
                System.out.print(row.findElement(By.xpath("(//tr[@class='team0 ng-scope']//td[@class='bt ng-binding'])["+i+"]")).getText()+"  ");
                System.out.println();

        }
    }
    @Test
    public void verifyTableTotalPoints() {
        List<String> points = List.of( "Element 3", "Element 4","Element 1", "Element 2");
        String name=" Krishna Hare";
        String collected = Arrays.stream(name.split(" ")).collect(Collectors.collectingAndThen(Collectors.toList(), (list) -> {
            Collections.reverse(list);
            return list;
        })).stream().collect(Collectors.joining(" "));
        points.stream().sorted((x,y)->x.compareToIgnoreCase(y)).forEach(System.out::println);
        System.out.println(collected);
    }

    @AfterClass
    public void teardown() {
        driver.quit();
    }
}