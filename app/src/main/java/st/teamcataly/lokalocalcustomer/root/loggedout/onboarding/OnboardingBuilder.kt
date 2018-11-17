package st.teamcataly.lokalocalcustomer.root.loggedout.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link OnboardingScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class OnboardingBuilder(dependency: ParentComponent) : ViewBuilder<OnboardingView, OnboardingRouter, OnboardingBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [OnboardingRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [OnboardingRouter].
     */
    fun build(parentViewGroup: ViewGroup): OnboardingRouter {
        val view = createView(parentViewGroup)
        val interactor = OnboardingInteractor()
        val component = DaggerOnboardingBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .build()
        return component.onboardingRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): OnboardingView? {
        return inflater.inflate(R.layout.onboarding_rib, parentViewGroup, false) as OnboardingView
    }

    interface ParentComponent {
        fun androidEventsService(): AndroidEventsService
        fun onboardingListener(): OnboardingInteractor.Listener
    }

    @dagger.Module
    abstract class Module {

        @OnboardingScope
        @Binds
        internal abstract fun presenter(view: OnboardingView): OnboardingInteractor.OnboardingPresenter

        @dagger.Module
        companion object {

            @OnboardingScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: OnboardingView,
                    interactor: OnboardingInteractor): OnboardingRouter {
                return OnboardingRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @OnboardingScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<OnboardingInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: OnboardingInteractor): Builder

            @BindsInstance
            fun view(view: OnboardingView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun onboardingRouter(): OnboardingRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class OnboardingScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class OnboardingInternal
}
