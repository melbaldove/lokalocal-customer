package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

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
import st.teamcataly.lokalocalcustomer.root.LokaLocalApi
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent
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
    @Inject
    lateinit var rootLifeCycleStream: Observable<RootLifecycleEvent>

    @Inject
    lateinit var lokaLocalApi: LokaLocalApi

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        androidEventsService.addBackPressListener(backPressListener)
        rootLifeCycleStream.subscribe(presenter::onRootLifecycleEvent).addTo(disposables)
        presenter.register().subscribe {
            presenter.shouldShowLoading(loadingOptions = LoadingOptions(isLoading = true, message = "Please wait while we create your profile..."))
            lokaLocalApi.register(it)
                    .doOnEvent {
                        presenter.shouldShowLoading(loadingOptions = LoadingOptions(isLoading = false))
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        presenter.success()
                        listener.onDone()
                    }, {
                        presenter.showError(ErrorModel(message = it.localizedMessage))
                    })
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
    interface OnboardingPresenter : BasePresenter {
        fun register(): Observable<RegistrationDetails>
        fun onRootLifecycleEvent(rootLifecycleEvent: RootLifecycleEvent)
        fun success()
    }
}
