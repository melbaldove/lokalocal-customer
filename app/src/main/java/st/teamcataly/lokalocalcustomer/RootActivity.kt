package st.teamcataly.lokalocalcustomer

import android.view.ViewGroup
import com.uber.rib.core.RibActivity
import com.uber.rib.core.ViewRouter
import st.teamcataly.lokalocalcustomer.root.RootBuilder
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import st.teamcataly.lokalocalcustomer.util.BackPressService
import java.util.concurrent.LinkedBlockingDeque

class RootActivity : RibActivity(), AndroidEventsService {

    private val backpressListeners = LinkedBlockingDeque<BackPressService.Listener>()
    override fun addBackPressListener(listener: BackPressService.Listener) {
        backpressListeners.add(listener)
    }

    override fun removeBackPressListener(listener: BackPressService.Listener) {
        backpressListeners.remove(listener)
    }

    override fun onBackPressed() {
        backpressListeners.descendingIterator().forEach {
            if (it.onBackPressed())
                return
        }
        super.onBackPressed()
    }

    override fun createRouter(parentViewGroup: ViewGroup): ViewRouter<*, *, *> {
        return RootBuilder(object : RootBuilder.ParentComponent {}).build(parentViewGroup,
                androidEventsService = this)
    }
}
