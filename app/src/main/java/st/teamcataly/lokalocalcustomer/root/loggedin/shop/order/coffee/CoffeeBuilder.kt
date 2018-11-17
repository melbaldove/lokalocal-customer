package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee

import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import dagger.BindsInstance
import dagger.Provides
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

class CoffeeBuilder(dependency: ParentComponent) : Builder<CoffeeRouter, CoffeeBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [CoffeeRouter].
     *
     * @return a new [CoffeeRouter].
     */
    fun build(): CoffeeRouter {
        val interactor = CoffeeInteractor()
        val component = DaggerCoffeeBuilder_Component.builder()
                .parentComponent(dependency)
                .interactor(interactor)
                .build()

        return component.coffeeRouter()
    }

    interface ParentComponent {
        fun shopRepository(): ShopRepository
        fun shop(): Shop
        fun loggedInEpoxyController(): LoggedInEpoxyController
        fun coffeeListener(): CoffeeInteractor.Listener
    }


    @dagger.Module
    abstract class Module {

        @dagger.Module
        companion object {

            @CoffeeScope
            @Provides
            @JvmStatic
            internal fun presenter(): EmptyPresenter {
                return EmptyPresenter()
            }

            @CoffeeScope
            @Provides
            @JvmStatic
            internal fun router(component: Component, interactor: CoffeeInteractor): CoffeeRouter {
                return CoffeeRouter(interactor, component)
            }

            // TODO: Create provider methods for dependencies created by this Rib. These methods should be static.
        }
    }


    @CoffeeScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<CoffeeInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: CoffeeInteractor): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }

    }

    interface BuilderComponent {
        fun coffeeRouter(): CoffeeRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class CoffeeScope


    @Qualifier
    @Retention(CLASS)
    internal annotation class CoffeeInternal
}
