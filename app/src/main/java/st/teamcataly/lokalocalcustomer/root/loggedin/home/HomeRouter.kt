package st.teamcataly.lokalocalcustomer.root.loggedin.home

import com.uber.rib.core.Router

/**
 * Adds and removes children of {@link HomeBuilder.HomeScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class HomeRouter(
    interactor: HomeInteractor,
    component: HomeBuilder.Component) : Router<HomeInteractor, HomeBuilder.Component>(interactor, component)
