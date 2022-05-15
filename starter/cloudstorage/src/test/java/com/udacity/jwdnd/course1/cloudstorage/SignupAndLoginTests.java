package com.udacity.jwdnd.course1.cloudstorage;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignupAndLoginTests {

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
    @DisplayName("should verify that the home page is not accessible without logging in.")
    public void test1() {
        driver.get("http://localhost:" + this.port + "/home");
        assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }

    @Test
    @DisplayName("should sign up a new user, logs that user in, verifies that they can access the home page, " +
            "then logs out and verifies that the home page is no longer accessible.")
    public void test2() {
        var userName = Double.toString(Math.random());
        UserUtilities.doMockSignUp("URL","Test",userName,"123", driver, this.port);
        UserUtilities.doLogIn(userName, "123", driver, this.port);

        assertEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());

        WebElement loginButton = driver.findElement(By.id("logout-button"));
        loginButton.click();

        assertNotEquals("http://localhost:" + this.port + "/home", driver.getCurrentUrl());
        assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
    }


}
