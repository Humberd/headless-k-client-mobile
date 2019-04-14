package pl.humberd.headlesskclientmobile

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.ProgressBar

enum class WorkerStatfus(
    val icon: Int,
    val color: String,
    val message: String
) {
    OK(R.drawable.ic_check_circle_black_24dp, "#00ff00", "Worker performs OK"),
    FATAL_ERROR(R.drawable.ic_error_black_24dp, "#ff00ff", "Worker has been shut down and is waiting for intervention"),
    NO_CONNECTION_WITH_WORKER(R.drawable.ic_phonelink_erase_black_24dp, "#757575", "Cannot connect to the Worker"),
    NO_CONNECTION_WITH_BACKEND(R.drawable.ic_phonelink_off_black_24dp, "#757575", "Cannot connecto to the Backend")
}

class MainActivity : AppCompatActivity() {
    lateinit var workerStatusIcon: ImageView;
    lateinit var workerStatusProgressBar: ProgressBar;

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        toolbar = findViewById(R.id.myToolbar)
//        setSupportActionBar(toolbar)

        workerStatusIcon = findViewById(R.id.workerStatusIcon)
        workerStatusProgressBar = findViewById(R.id.workerStatusProgressBar)


//        swiperefresh.isRefreshing = true
//        swiperefresh.setOnRefreshListener {
//            getJobs {
//                initRecyclerView(it)
//            }
//        }
//
//        getJobs {
//            initRecyclerView(it)
//        }
//
//
//        Apis.status.getStatus()
//            .subscribe({
//                Log.d(TAG, it.status);
//            }, {
//                Log.e(TAG, it.message)
//            })

    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_items, menu)
//        return true
//    }
//
//    fun getToken() {
//        FirebaseInstanceId.getInstance().instanceId
//            .addOnCompleteListener(OnCompleteListener { task ->
//                if (!task.isSuccessful) {
//                    Log.w(TAG, "getInstanceId failed", task.exception)
//                    return@OnCompleteListener
//                }
//
//                // Get new Instance ID token
//                val token = task.result?.token
//
//                // Log and toast
//                val msg = token
//                Log.d(TAG, msg)
//                Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
//            })
//    }
//
//    @SuppressLint("CheckResult")
//    fun getJobs(success: (List<Job>) -> Any) {
//        workerAPI.getJobs()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                success.invoke(it!!)
//            }, {
//                Log.e(TAG, "Some error occured ${it.message}")
//            }, {
//                swiperefresh.isRefreshing = false
//            })
//    }
//
//    companion object {
//        val TAG = "MainActivity"
//    }
//
//    private fun getWorkerApi(): WorkerAPI {
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
//
//    private fun initRecyclerView(jobs: List<Job>) {
//        val adapter = RecyclerViewAdapter(this, jobs)
//        findViewById<RecyclerView>(R.id.recycler_view).let {
//            it.adapter = adapter
//            it.layoutManager = LinearLayoutManager(this)
//        }
//    }
}
