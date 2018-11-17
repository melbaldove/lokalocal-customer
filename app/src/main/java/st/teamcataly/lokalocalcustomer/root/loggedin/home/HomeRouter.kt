package st.teamcataly.lokalocalcustomer.root.loggedin.home

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link HomeBuilder.HomeScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class HomeRouter(
    view: HomeView,
    interactor: HomeInteractor,
    component: HomeBuilder.Component) : ViewRouter<HomeView, HomeInteractor, HomeBuilder.Component>(view, interactor, component)
