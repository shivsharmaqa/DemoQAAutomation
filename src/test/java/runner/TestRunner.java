package runner;

import base.BaseClass;
import tests.CheckBoxTest;
import tests.LoginTest;
import tests.RadioButtonTest;
import tests.TextBoxTest;

public class TestRunner {

    public static void main(String[] args) throws Exception {

        BaseClass base = new BaseClass();

        base.setupBrowser();

        // Run Tests

        LoginTest login = new LoginTest();
        login.loginTest();

        TextBoxTest text = new TextBoxTest();
        text.textBoxTest();

        CheckBoxTest check = new CheckBoxTest();
        check.checkBoxTest();

        RadioButtonTest radio = new RadioButtonTest();
        radio.radioButtonTest();

        // Close Browser

        base.closeBrowser();

        System.out.println("All Tests Completed");
    }
}