package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import base.BaseClass;

import java.io.File;
import java.io.FileWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CheckBoxTest extends BaseClass {

    public void checkBoxTest() throws Exception {

        driver.get("https://demoqa.com/checkbox");

        // increase timeout for page resources and dynamic elements
        WebDriverWait localWait = new WebDriverWait(driver, Duration.ofSeconds(25));

        JavascriptExecutor js = (JavascriptExecutor) driver;

        // Wait for full document ready state
        try {
            localWait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        } catch (Exception ignored) {
            // continue - sometimes readyState may not reach 'complete' but page elements can still be interacted with
        }

        // Remove known overlays/ads that block interactions (commonly #fixedban on demoqa)
        try {
            js.executeScript(
                    "let el = document.getElementById('fixedban'); if(el){el.remove();} let banners = document.querySelectorAll('.banner, .ads, .advert'); banners.forEach(b=>b.remove());");
        } catch (Exception ignored) {
        }

        // Try waiting for several candidate selectors for the tree container
        try {
            localWait.until(ExpectedConditions.or(
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rc-tree")),
                    ExpectedConditions.presenceOfElementLocated(By.id("tree-node")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rct-tree")),
                    ExpectedConditions.presenceOfElementLocated(By.cssSelector(".check-box-tree-wrapper"))
            ));
        } catch (Exception e) {
            // Dump page source and a screenshot to target/test-output with timestamp for debugging
            try {
                String ts = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                File outDir = new File("target/test-output/checkbox-debug");
                outDir.mkdirs();

                // page source
                File srcFile = new File(outDir, "page-" + ts + ".html");
                try (FileWriter fw = new FileWriter(srcFile)) {
                    fw.write((String) js.executeScript("return document.documentElement.outerHTML;"));
                }

                // screenshot
                if (driver instanceof TakesScreenshot) {
                    File scr = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileHandler.copy(scr, new File(outDir, "screenshot-" + ts + ".png"));
                }
            } catch (Exception dumpEx) {
                // ignore dump errors
            }

            throw new Exception("Checkbox tree container was not found on the page. Debug artifacts (page HTML and screenshot) were saved to target/test-output/checkbox-debug if possible.", e);
        }

        // Scroll page to make sure elements are in view
        try {
            js.executeScript("window.scrollBy(0,400)");
        } catch (Exception ignored) {
        }

        // Try multiple selectors to expand the "Home" node - break on first success
        String[] expandSelectors = new String[] {".rc-tree-switcher", ".rc-tree-switcher_close", ".rct-collapse-btn", ".rct-icon-expand-close", "button[aria-label='Expand all']"};
        boolean expanded = false;
        for (String sel : expandSelectors) {
            try {
                WebElement expandArrow = localWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(sel)));
                js.executeScript("arguments[0].click();", expandArrow);
                expanded = true;
                break;
            } catch (Exception ignored) {
            }
        }

        // If we didn't expand yet, try a fallback XPath for a visible button inside the tree
        if (!expanded) {
            try {
                WebElement expandArrow = localWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[contains(@class,'rc-tree')]//span[contains(@class,'rc-tree-switcher')]")));
                js.executeScript("arguments[0].click();", expandArrow);
            } catch (Exception ignored) {
                // last resort: continue, the checkboxes might already be visible/expanded
            }
        }

        // Wait briefly for checkbox input or generic checkbox span to appear
        localWait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rc-tree-checkbox")),
                ExpectedConditions.presenceOfElementLocated(By.id("tree-node-home")),
                ExpectedConditions.presenceOfElementLocated(By.cssSelector(".rct-checkbox"))
        ));

        // Try to click the actual checkbox - prefer rc-tree-checkbox for React Tree, fall back to rct-checkbox
        try {
            WebElement homeCheckbox = localWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".rc-tree-checkbox")));
            // use JS click to avoid interception issues
            js.executeScript("arguments[0].click();", homeCheckbox);
        } catch (Exception e) {
            try {
                WebElement inputCheckbox = localWait.until(ExpectedConditions.elementToBeClickable(By.id("tree-node-home")));
                js.executeScript("arguments[0].click();", inputCheckbox);
            } catch (Exception e2) {
                WebElement homeCheckbox = localWait.until(
                        ExpectedConditions.elementToBeClickable(By.cssSelector(".rct-checkbox")));
                js.executeScript("arguments[0].click();", homeCheckbox);
            }
        }

        System.out.println("Checkbox Test Executed");

        Thread.sleep(2000);
    }
}