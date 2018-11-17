package st.teamcataly.lokalocalcustomer.root.loggedin

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Top level view for {@link LoggedInBuilder.LoggedInScope}.
 */
class LoggedInView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : View(context, attrs, defStyle), LoggedInInteractor.LoggedInPresenter
