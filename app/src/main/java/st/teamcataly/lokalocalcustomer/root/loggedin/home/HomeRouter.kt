package st.teamcataly.lokalocalcustomer.root.loggedin.home

import android.view.ViewGroup
import com.uber.rib.core.Router
import st.teamcataly.lokalocalcustomer.root.loggedin.home.history.HistoryBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.home.history.HistoryRouter

/**
 * Adds and removes children of {@link HomeBuilder.HomeScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class HomeRouter(
        interactor: HomeInteractor,
        component: HomeBuilder.Component,
        private val parentView: ViewGroup,
        private val historyBuilder: HistoryBuilder) : Router<HomeInteractor, HomeBuilder.Component>(interactor, component) {
    private var historyRouter: HistoryRouter? = null
    fun attachHistory() {
        historyRouter = historyBuilder.build(parentView)
        parentView.addView(historyRouter?.view)
        attachChild(historyRouter)
    }

    fun detachHistory() {
        historyRouter ?: return
        detachChild(historyRouter)
        parentView.removeView(historyRouter?.view)
        historyRouter = null
    }
}
