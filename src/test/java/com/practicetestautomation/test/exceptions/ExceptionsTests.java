package com.practicetestautomation.test.exceptions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ExceptionsTests {

    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {

        logger = Logger.getLogger(ExceptionsTests.class.getName());
        logger.setLevel(Level.INFO);

        logger.info("Running test in " + browser);
        switch (browser.toLowerCase()) {
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            default:
                logger.warning("Tests will be run in Chrome, configuration for " + browser + " is missing");
                driver = new ChromeDriver();
                break;
        }
        //Open page
        driver.get("https://practicetestautomation.com/practice-test-exceptions/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Close browser");
        driver.quit();
    }

    @Test
    public void noSuchElementExceptionTest (){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        //Verify Row 2 input field is displayed
        WebElement row2InputField = driver.findElement(By.xpath("//div[@id='row2']/input"));
        Assert.assertTrue(row2InputField.isDisplayed(), "Row input 2 is not displayed");
    }
    @Test
    public void TimeoutExceptionTest (){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        //Verify Row 2 input field is displayed
        WebElement row2InputField = driver.findElement(By.xpath("//div[@id='row2']/input"));
        Assert.assertTrue(row2InputField.isDisplayed(), "Row input 2 is not displayed");
    }

    @Test
    public void ElementNotInteractableExceptionTest(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Click Add button
        WebElement addButton = driver.findElement(By.id("add_btn"));
        addButton.click();
        //Wait for the second row to load
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@id='row2']/input")));
        //Type text into the second input field
        WebElement row2InputField = driver.findElement(By.xpath("//div[@id='row2']/input"));
        Assert.assertTrue(row2InputField.isDisplayed(), "Row input 2 is not displayed");

        row2InputField.sendKeys("Kebab");

        //Push Save button using locator By.name(“Save”)
        WebElement saveButton = driver.findElement(By.xpath("//div[@id='row2']/button[@name='Save']"));
        saveButton.click();

        //Verify text saved
        WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("confirmation")));
        String actualMessage = successMessage.getText();
        String expectedMessage = "Row 2 was saved";

        Assert.assertEquals(actualMessage, expectedMessage, "Message is not expected");
    }


}
