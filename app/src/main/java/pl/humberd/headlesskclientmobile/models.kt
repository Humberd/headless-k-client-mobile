package pl.humberd.headlesskclientmobile

import java.time.Instant

data class Job(
    val name: String,
    val lastSuccess: Instant,
    val status: JobStatus
)

enum class JobStatus(
    val src: String
) {
    SUCCESS("https://cdn3.iconfinder.com/data/icons/social-messaging-ui-color-line/254000/172-512.png"),
    FAILED("https://cdn4.iconfinder.com/data/icons/generic-interaction/143/close-x-cross-multiply-delete-cancel-modal-error-no-512.png"),
    UNKNOWN("https://cdn1.iconfinder.com/data/icons/rounded-flat-country-flag-collection-1/2000/_Unknown.png")
}
