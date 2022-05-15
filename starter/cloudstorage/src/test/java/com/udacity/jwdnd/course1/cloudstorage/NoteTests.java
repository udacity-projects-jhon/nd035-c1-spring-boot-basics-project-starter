package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
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
public class NoteTests {

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
            "creates a note and verifies that the note details are visible in the note list.")
    public void test1() {
        var userName = Double.toString(Math.random());
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        UserUtilities.doMockSignUp("URL","Test",userName,"123", driver, this.port);
        UserUtilities.doLogIn(userName, "123", driver, this.port);

        var note = new Note();
        note.setNoteTitle("Udacity Project");
        note.setNoteDescription("Finish the project");

        addNewNote(webDriverWait, note);

        // get firstNote
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        var userTable = driver.findElement(By.id("userTable"));
        var rows = userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        var firstNote = rows.get(0).findElements(By.tagName("td"));

        var noteTitleActual = firstNote.get(1).getText();
        var noteDescriptionActual = firstNote.get(2).getText();


        assertEquals(1, rows.size());
        assertEquals(note.getNoteTitle(), noteTitleActual);
        assertEquals(note.getNoteDescription(), noteDescriptionActual);
    }

    @Test
    @DisplayName("Write a Selenium test that logs in an existing user with existing notes, " +
            "clicks the edit note button on an existing note, changes the note data, saves the changes, " +
            "and verifies that the changes appear in the note list.")
    public void test2() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        UserUtilities.doMockSignUp("URL","Test","UT2","123", driver, this.port);
        UserUtilities.doLogIn("UT2", "123", driver, this.port);

        var initialNote = new Note();
        initialNote.setNoteTitle("Udacity Project");
        initialNote.setNoteDescription("Finish the project");

        addNewNote(webDriverWait, initialNote);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        driver.findElement(By.id("userTable")).findElement(By.tagName("a")).click();

        var editedNote = new Note();
        editedNote.setNoteTitle("Udacity Coursera");
        editedNote.setNoteDescription("- Put the certification on linkedin");

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleDriver = driver.findElement(By.id("note-title"));
        noteTitleDriver.clear();
        noteTitleDriver.click();
        noteTitleDriver.sendKeys(editedNote.getNoteTitle());

        WebElement noteDescriptionDriver = driver.findElement(By.id("note-description"));
        noteDescriptionDriver.click();
        noteDescriptionDriver.clear();
        noteDescriptionDriver.sendKeys(editedNote.getNoteDescription());

        driver.findElement(By.id("noteSubmit")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        driver.findElement(By.id("nav-notes-tab")).click();

        // get firstNote
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        var userTable = driver.findElement(By.id("userTable"));
        var rows = userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));
        var firstNote = rows.get(0).findElements(By.tagName("td"));

        var noteTitleActual = firstNote.get(1).getText();
        var noteDescriptionActual = firstNote.get(2).getText();

        assertEquals(1, rows.size());
        assertEquals(editedNote.getNoteTitle(), noteTitleActual);
        assertEquals(editedNote.getNoteDescription(), noteDescriptionActual);
    }

    @Test
    @DisplayName("Write a Selenium test that logs in an existing user with existing notes, " +
            "clicks the delete note button on an existing note, " +
            "and verifies that the note no longer appears in the note list.")
    public void test3() {
        WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
        UserUtilities.doMockSignUp("URL","Test","UT3","123", driver, this.port);
        UserUtilities.doLogIn("UT3", "123", driver, this.port);

        var initialNote = new Note();
        initialNote.setNoteTitle("Udacity Project");
        initialNote.setNoteDescription("Finish the project");

        addNewNote(webDriverWait, initialNote);

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        driver.findElement(By.id("userTable")).findElement(By.tagName("form")).submit();

        driver.findElement(By.id("nav-notes-tab")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("userTable")));
        var userTable = driver.findElement(By.id("userTable"));
        var rows = userTable.findElement(By.tagName("tbody")).findElements(By.tagName("tr"));

        assertEquals(0, rows.size());
    }

    private void addNewNote(WebDriverWait webDriverWait, Note note) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
        driver.findElement(By.id("nav-notes-tab")).click();
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-note-button")));
        driver.findElement(By.id("add-note-button")).click();

        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
        WebElement noteTitleDriver = driver.findElement(By.id("note-title"));
        noteTitleDriver.click();
        noteTitleDriver.sendKeys(note.getNoteTitle());

        WebElement noteDescriptionDriver = driver.findElement(By.id("note-description"));
        noteDescriptionDriver.click();
        noteDescriptionDriver.sendKeys(note.getNoteDescription());

        driver.findElement(By.id("noteSubmit")).submit();
        driver.findElement(By.id("nav-notes-tab")).click();
    }

}
