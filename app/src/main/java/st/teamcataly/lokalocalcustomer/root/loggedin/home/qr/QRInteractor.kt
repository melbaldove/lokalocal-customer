package st.teamcataly.lokalocalcustomer.root.loggedin.home.qr

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import st.teamcataly.lokalocalcustomer.util.BackPressService
import javax.inject.Inject

/**
 * Coordinates Business Logic for [QRScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class QRInteractor : Interactor<QRInteractor.QRPresenter, QRRouter>() {

    @Inject
    lateinit var presenter: QRPresenter

    @Inject
    lateinit var androidEventsService: AndroidEventsService

    @Inject
    lateinit var loginResponse: LoginResponse

    @Inject
    lateinit var listener: Listener

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        androidEventsService.addBackPressListener(backPressListener)
        presenter.qr(loginResponse.qrId)
        presenter.done().subscribe {
            listener.onDone()
        }
        // TODO: Add attachment logic here (RxSubscriptions, etc.).
    }

    private val backPressListener = object : BackPressService.Listener {
        override fun onBackPressed(): Boolean {
            listener.onDone()
            return true
        }

    }

    override fun willResignActive() {
        super.willResignActive()
        androidEventsService.removeBackPressListener(backPressListener)
        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

    interface Listener {
        fun onDone()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface QRPresenter {
        fun qr(id: String)
        fun done(): Observable<Unit>
    }
}
