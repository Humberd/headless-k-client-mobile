package pl.humberd.headlesskclientmobile.apis

import io.reactivex.Observable
import retrofit2.http.GET

interface StatusApi {

    @GET("/status/")
    fun getStatus(): Observable<StatusDto>

}

data class StatusDto(
    val version: String,
    val status: String
)
