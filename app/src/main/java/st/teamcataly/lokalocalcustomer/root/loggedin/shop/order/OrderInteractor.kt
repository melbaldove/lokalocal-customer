package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order

import android.util.Log
import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.disposables.CompositeDisposable
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.CoffeeInteractor
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Order
import javax.inject.Inject

/**
 * Coordinates Business Logic for [OrderScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class OrderInteractor : Interactor<OrderInteractor.OrderPresenter, OrderRouter>() {
    @Inject
    lateinit var presenter: OrderPresenter

    private var orders: MutableList<Order> = mutableListOf()
    private val disposables = CompositeDisposable()
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachCoffee()
    }

    inner class CoffeeListener : CoffeeInteractor.Listener {
        override fun addItem(id: String) {
            if (orders.none { it.id == id }) {
                orders.add(Order(id, 1))
                Log.d("coffee", orders.toString())
                return
            }
            orders.first { it.id == id }.quantity++
            Log.d("coffee", orders.toString())
        }

        override fun removeItem(id: String) {
            if (orders.none { it.id == id }) return
            val order = orders.first { it.id == id }

            if (order.quantity == 1) {
                orders.remove(order)
                Log.d("coffee", orders.toString())
                return
            }

            order.quantity--
            Log.d("coffee", orders.toString())
        }

    }

    override fun willResignActive() {
        super.willResignActive()

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

    interface OrderPresenter {

    }
}
