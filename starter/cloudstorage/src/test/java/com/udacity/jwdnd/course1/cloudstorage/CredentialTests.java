package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.util.UserUtilities;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CredentialTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Write a Selenium test that logs in an existing user, " +
            "creates a credential and verifies that the credential details are visible in the credential list.")
    public void test1() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        UserUtilities.doMockSignUp("URL","Test","UT","123", driver, this.port);
        UserUtilities.doLogIn("UT", "123", driver, this.port);

        var credential = new Credential();
        credential.setUrl("https://nordpass.com/");
        credential.setUsername("spiritedaway");
        credential.setPassword("Fj4ck8wKGdeCVh6R");

        addNewCredential(webDriverWait, credential);

        // get firstCredential
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        var userTable = driver.findElement(By.id("credentialTable"));
        var rows = userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        var firstCredential = rows.get(0).findElements(By.tagName("td"));

        var urlActual = firstCredential.get(1).getText();
        var userNameActual = firstCredential.get(2).getText();

        assertEquals(1, rows.size());
        assertEquals(credential.getUrl(), urlActual);
        assertEquals(credential.getUsername(), userNameActual);
    }


    @Test
    @DisplayName("Write a Selenium test that logs in an existing user with existing credentials, " +
            "clicks the edit credential button on an existing credential, changes the credential data, " +
            "saves the changes, and verifies that the changes appear in the credential list.")
    public void test2() {
        var userName = Double.toString(Math.random());
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        UserUtilities.doMockSignUp("URL","Test",userName,"123", driver, this.port);
        UserUtilities.doLogIn(userName, "123", driver, this.port);

        var credential = new Credential();
        credential.setUrl("https://nordpass.com/");
        credential.setUsername("spiritedaway");
        credential.setPassword("Fj4ck8wKGdeCVh6R");

        addNewCredential(webDriverWait, credential);

        // change
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        driver.findElement(By.id("credentialTable")).findElement(By.tagName("a")).click();

        var newCredential = new Credential();
        newCredential.setUrl("https://github.com/");
        newCredential.setUsername("romanholiday");
        newCredential.setPassword("Fj4ck8wKGdeCVh6R");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        var credentialUrl = driver.findElement(By.id("credential-url"));
        credentialUrl.clear();
        credentialUrl.click();
        credentialUrl.sendKeys(newCredential.getUrl());

        var credentialUsername = driver.findElement(By.id("credential-username"));
        credentialUsername.click();
        credentialUsername.clear();
        credentialUsername.sendKeys(newCredential.getUsername());

        var credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.clear();
        credentialPassword.sendKeys(newCredential.getPassword());

        driver.findElement(By.id("credentialSubmit")).click();
        driver.findElement(By.tagName("a")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        driver.findElement(By.id("nav-credentials-tab")).click();

        // get firstCredential
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        var rows = driver.findElement(By.id("credentialTable"))
                .findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        var firstCredential = rows.get(0).findElements(By.tagName("td"));

        var credentialUrlActual = firstCredential.get(1).getText();
        var credentialUsernameActual = firstCredential.get(2).getText();

        assertEquals(1, rows.size());
        assertEquals(newCredential.getUrl(), credentialUrlActual);
        assertEquals(newCredential.getUsername(), credentialUsernameActual);
    }

    @Test
    @DisplayName("Write a Selenium test that logs in an existing user with existing credentials, " +
            "clicks the delete credential button on an existing credential, and verifies that " +
            "the credential no longer appears in the credential list.")
    public void test3() {
        var userName = Double.toString(Math.random());
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        UserUtilities.doMockSignUp("URL","Test",userName,"123", driver, this.port);
        UserUtilities.doLogIn(userName, "123", driver, this.port);

        var credential = new Credential();
        credential.setUrl("https://nordpass.com/");
        credential.setUsername("spiritedaway");
        credential.setPassword("Fj4ck8wKGdeCVh6R");

        addNewCredential(webDriverWait, credential);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        driver.findElement(By.id("credentialTable")).findElement(By.tagName("form")).submit();

        driver.findElement(By.tagName("a")).click();
        driver.findElement(By.id("nav-credentials-tab")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentialTable")));
        var credentialTable = driver.findElement(By.id("credentialTable"));
        var rows = credentialTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        assertEquals(0, rows.size());
    }

    private void addNewCredential(WebDriverWait webDriverWait, Credential credential) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
        driver.findElement(By.id("nav-credentials-tab")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-credential-button")));
        driver.findElement(By.id("add-credential-button")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
        WebElement noteTitleDriver = driver.findElement(By.id("credential-url"));
        noteTitleDriver.click();
        noteTitleDriver.sendKeys(credential.getUrl());

        WebElement credentialUserName = driver.findElement(By.id("credential-username"));
        credentialUserName.click();
        credentialUserName.sendKeys(credential.getUsername());

        WebElement credentialPassword = driver.findElement(By.id("credential-password"));
        credentialPassword.click();
        credentialPassword.sendKeys(credential.getPassword());

        driver.findElement(By.id("credentialSubmit")).submit();
        driver.findElement(By.tagName("a")).click();
        driver.findElement(By.id("nav-credentials-tab")).click();
    }
}
