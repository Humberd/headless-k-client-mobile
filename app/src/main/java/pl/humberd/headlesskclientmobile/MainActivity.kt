package pl.humberd.headlesskclientmobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import pl.humberd.headlesskclientmobile.apis.Apis
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant

class MainActivity : AppCompatActivity() {
    val workerAPI = getWorkerApi()

    @SuppressLint("CheckResult")
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


        Apis.status.getStatus()
            .subscribe({
                Log.d(TAG, it.status);
            }, {
                Log.e(TAG, it.message)
            })

//        getToken();
    }

    fun getToken() {
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    Log.w(TAG, "getInstanceId failed", task.exception)
                    return@OnCompleteListener
                }

                // Get new Instance ID token
                val token = task.result?.token

                // Log and toast
                val msg = token
                Log.d(TAG, msg)
                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
            })
    }

    @SuppressLint("CheckResult")
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
                    Job("eat", Instant.now(), JobStatus.SUCCESS),
                    Job("eat2", Instant.now(), JobStatus.SUCCESS),
                    Job("eat2", Instant.now(), JobStatus.SUCCESS),
                    Job("eat2", Instant.now(), JobStatus.SUCCESS),
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
