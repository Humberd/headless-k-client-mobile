package pl.humberd.headlesskclientmobile.fragments

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import pl.humberd.headlesskclientmobile.R
import pl.humberd.headlesskclientmobile.apis.Apis
import pl.humberd.headlesskclientmobile.apis.StatusDto
import java.util.concurrent.TimeUnit

enum class WorkerStatusEnum(
    val icon: Int,
    val color: String,
    val message: String,
    var errorMessage: String = ""
) {

    OK(R.drawable.ic_check_circle_black_24dp, "#00ff00", "Worker performs OK"),
    FATAL_ERROR(R.drawable.ic_error_black_24dp, "#ff00ff", "Worker has been shut down."),
    WORKER_DISCONNECTED(R.drawable.ic_phonelink_erase_black_24dp, "#757575", "Worker Disconnected"),
    BACKEND_DISCONNECTED(R.drawable.ic_phonelink_off_black_24dp, "#757575", "Backend Disconnected"),
    UNKNOWN_STATUS(R.drawable.ic_battery_unknown_black_24dp, "#757575", "Unknown response:");

    fun displayToast(context: Context?) {
        val printableMessage = "${message} ${errorMessage}"
        Toast.makeText(context, printableMessage, Toast.LENGTH_LONG)
            .show()
    }
}

class WorkerStatusFragment : Fragment() {
    val refreshSubject = PublishSubject.create<Any>()
    val destroySubject = PublishSubject.create<Any>()

    lateinit var workerStatusIcon: ImageView;
    lateinit var workerStatusProgressBar: ProgressBar;

    private lateinit var currentStatus: WorkerStatusEnum;

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workerStatusIcon = view.findViewById(R.id.workerStatusIcon)
        workerStatusProgressBar = view.findViewById(R.id.workerStatusProgressBar)

        workerStatusIcon.setOnClickListener {
            currentStatus.displayToast(context)
        }

        workerStatusIcon.setOnLongClickListener {
            refresh()
            return@setOnLongClickListener true
        }

        subscribe()
        refresh()
    }

    override fun onDestroyView() {
        super.onDestroyView()

        destroySubject.onNext(true);
    }

    fun subscribe(): Disposable {
        return refreshSubject
            .takeUntil(destroySubject)
            .switchMap { Observable.interval(0, 20, TimeUnit.SECONDS) }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnNext {
                Log.d(TAG, "Request trigger")
                workerStatusProgressBar.visibility = View.VISIBLE
                workerStatusIcon.visibility = View.GONE
            }
            .switchMap {
                Apis.status.getStatus()
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnNext { onStatusReceive(it) }
                    .doOnError { onStatusError(it) }
                    .onErrorResumeNext(Observable.empty())
            }
            .subscribe({
                Log.d(TAG, "Request SUCCESS")
            }, {
                Log.e(TAG, it.message)
            })
    }

    fun refresh() {
        refreshSubject.onNext(true)
    }

    fun onStatusReceive(statusDto: StatusDto) {
        when (statusDto.status.toUpperCase()) {
            "OK" -> updateStatus(WorkerStatusEnum.OK)
            "FATAL_ERROR" -> {
                val status = WorkerStatusEnum.FATAL_ERROR
                status.errorMessage = if (statusDto.message !== null) statusDto.message else ""
                updateStatus(status)
            }
            "DISCONNECTED" -> updateStatus(WorkerStatusEnum.WORKER_DISCONNECTED)
            else -> {
                val status = WorkerStatusEnum.UNKNOWN_STATUS
                status.errorMessage = statusDto.status
                updateStatus(status)

                status.displayToast(context)
            }
        }
    }

    fun onStatusError(error: Throwable) {
        val status = WorkerStatusEnum.BACKEND_DISCONNECTED;
        val errMessage = error.message
        status.errorMessage = if (errMessage !== null) errMessage else "No error message"

        updateStatus(status)

        status.displayToast(context)

        Log.e(TAG, error.message)
    }

    fun updateStatus(status: WorkerStatusEnum) {
        currentStatus = status;
        workerStatusProgressBar.visibility = View.GONE
        workerStatusIcon.also {
            it.visibility = View.VISIBLE
            it.setColorFilter(Color.parseColor(status.color))
            it.setImageResource(status.icon)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_worker_status, container, false)
    }


    companion object {
        val TAG = "WorkerStatus"
    }
}
