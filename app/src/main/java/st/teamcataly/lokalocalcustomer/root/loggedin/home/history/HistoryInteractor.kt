package st.teamcataly.lokalocalcustomer.root.loggedin.home.history

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import st.teamcataly.lokalocalcustomer.root.Transaction
import st.teamcataly.lokalocalcustomer.root.TransactionRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import st.teamcataly.lokalocalcustomer.util.BackPressService
import javax.inject.Inject

/**
 * Coordinates Business Logic for [HistoryScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class HistoryInteractor : Interactor<HistoryInteractor.HistoryPresenter, HistoryRouter>() {

    @Inject
    lateinit var presenter: HistoryPresenter

    @Inject
    lateinit var listener: Listener

    @Inject
    lateinit var androidEventsService: AndroidEventsService

    @Inject
    lateinit var transactionRepository: TransactionRepository

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        androidEventsService.addBackPressListener(backPressListener)
        presenter.setHistory(transactionRepository.getRecentTransactions())
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
    }

    interface Listener {
        fun onDone()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface HistoryPresenter {
        fun setHistory(history: List<Transaction>)
    }
}
