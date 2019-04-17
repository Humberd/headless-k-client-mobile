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
                val newRequest = request.newBuilder().header("Authorization", "abc")
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
}
