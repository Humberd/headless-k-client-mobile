package pl.humberd.headlesskclientmobile.apis

import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.PUT

interface TokensApi {

    @PUT("/tokens/")
    fun updateFcmToken(@Body body: FcmUpdateRequest): Observable<Any>

}

data class FcmUpdateRequest(
    val fcmToken: String
)
