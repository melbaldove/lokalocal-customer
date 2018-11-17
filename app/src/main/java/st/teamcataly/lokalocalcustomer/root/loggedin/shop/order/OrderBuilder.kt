package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.ShopRepository
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.CoffeeBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee.CoffeeInteractor
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

class OrderBuilder(dependency: ParentComponent) : ViewBuilder<OrderView, OrderRouter, OrderBuilder.ParentComponent>(dependency) {


    /**
     * Builds a new [OrderRouter].
     *
     * @return a new [OrderRouter].
     */
    fun build(parentViewGroup: ViewGroup, shop: Shop): OrderRouter {
        val interactor = OrderInteractor()
        val view = createView(parentViewGroup)
        val component = DaggerOrderBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .shop(shop)
                .interactor(interactor)
                .build()

        return component.orderRouter()
    }


    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): OrderView {
        return inflater.inflate(R.layout.order_rib, parentViewGroup, false) as OrderView
    }

    interface ParentComponent {
        fun shopRepository(): ShopRepository
        fun epoxyController(): LoggedInEpoxyController
    }


    @dagger.Module
    abstract class Module {
        @OrderScope
        @Binds
        internal abstract fun presenter(view: OrderView): OrderInteractor.OrderPresenter

        @dagger.Module
        companion object {


            @OrderScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: OrderView,
                    interactor: OrderInteractor
            ): OrderRouter {
                return OrderRouter(view, interactor, component, CoffeeBuilder(component))
            }

            @OrderScope
            @Provides
            @JvmStatic
            internal fun coffeeListener(interactor: OrderInteractor): CoffeeInteractor.Listener {
                return interactor.CoffeeListener()
            }

            // TODO: Create provider methods for dependencies created by this Rib. These methods should be static.
        }
    }


    @OrderScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<OrderInteractor>, CoffeeBuilder.ParentComponent, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: OrderInteractor): Builder

            @BindsInstance
            fun view(view: OrderView): Builder

            @BindsInstance
            fun shop(shop: Shop): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }

    }

    interface BuilderComponent {
        fun orderRouter(): OrderRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class OrderScope


    @Qualifier
    @Retention(CLASS)
    internal annotation class OrderInternal
}
