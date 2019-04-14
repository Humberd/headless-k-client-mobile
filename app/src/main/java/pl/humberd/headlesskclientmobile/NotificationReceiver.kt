package pl.humberd.headlesskclientmobile

import android.app.Service
import android.content.Intent
import android.os.IBinder

class NotificationReceiver : Service() {

    override fun onBind(intent: Intent): IBinder {
        TODO("Return the communication channel to the service.")
    }
}
