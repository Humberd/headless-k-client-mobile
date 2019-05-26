package pl.humberd.headlesskclientmobile.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.android.schedulers.AndroidSchedulers
import pl.humberd.headlesskclientmobile.R
import pl.humberd.headlesskclientmobile.RecyclerViewAdapter
import pl.humberd.headlesskclientmobile.apis.Apis
import pl.humberd.headlesskclientmobile.apis.JobStatusDto

class JobsListFragment : Fragment() {
    lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_jobs_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        swipeRefresh = view.findViewById(R.id.swiperefresh)
        swipeRefresh.setOnRefreshListener {
            getJobsList()
        }

        getJobsList()
    }

    @SuppressLint("CheckResult")
    private fun getJobsList() {
        swipeRefresh.isRefreshing = true

        Apis.jobStatus.getJobStatusList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                initRecyclerView(it)
            }, {
                Log.e(TAG, "Some error occures ${it.message}")
            }, {
                swipeRefresh.isRefreshing = false
            })

    }

    private fun initRecyclerView(jobsList: List<JobStatusDto>) {
        val adapter = RecyclerViewAdapter(jobsList)
        view!!.findViewById<RecyclerView>(R.id.job_list_recycle_view).let {
            it.adapter = adapter
            it.layoutManager = LinearLayoutManager(context)
        }
    }

    companion object {
        val TAG = "JobsListFragment"
    }
}
