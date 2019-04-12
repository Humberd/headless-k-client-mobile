package pl.humberd.headlesskclientmobile

import io.reactivex.Observable
import retrofit2.http.GET

interface WorkerAPI {

    @GET("/")
    fun getJobs(): Observable<List<Job>>
}

