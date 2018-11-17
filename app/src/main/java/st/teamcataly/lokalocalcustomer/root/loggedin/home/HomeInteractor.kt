package st.teamcataly.lokalocalcustomer.root.loggedin.home

import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.EpoxyModel
import com.uber.rib.core.Bundle
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.home.epoxy.home
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

    override fun didBecomeActive(savedInstanceState: Bundle?) {
        super.didBecomeActive(savedInstanceState)
        loggedInEpoxyController.addModel(modelInitializer)

    }
    private val modelInitializer: EpoxyController.() -> Unit = {
        home {
            id("home")
            name("melby")
            credits("100")
            spanSizeOverride { totalSpanCount, position, itemCount -> totalSpanCount }
        }
    }
    override fun willResignActive() {
        super.willResignActive()
        loggedInEpoxyController.removeModel(modelInitializer)
        // TODO: Perform any required clean up here, or delete this method entirely if not needed.
    }

}
