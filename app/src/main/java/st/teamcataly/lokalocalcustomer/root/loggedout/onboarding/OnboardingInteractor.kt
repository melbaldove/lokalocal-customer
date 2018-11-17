package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.model.RegistrationDetails
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import st.teamcataly.lokalocalcustomer.util.BackPressService
import javax.inject.Inject

/**
 * Coordinates Business Logic for [OnboardingScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class OnboardingInteractor : Interactor<OnboardingInteractor.OnboardingPresenter, OnboardingRouter>() {

    @Inject
    lateinit var presenter: OnboardingPresenter
    @Inject
    lateinit var listener: Listener
    @Inject
    lateinit var androidEventsService: AndroidEventsService
    private val disposables = CompositeDisposable()

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        androidEventsService.addBackPressListener(backPressListener)
        presenter.register().subscribe {
            listener.onDone()
        }.addTo(disposables)
    }

    private val backPressListener = object : BackPressService.Listener {
        override fun onBackPressed(): Boolean {
            listener.onDone()
            return true
        }
    }

    interface Listener {
        fun onDone()
    }

    override fun willResignActive() {
        super.willResignActive()
        androidEventsService.removeBackPressListener(backPressListener)
        disposables.clear()
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface OnboardingPresenter {
        fun register(): Observable<RegistrationDetails>
    }
}
