package st.teamcataly.lokalocalcustomer.root.loggedout

import android.view.ViewGroup
import com.uber.rib.core.ViewRouter
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.OnboardingBuilder
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.OnboardingRouter

/**
 * Adds and removes children of {@link LoggedOutBuilder.LoggedOutScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class LoggedOutRouter(
        view: LoggedOutView,
        interactor: LoggedOutInteractor,
        component: LoggedOutBuilder.Component,
        private val loggedOutParent: ViewGroup,
        private val onboardingBuilder: OnboardingBuilder) : ViewRouter<LoggedOutView, LoggedOutInteractor, LoggedOutBuilder.Component>(view, interactor, component) {
    private var onboardingRouter: OnboardingRouter? = null
    fun attachOnboarding() {
        onboardingRouter = onboardingBuilder.build(loggedOutParent)
        loggedOutParent.addView(onboardingRouter?.view)
        attachChild(onboardingRouter)
    }
    fun detachOnBoarding() {
        onboardingRouter ?: return
        detachChild(onboardingRouter)
        loggedOutParent.removeView(onboardingRouter?.view)
        onboardingRouter = null
    }
}
