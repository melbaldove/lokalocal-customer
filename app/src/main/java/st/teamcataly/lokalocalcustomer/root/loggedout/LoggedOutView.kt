package st.teamcataly.lokalocalcustomer.root.loggedout

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.loggedout_rib.view.*
import st.teamcataly.lokalocalcustomer.base.ErrorModel
import st.teamcataly.lokalocalcustomer.base.LoadingOptions
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginDetails
import st.teamcataly.lokalocalcustomer.util.DialogHelperImpl

/**
 * Top level view for {@link LoggedOutBuilder.LoggedOutScope}.
 */
class LoggedOutView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ConstraintLayout(context, attrs, defStyle), LoggedOutInteractor.LoggedOutPresenter {
    private lateinit var dialogHelper: DialogHelperImpl
    override fun login() = RxView.clicks(fragment_signin_tv1).map {
        LoginDetails(
                username = usernameText.text.toString(),
                password = passwordText.text.toString()
        )
    }

    override fun shouldShowLoading(loadingOptions: LoadingOptions) = dialogHelper.shouldShowLoading(loadingOptions)

    override fun showError(errorModel: ErrorModel) = dialogHelper.showError(errorModel)

    override fun register() = RxView.clicks(fragment_signin_register).map {}!!

    override fun onFinishInflate() {
        super.onFinishInflate()
        dialogHelper = DialogHelperImpl(this.context)

    }
}
