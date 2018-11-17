package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

import com.uber.rib.core.Bundle
import com.uber.rib.core.Interactor
import com.uber.rib.core.RibInteractor
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

  override fun didBecomeActive(savedInstanceState: Bundle?) {
    super.didBecomeActive(savedInstanceState)

    // TODO: Add attachment logic here (RxSubscriptions, etc.).
  }

  override fun willResignActive() {
    super.willResignActive()

    // TODO: Perform any required clean up here, or delete this method entirely if not needed.
  }

  /**
   * Presenter interface implemented by this RIB's view.
   */
  interface OnboardingPresenter
}
