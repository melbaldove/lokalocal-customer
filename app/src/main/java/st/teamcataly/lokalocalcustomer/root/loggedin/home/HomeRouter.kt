package st.teamcataly.lokalocalcustomer.root.loggedin.home

import android.view.ViewGroup
import com.uber.rib.core.Router
import st.teamcataly.lokalocalcustomer.root.loggedin.home.history.HistoryBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.home.history.HistoryRouter
import st.teamcataly.lokalocalcustomer.root.loggedin.home.qr.QRBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.home.qr.QRRouter

/**
 * Adds and removes children of {@link HomeBuilder.HomeScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class HomeRouter(
        interactor: HomeInteractor,
        component: HomeBuilder.Component,
        private val parentView: ViewGroup,
        private val historyBuilder: HistoryBuilder,
        private val qrBuilder: QRBuilder) : Router<HomeInteractor, HomeBuilder.Component>(interactor, component) {
    private var historyRouter: HistoryRouter? = null
    private var qrRouter: QRRouter? = null
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

    fun attachQr() {
        qrRouter = qrBuilder.build(parentView)
        parentView.addView(qrRouter?.view)
        attachChild(qrRouter)
    }

    fun detachQr() {
        qrRouter ?: return
        detachChild(qrRouter)
        parentView.removeView(qrRouter?.view)
        qrRouter = null
    }
}
