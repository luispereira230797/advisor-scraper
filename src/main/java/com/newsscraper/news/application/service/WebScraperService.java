package com.newsscraper.news.application.service;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.env.Environment;

import java.util.Objects;

@Service("webScraperService")
public class WebScraperService {
    @Autowired
    private Environment env;

    public String getWebContent(String url) throws Exception {
        try {
            System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(env.getProperty("CHROMEDRIVER_PATH")));
            var options = new ChromeOptions();
            options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200",
                    "--ignore-certificate-errors", "--silent", "--no-sandbox", "--disable-dev-shm-usage");
            options.setBinary(env.getProperty("CUSTOM_GOOGLE_CHROME_BIN"));
            var driver = new ChromeDriver(options);

            // Get the login page
            driver.get(url);

            // Wait until the div with queryly_item_title class exist
            var wait = new WebDriverWait(driver, 10); // Wait up to 10 seconds
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className("queryly_item_title")));

            // Get page content
            var response =  driver.getPageSource();
            driver.quit();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
