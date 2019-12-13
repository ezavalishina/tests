package ru.nsu.fit.tests;

import org.apache.xpath.operations.String;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import ru.nsu.fit.services.HttpClientUtils;
import ru.nsu.fit.services.browser.Browser;
import ru.nsu.fit.services.browser.BrowserService;
import ru.yandex.qatools.allure.annotations.Description;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Severity;
import ru.yandex.qatools.allure.annotations.Title;
import ru.yandex.qatools.allure.model.SeverityLevel;

import javax.ws.rs.core.Response;

/**
 * @author Timur Zolotuhin (tzolotuhin@gmail.com)
 */
public class UITests {
    private Browser browser = null;

    @BeforeClass
    public void beforeClass() {
        browser = BrowserService.openNewBrowser();
    }

    @Test
    @Title("Delete not existing customer")
    @Description("Delete not existing customer via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void deleteNotExistingCustomer() {
        Browser browser = BrowserService.openNewBrowser();
        browser.openPage("http://localhost:8080/customers.html");
        browser.waitForElement(By.id("delete_customer"));

        // create customer
        browser.getElement(By.id("delete_customer")).click();

        browser.getElement(By.id("del_email_id")).sendKeys("emal@example.com");

        browser.getElement(By.id("delete_customer_id")).click();

        try {
            Alert alert = browser.getWebDriver().switchTo().alert();
            Assert.assertEquals(alert.getText(), "Customer with this email is not existing");
            alert.accept();
            browser.close();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
            browser.close();
        }
    }


    @Test
    @Title("Check incorrect login")
    @Description("Check incorrect login via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void checkIncorrectLogin() {
        Browser browser = BrowserService.openNewBrowser();

        browser.openPage("http://localhost:8080/");
        browser.waitForElement(By.id("email"));

        browser.getElement(By.id("email")).sendKeys("adsain");
        browser.getElement(By.id("password")).sendKeys("sejdjp");

        browser.getElement(By.id("login")).click();

        try {
            Alert alert = browser.getWebDriver().switchTo().alert();
            Assert.assertEquals(alert.getText(), "Customer with this login is not exist");
            alert.accept();
            browser.close();
        } catch (NoAlertPresentException e) {
            browser.close();
        }
    }

    @Test
    @Title("Check void login")
    @Description("Check void login via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void checkVoidLogin() {
        Browser browser = BrowserService.openNewBrowser();

        browser.openPage("http://localhost:8080/");
        browser.waitForElement(By.id("email"));

        browser.getElement(By.id("password")).sendKeys("sepsfg");

        browser.getElement(By.id("login")).click();

        try {
            Alert alert = browser.getWebDriver().switchTo().alert();
            Assert.assertEquals(alert.getText(), "Email is empty");
            alert.accept();
            browser.close();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
            browser.close();
        }
    }

    @Test
    @Title("Check void password")
    @Description("Check void password via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void checkVoidPassword() {
        Browser browser = BrowserService.openNewBrowser();

        browser.openPage("http://localhost:8080/");
        browser.waitForElement(By.id("email"));

        browser.getElement(By.id("email")).sendKeys("admin");

        browser.getElement(By.id("login")).click();

        try {
            Alert alert = browser.getWebDriver().switchTo().alert();
            Assert.assertEquals(alert.getText(), "Password is empty");
            alert.accept();
            browser.close();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
            browser.close();
        }
    }

    @AfterClass
    public void afterClass() {
        if (browser != null)
            browser.close();
    }
}
