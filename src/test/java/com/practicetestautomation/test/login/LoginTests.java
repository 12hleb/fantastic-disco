package com.practicetestautomation.test.login;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginTests {

    private WebDriver driver;
    private Logger logger;

    @BeforeMethod(alwaysRun = true)
    @Parameters("browser")
    public void setup(@Optional("chrome") String browser) {

        logger = Logger.getLogger(LoginTests.class.getName());
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
        driver = new ChromeDriver();
        driver.get("https://practicetestautomation.com/practice-test-login/");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        driver.quit();
    }

    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test(groups = {"positive", "regression", "smoke"})
    public void testLoginFunctionality()   {
        logger.info("Starting testLoginFunctionality");
// Type username student into Username field
        WebElement userNameField = driver.findElement(By.id("username"));
        userNameField.sendKeys("student");
// Type password Password123 into Password field
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");
// Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
// Verify new page URL contains practicetestautomation.com/logged-in-successfully/
        String expectUrl = "https://practicetestautomation.com/logged-in-successfully/";
        String actualUrl = driver.getCurrentUrl();
        Assert.assertEquals(actualUrl, expectUrl);
// Verify new page contains expected text ('Congratulations' or 'successfully logged in')
        logger.info("Verify Logged In Successfully");
        String expectText = "Logged In Successfully";
        String pageSource = driver.getPageSource();
        Assert.assertTrue(pageSource.contains(expectText));
// Verify button Log out is displayed on the new page
        logger.info("Assertion for log out");
        WebElement logOutButton = driver.findElement(By.linkText("Log out"));
        Assert.assertTrue(logOutButton.isDisplayed());
    }
    @Test(groups = {"negotive", "regression"})
    public void incorrectUserNameTest() {
//Type username incorrectUser into Username field
        WebElement userNameField = driver.findElement(By.id("username"));
        userNameField.sendKeys("student123");
// Type password Password123 into Password field
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("Password123");
//Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
//Verify error message is displayed
        WebElement errorMessage = driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());
//Verify error message text is Your username is invalid!
        String actualText = errorMessage.getText();
        String expectedText = "Your username is invalid!";
        Assert.assertEquals(actualText,expectedText);
        driver.quit();
    }
    @Test(groups = {"neootive", "regression"})
    public void incorrectPasswordTest() {
//Type username student into Username field
        WebElement userNameField = driver.findElement(By.id("username"));
        userNameField.sendKeys("student");
//Type password incorrectPassword into Password field
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys("PasswordIncor");
//Push Submit button
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();

        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
//Verify error message is displayed
        WebElement errorMessage = driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());
//Verify error message text is Your password is invalid!
        String actualText = errorMessage.getText();
        String expectedText = "Your password is invalid!";
        Assert.assertEquals(actualText,expectedText);
        driver.quit();
    }

    @Parameters({"username", "password", "expectedErrorMessage"})
    @Test(groups = {"neootive", "regression"})
    public void negativeLoginTest(String username, String password, String expectedErrorMessage)   {
// Type username student into Username field
        logger.info("Input username");
        WebElement userNameField = driver.findElement(By.id("username"));
        userNameField.sendKeys(username);
// Type password Password123 into Password field
        logger.info("Input password");
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(password);
// Push Submit button
        logger.info("click submit");
        WebElement submitButton = driver.findElement(By.id("submit"));
        submitButton.click();
        //Verify error message is displayed
        try{
            Thread.sleep(2000);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }
        logger.info("Verify error message is displayed");
        WebElement errorMessage = driver.findElement(By.id("error"));
        Assert.assertTrue(errorMessage.isDisplayed());
//Verify error message text is Your username is invalid!
        logger.info("Verify error message text");
        String actualText = errorMessage.getText();
        Assert.assertEquals(actualText,expectedErrorMessage);
        driver.quit();
    }
}
