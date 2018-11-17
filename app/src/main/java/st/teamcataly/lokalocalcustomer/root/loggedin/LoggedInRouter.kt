package st.teamcataly.lokalocalcustomer.root.loggedin

import com.uber.rib.core.ViewRouter
import st.teamcataly.lokalocalcustomer.root.loggedin.home.HomeBuilder
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.OnboardingRouter

/**
 * Adds and removes children of {@link LoggedInBuilder.LoggedInScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class LoggedInRouter(
        view: LoggedInView,
        interactor: LoggedInInteractor,
        component: LoggedInBuilder.Component,
        private val homeBuilder: HomeBuilder) : ViewRouter<LoggedInView, LoggedInInteractor, LoggedInBuilder.Component>(view, interactor, component) {

}
