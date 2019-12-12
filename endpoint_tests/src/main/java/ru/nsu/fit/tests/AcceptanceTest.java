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
public class AcceptanceTest {
    private Browser browser = null;

    @BeforeClass
    public void beforeClass() {
        browser = BrowserService.openNewBrowser();
    }

    @Test
    @Title("Create customer")
    @Description("Create customer via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void createCustomer() {
        // login to admin cp
        browser.openPage("http://localhost:8080/");
        browser.waitForElement(By.id("email"));

        browser.getElement(By.id("email")).sendKeys("admin");
        browser.getElement(By.id("password")).sendKeys("setup");

        browser.getElement(By.id("login")).click();

        // create customer
        browser.getElement(By.id("add_new_customer")).click();

        browser.getElement(By.id("first_name_id")).sendKeys("John");
        browser.getElement(By.id("last_name_id")).sendKeys("Weak");
        browser.getElement(By.id("email_id")).sendKeys("email@example.com");
        browser.getElement(By.id("password_id")).sendKeys("strongpass");

        browser.getElement(By.id("create_customer_id")).click();
    }

    @Test(dependsOnMethods = "createCustomer")
    @Title("Check login")
    @Description("Try to login")
    @Severity(SeverityLevel.CRITICAL)
    @Features("Customer feature")
    public void checkCustomer() {
        // login to admin cp
        browser.openPage("http://localhost:8080/");
        browser.waitForElement(By.id("email"));

        browser.getElement(By.id("email")).sendKeys("email@example.com");
        browser.getElement(By.id("password")).sendKeys("strongpass");

        browser.getElement(By.id("login")).click();
    }

    @Test(dependsOnMethods = "checkCustomer")
    @Title("Check void password")
    @Description("Check void password via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void createExistingCustomer() {
        // login to admin cp
        browser.openPage("http://localhost:8080/customers.html");
        browser.waitForElement(By.id("add_new_customer"));

        // create customer
        browser.getElement(By.id("add_new_customer")).click();

        browser.getElement(By.id("first_name_id")).sendKeys("John");
        browser.getElement(By.id("last_name_id")).sendKeys("Weak");
        browser.getElement(By.id("email_id")).sendKeys("email@example.com");
        browser.getElement(By.id("password_id")).sendKeys("strongpass");

        browser.getElement(By.id("create_customer_id")).click();

        try {
            Alert alert = browser.getWebDriver().switchTo().alert();
            Assert.assertEquals(alert.getText(), "Customer with this email is existing");
            alert.accept();
        } catch (NoAlertPresentException e) {
            e.printStackTrace();
        }
    }

    @Test(dependsOnMethods = "createExistingCustomer")
    @Title("Delete existing customer")
    @Description("Delete existing customer via UI API")
    @Severity(SeverityLevel.BLOCKER)
    @Features("Customer feature")
    public void deleteExistingCustomer() {
        // login to admin cp
        browser.openPage("http://localhost:8080/customers.html");
        browser.waitForElement(By.id("delete_customer"));

        // create customer
        browser.getElement(By.id("delete_customer")).click();

        browser.getElement(By.id("del_email_id")).sendKeys("email@example.com");

        browser.getElement(By.id("delete_customer_id")).click();
    }


//
//    @Test
//    @Title("Check incorrect login")
//    @Description("Check incorrect login via UI API")
//    @Severity(SeverityLevel.BLOCKER)
//    @Features("Customer feature")
//    public void checkLogin() {
//        browser.openPage("http://localhost:8080/");
//        browser.waitForElement(By.id("email"));
//
//        browser.getElement(By.id("email")).sendKeys("admin");
//        browser.getElement(By.id("password")).sendKeys("sep");
//
//        browser.getElement(By.id("login")).click();
//
//        try {
//            Alert alert = browser.getWebDriver().switchTo().alert();
//            Assert.assertEquals(alert.getText(), "Email or password is incorrect");
//            alert.accept();
//        } catch (NoAlertPresentException e) {
//
//        }
//    }
//
//    @Test
//    @Title("Check void login")
//    @Description("Check void login via UI API")
//    @Severity(SeverityLevel.BLOCKER)
//    @Features("Customer feature")
//    public void checkVoidLogin() {
//        browser.openPage("http://localhost:8080/");
//        browser.waitForElement(By.id("email"));
//
//        browser.getElement(By.id("password")).sendKeys("sepsfg");
//
//        browser.getElement(By.id("login")).click();
//
//        try {
//            Alert alert = browser.getWebDriver().switchTo().alert();
//            Assert.assertEquals(alert.getText(), "Email is empty");
//            alert.accept();
//        } catch (NoAlertPresentException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    @Title("Check void password")
//    @Description("Check void password via UI API")
//    @Severity(SeverityLevel.BLOCKER)
//    @Features("Customer feature")
//    public void checkVoidPassword() {
//        browser.openPage("http://localhost:8080/");
//        browser.waitForElement(By.id("email"));
//
//        browser.getElement(By.id("email")).sendKeys("admin");
//
//        browser.getElement(By.id("login")).click();
//
//        try {
//            Alert alert = browser.getWebDriver().switchTo().alert();
//            Assert.assertEquals(alert.getText(), "Password is empty");
//            alert.accept();
//        } catch (NoAlertPresentException e) {
//            e.printStackTrace();
//        }
//    }

    @AfterClass
    public void afterClass() {
        if (browser != null)
            browser.close();
    }
}
