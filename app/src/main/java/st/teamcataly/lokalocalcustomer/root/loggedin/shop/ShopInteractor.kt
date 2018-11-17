package st.teamcataly.lokalocalcustomer.root.loggedin.shop

import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import st.teamcataly.lokalocalcustomer.LocationService
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.ModelInitializer
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.epoxy.shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection.ShopSelectionInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [ShopScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class ShopInteractor : Interactor<EmptyPresenter, ShopRouter>() {
    @Inject
    lateinit var loggedInEpoxyController: LoggedInEpoxyController

    @Inject
    lateinit var shopRepository: ShopRepository

    @Inject
    lateinit var locationService: LocationService

    private var currentModelInitializer: ModelInitializer? = null

    private val disposables = CompositeDisposable()
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        locationService.getCurrentLocation()
                .flatMap { shopRepository.getNearestShop(it.lat, it.long) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    currentModelInitializer = modelInitializer(it.partnerName)
                    loggedInEpoxyController.addModel(currentModelInitializer!!)
                    router.attachOrder(it)
                }, {
                    it
                }).addTo(disposables)

    }

    private fun modelInitializer(name: String): ModelInitializer {
        return {
            shop {
                id("shop")
                shopName(name)
                changeShopListener { router.attachShopSelection() }
                spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
            }
        }
    }

    inner class ShopSelectionListener : ShopSelectionInteractor.Listener {
        override fun onDone() {
            router.detachShopSelection()
        }

        override fun onShopId(id: String) {
            shopRepository.getShop(id)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ shop ->
                        router.detachShopSelection()
                        currentModelInitializer?.let {
                            loggedInEpoxyController.replaceModel(it, modelInitializer(shop.partnerName))
                        }
                        router.detachOrder()
                        router.attachOrder(shop)
                        currentModelInitializer = modelInitializer(shop.partnerName)

                    }, {

                    }).addTo(disposables)
        }
    }

    override fun willResignActive() {
        disposables.clear()
        super.willResignActive()

        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

}
