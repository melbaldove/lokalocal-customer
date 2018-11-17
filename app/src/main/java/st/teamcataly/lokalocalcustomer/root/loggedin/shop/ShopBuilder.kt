package st.teamcataly.lokalocalcustomer.root.loggedin.shop

import android.view.ViewGroup
import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import dagger.BindsInstance
import dagger.Provides
import io.reactivex.Observable
import st.teamcataly.lokalocalcustomer.root.LokaLocalApi
import st.teamcataly.lokalocalcustomer.root.RootLifecycleEvent
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.OrderBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection.ShopSelectionBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection.ShopSelectionInteractor
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

class ShopBuilder(dependency: ParentComponent) : Builder<ShopRouter, ShopBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [ShopRouter].
     *
     * @return a new [ShopRouter].
     */
    fun build(): ShopRouter {
        val interactor = ShopInteractor()
        val component = DaggerShopBuilder_Component.builder()
                .parentComponent(dependency)
                .interactor(interactor)
                .build()

        return component.shopRouter()
    }

    interface ParentComponent {
        fun loggedInEpoxyController(): LoggedInEpoxyController
        fun shopParentView(): ShopParentView
        fun androidEventsService(): AndroidEventsService
        fun rootLifecycleStream(): Observable<RootLifecycleEvent>
        fun lokaLocalApi(): LokaLocalApi
        fun loginResponse(): LoginResponse
    }


    @dagger.Module
    abstract class Module {

        @dagger.Module
        companion object {

            @ShopScope
            @Provides
            @JvmStatic
            internal fun presenter(): EmptyPresenter {
                return EmptyPresenter()
            }

            @ShopScope
            @Provides
            @JvmStatic
            internal fun router(component: Component, interactor: ShopInteractor, shopParentView: ShopParentView): ShopRouter {
                return ShopRouter(interactor, component, shopParentView as ViewGroup, OrderBuilder(component), ShopSelectionBuilder(component))
            }

            @ShopScope
            @Provides
            @JvmStatic
            internal fun shopRepository(api: LokaLocalApi, loginResponse: LoginResponse): ShopRepository {
                return ShopRepository(api, loginResponse.qrId)
            }


            @ShopScope
            @Provides
            @JvmStatic
            internal fun shopListener(interactor: ShopInteractor): ShopSelectionInteractor.Listener {
                return interactor.ShopSelectionListener()
            }
        }
    }


    @ShopScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<ShopInteractor>, OrderBuilder.ParentComponent, ShopSelectionBuilder.ParentComponent, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: ShopInteractor): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }

    }

    interface BuilderComponent {
        fun shopRouter(): ShopRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class ShopScope


    @Qualifier
    @Retention(CLASS)
    internal annotation class ShopInternal
}
