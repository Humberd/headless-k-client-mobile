package pl.humberd.headlesskclientmobile.apis

import android.util.Log
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pl.humberd.headlesskclientmobile.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


object Apis {
    private val TAG = "APIS";
    val status: StatusApi = this.getApi()
    val tokens: TokensApi = this.getApi()
    val jobStatus: JobStatusApi = this.getApi()

    private inline fun <reified T> getApi(): T {
        return this.getRetrofit().create()
    }

    private fun getRetrofit(): Retrofit {
        val gson = GsonBuilder()
            .create()

        val logging = HttpLoggingInterceptor()
// set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClientBuilder = OkHttpClient.Builder()
            .addInterceptor(logging)
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

        Log.d(TAG, "SERVER_URL: ${BuildConfig.SERVER_URL}")
        Log.d(TAG, "CLIENT_TOKEN: ${BuildConfig.CLIENT_TOKEN}")

        return retrofit
    }
}
