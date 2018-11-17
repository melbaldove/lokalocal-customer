package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

import android.content.Context
import android.util.AttributeSet
import android.widget.ScrollView
import com.jakewharton.rxbinding2.view.RxView
import kotlinx.android.synthetic.main.onboarding_rib.view.*
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.model.RegistrationDetails

/**
 * Top level view for {@link OnboardingBuilder.OnboardingScope}.
 */
class OnboardingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ScrollView(context, attrs, defStyle), OnboardingInteractor.OnboardingPresenter {
    override fun register() = RxView.clicks(onboarding_rib_continue).map {
        RegistrationDetails(
                userName = onboarding_rib_username?.text.toString(),
                password = onboarding_rib_password?.text.toString(),
                firstName = onboarding_rib_firstName?.text.toString(),
                lastName = onboarding_rib_lastName?.text.toString(),
                qrId = "dummy"
        )
    }!!
}
