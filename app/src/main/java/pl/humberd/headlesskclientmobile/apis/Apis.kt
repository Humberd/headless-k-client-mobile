package pl.humberd.headlesskclientmobile.apis

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import pl.humberd.headlesskclientmobile.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object Apis {
    val status: StatusApi = this.getApi()

    private inline fun <reified T> getApi(): T {
        return this.getRetrofit().create()
    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .create()

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor {
                val request = it.request()
                val newRequest = request.newBuilder().header("Authorization", BuildConfig.CLIENT_TOKEN)
                return@addInterceptor it.proceed(newRequest.build())
            }

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.SERVER_URL)
            .client(okHttpClientBuilder.build())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit
    }
//    val worker = getWorkerApi()
//    
//
//    private fun getWorkerApi(): WorkerAPI {
//        val gson = GsonBuilder()
//            .registerTypeAdapter(Instant::class.java, InstantTypeConverter)
//            .create()
//
//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:3231")
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
//            .addConverterFactory(GsonConverterFactory.create(gson))
//            .build()
//
////        return retrofit.create()
//        return object : WorkerAPI {
//            override fun getJobs(): Observable<List<Job>> {
//                return Observable.just(
//                    listOf(
//                        Job("work", Instant.now(), JobStatus.FAILED),
//                        Job("train", Instant.now(), JobStatus.SUCCESS),
//                        Job("fight", Instant.now(), JobStatus.UNKNOWN),
//                        Job("eat", Instant.now(), JobStatus.SUCCESS),
//                        Job("eat2", Instant.now(), JobStatus.SUCCESS),
//                        Job("eat2", Instant.now(), JobStatus.SUCCESS),
//                        Job("eat2", Instant.now(), JobStatus.SUCCESS),
//                        Job("buy", Instant.now(), JobStatus.SUCCESS)
//                    )
//                )
//            }
//        }
//    }
}
