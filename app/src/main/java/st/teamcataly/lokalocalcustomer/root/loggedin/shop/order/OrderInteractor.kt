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
import st.teamcataly.lokalocalcustomer.base.BasePresenter
import st.teamcataly.lokalocalcustomer.base.ErrorModel
import st.teamcataly.lokalocalcustomer.base.LoadingOptions
import st.teamcataly.lokalocalcustomer.root.TransactionRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.CoffeeInteractor
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Order
import timber.log.Timber
import javax.inject.Inject
import kotlin.math.roundToInt

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
    lateinit var transactionRepository: TransactionRepository
    @Inject
    lateinit var shopRepository: ShopRepository
    private val disposables = CompositeDisposable()
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachCoffee()
        reset()
        presenter.buy().subscribe {
            presenter.shouldShowLoading(LoadingOptions(isLoading = true, message = "Placing your order..."))
            if (orders.isEmpty()) return@subscribe
            shopRepository.order(shop.id, orders)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnEvent {
                        presenter.shouldShowLoading(LoadingOptions(isLoading = false))
                    }
                    .subscribe({
                        orders.forEach {
                            transactionRepository.newTransaction(it.coffee, it.quantity, shop.partnerName)
                        }
                        reset()
                        router.detachCoffee()
                        router.attachCoffee()
                        presenter.success()

                        Timber.d("THANKS FOR ORDERING")
                    }, {
                        presenter.showError(ErrorModel(message = it.localizedMessage))
                        Timber.e(it)
                    }).addTo(disposables)
        }.addTo(disposables)
    }

    inner class CoffeeListener : CoffeeInteractor.Listener {
        override fun addItem(coffee: Coffee) {
            presenter.show()
            total += coffee.price
            presenter.setTotal(total.roundToInt().toString())
            if (orders.none { it.coffee == coffee }) {
                orders.add(Order(coffee, 1))
                Log.d("coffee", orders.toString())
                return
            }
            orders.first { it.coffee == coffee }.quantity++
            Log.d("coffee", orders.toString())
        }

        override fun removeItem(coffee: Coffee) {
            if (orders.none { it.coffee == coffee }) return
            val order = orders.first { it.coffee == coffee }
            total -= coffee.price
            presenter.setTotal(total.roundToInt().toString())
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

    private fun reset() {
        total = 0.0
        presenter.setTotal("0")
        presenter.hide()
        orders.clear()
    }

    override fun willResignActive() {
        super.willResignActive()
        disposables.clear()
        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

    interface OrderPresenter : BasePresenter {
        fun buy(): Observable<Unit>
        fun setTotal(total: String)
        fun show()
        fun hide()
        fun success()
    }
}
