package pl.humberd.headlesskclientmobile

import android.content.Context
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import org.ocpsoft.prettytime.PrettyTime
import pl.humberd.headlesskclientmobile.apis.JobStatus
import pl.humberd.headlesskclientmobile.apis.JobStatusDto
import java.util.*

class RecyclerViewAdapter(
    private val context: Context,
    private val jobs: List<JobStatusDto>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val prettyTime = PrettyTime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.d("RecycleViewAdapter", "onBindViewHolder: called");

        val job = jobs.get(position)
        holder.name.text = job.name
        holder.lastSuccess.text = if (job.lastSuccess === null) {
            "---"
        } else {
            prettyTime.format(Date(job.lastSuccess))
        }

        holder.lastCheck.text = if (job.lastCheck === null) {
            "---"
        } else {
            prettyTime.format(Date(job.lastCheck))
        }

        if (job.status === JobStatus.ERROR) {
            holder.parentLayout.setBackgroundResource(R.color.errorBackgroundColor)
        }

        holder.status.setImageResource(
            when (job.status) {
                JobStatus.SUCCESS,
                JobStatus.ALREADY_DONE -> R.drawable.ic_check_black_24dp
                JobStatus.ERROR -> R.drawable.ic_close_black_24dp
            }
        )
        holder.status.setColorFilter(
            ResourcesCompat.getColor(
                context.resources,
                when (job.status) {
                    JobStatus.SUCCESS,
                    JobStatus.ALREADY_DONE -> R.color.successColor
                    JobStatus.ERROR -> R.color.errorColor
                }, null
            )
        )


    }

    override fun getItemCount(): Int {
        return jobs.size;
    }

    companion object {
        val TAG = "RecyclerViewAdapter"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val status: ImageView = itemView.findViewById(R.id.job_status_icon)
        val name: TextView = itemView.findViewById(R.id.job_name)
        val lastSuccess: TextView = itemView.findViewById(R.id.job_last_success)
        val lastCheck: TextView = itemView.findViewById(R.id.job_last_check)
        val parentLayout: RelativeLayout = itemView.findViewById(R.id.job_item_layout)
    }

}
