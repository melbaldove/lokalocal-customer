package st.teamcataly.lokalocalcustomer.root.loggedin.home

import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import st.teamcataly.lokalocalcustomer.root.LokaLocalApi
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.ModelInitializer
import st.teamcataly.lokalocalcustomer.root.loggedin.home.epoxy.home
import st.teamcataly.lokalocalcustomer.root.loggedin.home.history.HistoryInteractor
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import javax.inject.Inject

/**
 * Coordinates Business Logic for [HomeScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class HomeInteractor : Interactor<EmptyPresenter, HomeRouter>() {

    @Inject
    lateinit var loggedInEpoxyController: LoggedInEpoxyController
    @Inject
    lateinit var loginResponse: LoginResponse

    @Inject
    lateinit var lokaLokaLocalApi: LokaLocalApi

    private var currentModelInitializer: ModelInitializer? = null

    private val disposables = CompositeDisposable()
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        currentModelInitializer = modelInitializer(loginResponse, "0")
        loggedInEpoxyController.addModel(currentModelInitializer!!)
        lokaLokaLocalApi.getBalance(loginResponse.qrId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val newModelInitializer = modelInitializer(loginResponse, it.balance.toString())
                    loggedInEpoxyController.replaceModel(currentModelInitializer!!, newModelInitializer)
                    currentModelInitializer = newModelInitializer
                }, {

                }).addTo(disposables)

    }

    private fun modelInitializer(loginResponse: LoginResponse, credits: String): ModelInitializer = {
        home {
            id("home")
            name(loginResponse.firstName)
            credits(credits)
            historyListener {
                router.attachHistory()
            }
            spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
        }
    }

    inner class HistoryListener: HistoryInteractor.Listener {
        override fun onDone() {
            router.detachHistory()
        }
    }

    override fun willResignActive() {
        super.willResignActive()
        disposables.clear()
        loggedInEpoxyController.removeModel(modelInitializer(loginResponse, "100"))
    }

}
