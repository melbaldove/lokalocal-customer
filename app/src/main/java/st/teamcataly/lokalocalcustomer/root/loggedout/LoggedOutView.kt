package st.teamcataly.lokalocalcustomer.root.loggedout

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.loggedout_rib.view.*

/**
 * Top level view for {@link LoggedOutBuilder.LoggedOutScope}.
 */
class LoggedOutView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle), LoggedOutInteractor.LoggedOutPresenter {
    override fun register() = RxView.clicks(fragment_signin_register).map {}!!
}
