package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order

import android.util.Log
import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.CoffeeInteractor
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Order
import timber.log.Timber
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
    private var total = 0.0

    @Inject
    lateinit var shop: Shop

    @Inject
    lateinit var shopRepository: ShopRepository
    private val disposables = CompositeDisposable()
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachCoffee()
        presenter.hide()
        presenter.buy().subscribe {
            if (orders.isEmpty()) return@subscribe
            shopRepository.order(shop.id, orders)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        Timber.d("THANKS FOR ORDERING")
                    }, {
                        Timber.e(it)
                    })

        }.addTo(disposables)
    }

    inner class CoffeeListener : CoffeeInteractor.Listener {
        override fun addItem(coffee: Coffee) {
            presenter.show()
            total += coffee.price
            presenter.setTotal(total.toString())
            if (orders.none { it.itemId == coffee.id }) {
                orders.add(Order(coffee.id, 1))
                Log.d("coffee", orders.toString())
                return
            }
            orders.first { it.itemId == coffee.id }.quantity++
            Log.d("coffee", orders.toString())
        }

        override fun removeItem(coffee: Coffee) {
            if (orders.none { it.itemId == coffee.id }) return
            val order = orders.first { it.itemId == coffee.id }
            total -= coffee.price
            presenter.setTotal(total.toString())
            if (order.quantity == 1) {
                orders.remove(order)
                if (orders.isEmpty()) {
                    presenter.hide()
                }
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
        fun buy(): Observable<Unit>
        fun setTotal(total: String)
        fun show()
        fun hide()
    }
}
