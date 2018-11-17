package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link OnboardingBuilder.OnboardingScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class OnboardingRouter(
    view: OnboardingView,
    interactor: OnboardingInteractor,
    component: OnboardingBuilder.Component) : ViewRouter<OnboardingView, OnboardingInteractor, OnboardingBuilder.Component>(view, interactor, component)
