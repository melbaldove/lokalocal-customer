package st.teamcataly.lokalocalcustomer.root.loggedin.home

import android.view.ViewGroup
import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import dagger.BindsInstance
import dagger.Provides
import st.teamcataly.lokalocalcustomer.root.LokaLocalApi
import st.teamcataly.lokalocalcustomer.root.TransactionRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInParentView
import st.teamcataly.lokalocalcustomer.root.loggedin.home.history.HistoryBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.home.history.HistoryInteractor
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

class HomeBuilder(dependency: ParentComponent) : Builder<HomeRouter, HomeBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [HomeRouter].
     *
     * @return a new [HomeRouter].
     */
    fun build(): HomeRouter {
        val interactor = HomeInteractor()
        val component = DaggerHomeBuilder_Component.builder()
                .parentComponent(dependency)
                .interactor(interactor)
                .build()

        return component.homeRouter()
    }

    interface ParentComponent {
        fun loggedInEpoxyController(): LoggedInEpoxyController
        fun loginResponse(): LoginResponse
        fun lokaLocalApi(): LokaLocalApi
        fun loggedInParentView(): LoggedInParentView
        fun androidEventsService(): AndroidEventsService
        fun transactionRepository(): TransactionRepository
    }


    @dagger.Module
    abstract class Module {

        @dagger.Module
        companion object {

            @HomeScope
            @Provides
            @JvmStatic
            internal fun presenter(): EmptyPresenter {
                return EmptyPresenter()
            }

            @HomeScope
            @Provides
            @JvmStatic
            internal fun router(component: Component, interactor: HomeInteractor, parentView: LoggedInParentView): HomeRouter {
                return HomeRouter(interactor, component, parentView as ViewGroup, HistoryBuilder(component))
            }

            @HomeScope
            @Provides
            @JvmStatic
            internal fun historyListener(interactor: HomeInteractor): HistoryInteractor.Listener {
                return interactor.HistoryListener()
            }
            // TODO: Create provider methods for dependencies created by this Rib. These methods should be static.
        }
    }


    @HomeScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<HomeInteractor>, HistoryBuilder.ParentComponent, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: HomeInteractor): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }

    }

    interface BuilderComponent {
        fun homeRouter(): HomeRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class HomeScope


    @Qualifier
    @Retention(CLASS)
    internal annotation class HomeInternal
}
