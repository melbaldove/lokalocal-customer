package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee

import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.ModelInitializer
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.epoxy.coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.epoxy.coffeeTitle
import timber.log.Timber
import javax.inject.Inject

/**
 * Coordinates Business Logic for [CoffeeScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class CoffeeInteractor : Interactor<EmptyPresenter, CoffeeRouter>() {

    @Inject
    lateinit var shopRepository: ShopRepository
    @Inject
    lateinit var shop: Shop
    @Inject
    lateinit var loggedInEpoxyController: LoggedInEpoxyController

    private var currentModelInitializer: ModelInitializer? = null
    @Inject
    lateinit var listener: Listener
    private val disposables = CompositeDisposable()
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)

        shopRepository.getMenu(shop)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentModelInitializer = modelInitializer(it)
                    loggedInEpoxyController.addModel(currentModelInitializer!!)
                }, {
                    Timber.e(it)
                }).addTo(disposables)
    }

    private fun modelInitializer(coffee: List<Coffee>): ModelInitializer {
        return {
            coffeeTitle {
                id("coffeeTitle")
                spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
            }
            coffee.forEach {
                coffee {
                    id(it.id)
                    coffee(it)
                    addListener(listener::addItem)
                    removeListener(listener::removeItem)
                }
            }
        }
    }

    interface Listener {
        fun addItem(id: String)
        fun removeItem(id: String)
    }

    override fun willResignActive() {
        super.willResignActive()
        disposables.clear()
        loggedInEpoxyController.removeModel(currentModelInitializer!!)
        currentModelInitializer = null
        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }
}
