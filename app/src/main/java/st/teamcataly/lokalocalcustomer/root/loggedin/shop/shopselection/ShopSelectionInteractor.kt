package st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import st.teamcataly.lokalocalcustomer.util.BackPressService
import javax.inject.Inject

/**
 * Coordinates Business Logic for [ShopSelectionScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class ShopSelectionInteractor : Interactor<ShopSelectionInteractor.ShopSelectionPresenter, ShopSelectionRouter>() {

    @Inject
    lateinit var presenter: ShopSelectionPresenter
    @Inject
    lateinit var androidEventsService: AndroidEventsService
    @Inject
    lateinit var rootLifecycleStream: Observable<RootLifecycleEvent>
    @Inject
    lateinit var listener: Listener

    private val disposables = CompositeDisposable()
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        androidEventsService.addBackPressListener(backpressListener)
        rootLifecycleStream.subscribe(presenter::onRootLifecycleEvent).addTo(disposables)
        presenter.shopId().subscribe(listener::onShopId).addTo(disposables)

    }

    private val backpressListener = object : BackPressService.Listener {
        override fun onBackPressed(): Boolean {
            listener.onDone()
            return true
        }
    }

    override fun willResignActive() {
        super.willResignActive()
        androidEventsService.removeBackPressListener(backpressListener)
        disposables.clear()
    }

    interface Listener {
        fun onDone()
        fun onShopId(id: String)
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface ShopSelectionPresenter {
        fun onRootLifecycleEvent(rootLifecycleEvent: RootLifecycleEvent)
        fun shopId(): Observable<String>
    }
}
