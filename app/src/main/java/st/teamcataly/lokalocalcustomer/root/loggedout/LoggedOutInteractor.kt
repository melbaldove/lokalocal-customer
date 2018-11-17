package st.teamcataly.lokalocalcustomer.root.loggedout

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
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginDetails
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.OnboardingInteractor
import javax.inject.Inject

/**
 * Coordinates Business Logic for [LoggedOutScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class LoggedOutInteractor : Interactor<LoggedOutInteractor.LoggedOutPresenter, LoggedOutRouter>() {

    @Inject
    lateinit var presenter: LoggedOutPresenter
    private val disposables = CompositeDisposable()

    @Inject
    lateinit var api: LokaLocalApi

    @Inject
    lateinit var listener: Listener

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        presenter.register().subscribe { router.attachOnboarding() }.addTo(disposables)
        presenter.login().subscribe {
            presenter.shouldShowLoading(LoadingOptions(isLoading = true, message = "Logging you in..."))
            api.login(it)
                    .doOnEvent { t1, t2 ->
                        presenter.shouldShowLoading(LoadingOptions(isLoading = false))
                    }
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(listener::onLoggedIn, {
                        presenter.showError(ErrorModel(message = it.localizedMessage))
                    }).addTo(disposables)

        }.addTo(disposables)
    }

    override fun willResignActive() {
        super.willResignActive()
        disposables.clear()
    }

    inner class OnboardingListener : OnboardingInteractor.Listener {
        override fun onDone() {
            router.detachOnBoarding()
        }

    }

    interface Listener {
        fun onLoggedIn(loginResponse: LoginResponse)
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface LoggedOutPresenter : BasePresenter {
        fun register(): Observable<Unit>
        fun login(): Observable<LoginDetails>
    }
}
