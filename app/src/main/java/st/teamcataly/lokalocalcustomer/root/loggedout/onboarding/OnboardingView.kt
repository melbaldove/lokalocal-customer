package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ScrollView

/**
 * Top level view for {@link OnboardingBuilder.OnboardingScope}.
 */
class OnboardingView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) : ScrollView(context, attrs, defStyle), OnboardingInteractor.OnboardingPresenter
