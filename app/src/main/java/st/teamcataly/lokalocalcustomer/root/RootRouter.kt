package st.teamcataly.lokalocalcustomer.root

import com.uber.rib.core.ViewRouter
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInRouter
import st.teamcataly.lokalocalcustomer.root.loggedout.LoggedOutBuilder
import st.teamcataly.lokalocalcustomer.root.loggedout.LoggedOutRouter

/**
 * Adds and removes children of {@link RootBuilder.RootScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class RootRouter(
        view: RootView,
        interactor: RootInteractor,
        component: RootBuilder.Component,
        private val loggedOutBuilder: LoggedOutBuilder,
        private val loggedInBuilder: LoggedInBuilder) : ViewRouter<RootView, RootInteractor, RootBuilder.Component>(view, interactor, component) {
    private var loggedInRouter: LoggedInRouter? = null
    private var loggedOutRouter: LoggedOutRouter? = null
    fun attachLoggedIn() {
        loggedInRouter = loggedInBuilder.build(view)
        view.addView(loggedInRouter?.view)
        attachChild(loggedInRouter)
    }

    fun detachLoggedIn() {
        loggedInRouter ?: return
        detachChild(loggedInRouter)
        view.removeView(loggedInRouter?.view)
        loggedInRouter = null
    }

    fun attachLoggedOut() {
        loggedOutRouter = loggedOutBuilder.build(view)
        view.addView(loggedOutRouter?.view)
        attachChild(loggedOutRouter)
    }

    fun detachLoggedOut() {
        loggedOutRouter ?: return
        detachChild(loggedOutRouter)
        view.removeView(loggedOutRouter?.view)
        loggedOutRouter = null
    }
}
