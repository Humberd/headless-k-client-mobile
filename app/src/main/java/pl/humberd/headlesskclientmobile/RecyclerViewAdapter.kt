package pl.humberd.headlesskclientmobile

import android.annotation.SuppressLint
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import org.ocpsoft.prettytime.PrettyTime
import pl.humberd.headlesskclientmobile.apis.JobStatusDto
import java.util.*

class RecyclerViewAdapter(
    private val jobs: List<JobStatusDto>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val prettyTime = PrettyTime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "RestrictedApi")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.d("RecycleViewAdapter", "onBindViewHolder: called");

        val job = jobs.get(position)
        holder.name.text = job.name
        holder.timeInterval.text = "${job.timeInterval / 1000 / 60} min"
        holder.lastSuccess.text = if (job.lastSuccess === null) {
            ""
        } else {
            prettyTime.format(Date(job.lastSuccess))
        }

        holder.lastCheck.text = if (job.lastCheck === null) {
            ""
        } else {
            prettyTime.format(Date(job.lastCheck))
        }

        val color = "#ffffff"

        holder.parentLayout.setBackgroundColor(Color.parseColor("#ffffff"))

//        when (job.status) {
//            JobStatus.SUCCESS,
//            JobStatus.ALREADY_DONE ->
//        }

//        Glide.with(context)
//            .asBitmap()
//            .load(job.status.src)
//            .into(holder.jobStatus)
//
//        holder.jobName.text = job.name
//
//        val jobDate = Date.from(job.lastSuccess);
//        holder.lastSuccess.text = "Last success: ${prettyTime.format(jobDate)}"
//
//        holder.parentLayout.setOnClickListener {
//            Toast.makeText(context, "Clicked ${job.name}", Toast.LENGTH_LONG).show()
//        }
    }

    override fun getItemCount(): Int {
        return jobs.size;
    }

    companion object {
        val TAG = "RecyclerViewAdapter"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.job_name)
        val timeInterval: TextView = itemView.findViewById(R.id.time_interval)
        val lastSuccess: TextView = itemView.findViewById(R.id.job_last_success)
        val lastCheck: TextView = itemView.findViewById(R.id.job_last_check)
        val parentLayout: RelativeLayout = itemView.findViewById(R.id.job_item_layout)
    }

}
