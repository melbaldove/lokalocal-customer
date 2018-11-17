package st.teamcataly.lokalocalcustomer

import android.app.Application
import timber.log.Timber

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class LokaLocalApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}