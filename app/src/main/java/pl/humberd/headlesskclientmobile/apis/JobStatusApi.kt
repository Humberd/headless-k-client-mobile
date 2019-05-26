package pl.humberd.headlesskclientmobile.apis

import io.reactivex.Observable
import retrofit2.http.GET


interface JobStatusApi {

    @GET("/jobs/")
    fun getJobStatusList(): Observable<List<JobStatusDto>>
}

data class JobStatusDto(
    val id: String,
    val name: String,
    val status: JobStatus,
    val timeInterval: Long,
    val lastCheck: Long? = null,
    val lastError: Long? = null,
    val lastSuccess: Long? = null
)

enum class JobStatus {
    ALREADY_DONE,
    ERROR,
    SUCCESS
}
