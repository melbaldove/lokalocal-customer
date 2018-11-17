package st.teamcataly.lokalocalcustomer.root.loggedin

import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import io.reactivex.Observable
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.root.LokaLocalApi
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent
import st.teamcataly.lokalocalcustomer.root.loggedin.home.HomeBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopParentView
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link LoggedInScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class LoggedInBuilder(dependency: ParentComponent) : ViewBuilder<LoggedInView, LoggedInRouter, LoggedInBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [LoggedInRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [LoggedInRouter].
     */
    fun build(parentViewGroup: ViewGroup): LoggedInRouter {
        val view = createView(parentViewGroup)

        val interactor = LoggedInInteractor()
        view.layoutManager = GridLayoutManager(parentViewGroup.context, 2)
        val epoxyController = LoggedInEpoxyController()
        view.setController(epoxyController)
        val component = DaggerLoggedInBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .loggedInEpoxyController(epoxyController)
                .interactor(interactor)
                .build()
        return component.loggedinRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): LoggedInView? {
        return inflater.inflate(R.layout.loggedin_rib, parentViewGroup, false) as LoggedInView
    }

    interface ParentComponent {
        fun loggedInParentView(): LoggedInParentView
        fun androidEventsService(): AndroidEventsService
        fun rootLifecycleStream(): Observable<RootLifecycleEvent>
        fun lokaLocalApi(): LokaLocalApi
    }

    @dagger.Module
    abstract class Module {

        @LoggedInScope
        @Binds
        internal abstract fun presenter(view: LoggedInView): LoggedInInteractor.LoggedInPresenter

        @LoggedInScope
        @Binds
        internal abstract fun shopParentView(loggedInParentView: LoggedInParentView): ShopParentView

        @dagger.Module
        companion object {

            @LoggedInScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: LoggedInView,
                    interactor: LoggedInInteractor): LoggedInRouter {
                return LoggedInRouter(view, interactor, component, HomeBuilder(component), ShopBuilder(component))
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @LoggedInScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<LoggedInInteractor>, HomeBuilder.ParentComponent, ShopBuilder.ParentComponent, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: LoggedInInteractor): Builder

            @BindsInstance
            fun view(view: LoggedInView): Builder

            @BindsInstance
            fun loggedInEpoxyController(loggedInEpoxyController: LoggedInEpoxyController): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun loggedinRouter(): LoggedInRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class LoggedInScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class LoggedInInternal
}
