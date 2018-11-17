package st.teamcataly.lokalocalcustomer.root

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import st.teamcataly.lokalocalcustomer.root.loggedout.LoggedOutInteractor
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import javax.inject.Inject

/**
 * Coordinates Business Logic for [RootScope].
 *
 * TODO describe the logic of this scope.
 */
@RibInteractor
class RootInteractor : Interactor<RootInteractor.RootPresenter, RootRouter>() {

    @Inject
    lateinit var presenter: RootPresenter
    private var loginResponse: LoginResponse? = null
    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        router.attachLoggedOut()
    }

    override fun willResignActive() {
        super.willResignActive()

    }

    inner class LoggedOutListener : LoggedOutInteractor.Listener {
        override fun onLoggedIn(loginResponse: LoginResponse) {
            this@RootInteractor.loginResponse = loginResponse
            router.detachLoggedOut()
            router.attachLoggedIn(loginResponse)
        }
    }

    /**
     * Presenter interface implemented by this RIB's view.
     */
    interface RootPresenter
}
