package pl.humberd.headlesskclientmobile

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import org.ocpsoft.prettytime.PrettyTime
import java.util.*

class RecyclerViewAdapter(
    private val context: Context,
    private val jobs: List<Job>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    private val prettyTime = PrettyTime()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.job_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.d("RecycleViewAdapter", "onBindViewHolder: called");

        val job = jobs.get(position)

        Glide.with(context)
            .asBitmap()
            .load(job.status.src)
            .into(holder.jobStatus)

        holder.jobName.text = job.name

        val jobDate = Date.from(job.lastSuccess);
        holder.lastSuccess.text = "Last success: ${prettyTime.format(jobDate)}"

        holder.parentLayout.setOnClickListener {
            Toast.makeText(context, "Clicked ${job.name}", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return jobs.size;
    }

    companion object {
        val TAG = "RecyclerViewAdapter"
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val jobStatus: CircleImageView
        val jobName: TextView
        val lastSuccess: TextView
        val parentLayout: RelativeLayout

        init {
            jobStatus = itemView.findViewById(R.id.job_status)
            jobName = itemView.findViewById(R.id.job_name)
            lastSuccess = itemView.findViewById(R.id.job_last_success)
            parentLayout = itemView.findViewById(R.id.job_item_layout)
        }
    }

}
