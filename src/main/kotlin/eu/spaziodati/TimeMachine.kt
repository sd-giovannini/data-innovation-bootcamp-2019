package eu.spaziodati

import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.http.Url
import io.ktor.response.respond
import io.ktor.routing.Routing
import io.ktor.routing.get
import org.openqa.selenium.TakesScreenshot
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.slf4j.Logger


fun Application.main() {
    install(DefaultHeaders)
    install(CallLogging)
    install(Routing) {
        get("/manual") {
            val logger = call.application.environment.log

            // TODO CHECK url params
            val url = Url(call.parameters["url"]!!.trim('"', ' '))
            val atokaId = call.parameters["atokaId"]!!


            processUrls(atokaId, listOf(url), logger)

            call.respond("Ok")
        }

        get("/company/{atokaId}") {
            val logger = call.application.environment.log
            val atokaId = call.parameters["atokaId"]

            val websites = fetchWebsites(atokaId!!, logger)

            processUrls(atokaId, websites, logger)

            call.respond("Ok")
        }
    }
}

fun processUrls(atokaId: String, urls: List<Url>, logger: Logger) {
    val driver: ChromeDriver = initDriver()

    urls.forEach { url ->
        process(driver, atokaId, url, logger)
    }

    driver.close()
}

fun <D> process(driver: D, atokaId: String, url: Url, logger: Logger) where D : WebDriver, D : TakesScreenshot {
    val tmpFile = loadAndTakeScreenshot(driver, url, logger)

    upload(atokaId, url, tmpFile, logger)
}

