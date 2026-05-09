package tests;

import org.openqa.selenium.By;

import base.BaseClass;

public class RadioButtonTest extends BaseClass {

    public void radioButtonTest() throws Exception {

        driver.get("https://demoqa.com/radio-button");

        driver.findElement(By.xpath("//label[@for='yesRadio']"))
                .click();

        System.out.println("Radio Button Test Executed");

        Thread.sleep(2000);
    }
}