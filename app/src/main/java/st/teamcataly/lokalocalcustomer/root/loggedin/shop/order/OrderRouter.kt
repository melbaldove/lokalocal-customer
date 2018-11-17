package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order

import com.uber.rib.core.ViewRouter
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.CoffeeBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.CoffeeRouter

/**
 * Adds and removes children of {@link OrderBuilder.OrderScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class OrderRouter(
        view: OrderView,
        interactor: OrderInteractor,
        component: OrderBuilder.Component,
        private val coffeeBuilder: CoffeeBuilder) : ViewRouter<OrderView, OrderInteractor, OrderBuilder.Component>(view, interactor, component) {
    private var coffeeRouter: CoffeeRouter? = null
    fun attachCoffee() {
        coffeeRouter = coffeeBuilder.build()
        attachChild(coffeeRouter)
    }
}
