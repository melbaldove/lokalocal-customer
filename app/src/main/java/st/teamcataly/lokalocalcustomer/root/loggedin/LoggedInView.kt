package st.teamcataly.lokalocalcustomer.root.loggedin

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import com.airbnb.epoxy.EpoxyRecyclerView

/**
 * Top level view for {@link LoggedInBuilder.LoggedInScope}.
 */
class LoggedInView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : EpoxyRecyclerView(context, attrs, defStyle), LoggedInInteractor.LoggedInPresenter {
    override fun onFinishInflate() {
        super.onFinishInflate()

    }
}
