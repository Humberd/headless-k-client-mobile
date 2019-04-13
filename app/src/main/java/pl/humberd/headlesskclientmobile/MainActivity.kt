package pl.humberd.headlesskclientmobile

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant

class MainActivity : AppCompatActivity() {
    val workerAPI = getWorkerApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swiperefresh.isRefreshing = true

        swiperefresh.setOnRefreshListener {
            getJobs {
                initRecyclerView(it)
            }
        }

        getJobs {
            initRecyclerView(it)
        }
    }

    fun getJobs(success: (List<Job>) -> Any) {
        workerAPI.getJobs()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                success.invoke(it!!)
            }, {
                Log.e(TAG, "Some error occured ${it.message}")
            }, {
                swiperefresh.isRefreshing = false
            })
    }

    companion object {
        val TAG = "MainActivity"
    }

    private fun getWorkerApi(): WorkerAPI {
        val gson = GsonBuilder()
            .registerTypeAdapter(Instant::class.java, InstantTypeConverter)
            .create()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3231")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

//        return retrofit.create()
        return object : WorkerAPI {
            override fun getJobs(): Observable<List<Job>> {
                return Observable.just(listOf(
                    Job("work", Instant.now(), JobStatus.FAILED),
                    Job("train", Instant.now(), JobStatus.SUCCESS),
                    Job("fight", Instant.now(), JobStatus.UNKNOWN),
                    Job("buy", Instant.now(), JobStatus.SUCCESS)
                ))
            }
        }
    }

    private fun initRecyclerView(jobs: List<Job>) {
        val adapter = RecyclerViewAdapter(this, jobs)
        findViewById<RecyclerView>(R.id.recycler_view).let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(this)
        }
    }
}
