package eu.spaziodati

import com.amazonaws.services.s3.transfer.TransferManagerBuilder
import io.ktor.http.Url
import org.slf4j.Logger
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val BUCKET = "archiveitems"

fun getKey(atokaId: String, domain: String, timestamp: String): String {
    return "dev/data-innovation-bootcamp/website-screenshots/$atokaId/$domain/$timestamp.png"
}


fun upload(atokaId: String, url: Url, imageFile: File, logger: Logger) {
    val domain = url.host

    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.BASIC_ISO_DATE
    val formatted = current.format(formatter)

    val s3TransferManager = TransferManagerBuilder.defaultTransferManager()

    val key = getKey(atokaId, domain, formatted)

    s3TransferManager.upload(BUCKET, key, imageFile)

    logger.debug("Uploading screenshot to s3://$BUCKET/$key")
}