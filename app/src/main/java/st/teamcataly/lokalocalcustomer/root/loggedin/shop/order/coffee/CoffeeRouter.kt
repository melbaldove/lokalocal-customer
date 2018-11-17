package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee

import com.uber.rib.core.Router

/**
 * Adds and removes children of {@link CoffeeBuilder.CoffeeScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class CoffeeRouter(
    interactor: CoffeeInteractor,
    component: CoffeeBuilder.Component) : Router<CoffeeInteractor, CoffeeBuilder.Component>(interactor, component)
