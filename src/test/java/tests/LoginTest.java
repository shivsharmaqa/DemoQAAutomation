package tests;

import org.openqa.selenium.By;

import base.BaseClass;

public class LoginTest extends BaseClass {

    public void loginTest() throws Exception {

        driver.get("https://demoqa.com/login");

        driver.findElement(By.id("userName"))
                .sendKeys("testuser");

        driver.findElement(By.id("password"))
                .sendKeys("Test@123");

        driver.findElement(By.id("login"))
                .click();

        System.out.println("Login Test Executed");

        Thread.sleep(2000);
    }
}