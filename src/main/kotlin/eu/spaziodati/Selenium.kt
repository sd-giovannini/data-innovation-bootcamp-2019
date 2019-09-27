package eu.spaziodati

import io.ktor.http.Url
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.OutputType
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.support.ui.WebDriverWait
import org.slf4j.Logger
import java.io.File

fun initDriver(): ChromeDriver {
    val options = ChromeOptions()
    options.addArguments("headless")
    options.addArguments("window-size=1920x1080")
    return ChromeDriver(options)
}

fun <D> loadAndTakeScreenshot(driver: D, url: Url, logger: Logger): File where D : WebDriver, D : TakesScreenshot {
    logger.debug("Fetching '$url'")
    driver.get(url.toString())

    waitUntilPageIsReady(driver)

    return driver.getScreenshotAs(OutputType.FILE)
}


fun <D : WebDriver> waitUntilPageIsReady(driver: D) {
    val executor = driver as JavascriptExecutor
    WebDriverWait(driver, 1)
        .until { executor.executeScript("return document.readyState") == "complete" }
}