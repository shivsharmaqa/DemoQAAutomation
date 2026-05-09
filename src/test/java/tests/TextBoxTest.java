package tests;

import org.openqa.selenium.By;

import base.BaseClass;

public class TextBoxTest extends BaseClass {

    public void textBoxTest() throws Exception {

        driver.get("https://demoqa.com/text-box");

        driver.findElement(By.id("userName"))
                .sendKeys("Shiv");

        driver.findElement(By.id("userEmail"))
                .sendKeys("shiv@gmail.com");

        driver.findElement(By.id("currentAddress"))
                .sendKeys("India");

        driver.findElement(By.id("permanentAddress"))
                .sendKeys("Rajasthan");

        driver.findElement(By.id("submit"))
                .click();

        System.out.println("Text Box Test Executed");

        Thread.sleep(2000);
    }
}