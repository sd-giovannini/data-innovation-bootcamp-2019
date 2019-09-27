package eu.spaziodati

import io.ktor.http.Url
import org.json.JSONObject
import org.slf4j.Logger


fun fetchWebsites(atokaId: String, logger: Logger): List<Url> {
    val token = System.getenv("ATOKEN")

    val res = khttp.get(
        url = "https://api.atoka.io/v2/companies",
        params = mapOf("token" to token, "packages" to "web", "ids" to atokaId)

    )

    val obj: JSONObject = res.jsonObject

    val websites = obj.getJSONArray("items")
        ?.getJSONObject(0)
        ?.getJSONObject("web")
        ?.getJSONArray("websites")?.map { i: Any -> (i as JSONObject).getString("url") }.orEmpty()

    logger.debug("Company $atokaId has $websites")

    return websites.map { url -> Url(url) }

}