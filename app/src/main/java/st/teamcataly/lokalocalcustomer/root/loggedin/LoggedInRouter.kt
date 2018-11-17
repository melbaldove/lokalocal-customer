package st.teamcataly.lokalocalcustomer.root.loggedin

import com.uber.rib.core.ViewRouter
import st.teamcataly.lokalocalcustomer.root.loggedin.home.HomeBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.home.HomeRouter
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopRouter

/**
 * Adds and removes children of {@link LoggedInBuilder.LoggedInScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class LoggedInRouter(
        view: LoggedInView,
        interactor: LoggedInInteractor,
        component: LoggedInBuilder.Component,
        private val homeBuilder: HomeBuilder,
        private val shopBuilder: ShopBuilder) : ViewRouter<LoggedInView, LoggedInInteractor, LoggedInBuilder.Component>(view, interactor, component) {
    private var homeRouter: HomeRouter? = null
    private var shopRouter: ShopRouter? = null
    fun attachHome() {
        homeRouter = homeBuilder.build()
        attachChild(homeRouter)
    }

    fun detachHome() {

    }

    fun attachShop() {
        shopRouter = shopBuilder.build()
        attachChild(shopRouter)
    }
}
