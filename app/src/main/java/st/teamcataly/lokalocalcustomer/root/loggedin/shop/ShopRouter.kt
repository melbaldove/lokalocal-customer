package st.teamcataly.lokalocalcustomer.root.loggedin.shop

import android.view.ViewGroup
import com.uber.rib.core.Router
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.OrderBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.OrderRouter
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection.ShopSelectionBuilder
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection.ShopSelectionRouter

/**
 * Adds and removes children of {@link ShopBuilder.ShopScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class ShopRouter(
        interactor: ShopInteractor,
        component: ShopBuilder.Component,
        private val shopParentView: ViewGroup,
        private val orderBuilder: OrderBuilder,
        private val shopSelectionBuilder: ShopSelectionBuilder) : Router<ShopInteractor, ShopBuilder.Component>(interactor, component) {
    private var orderRouter: OrderRouter? = null
    private var shopSelectionRouter: ShopSelectionRouter? = null
    fun attachOrder(shop: Shop) {
        orderRouter = orderBuilder.build(shopParentView, shop)
        shopParentView.addView(orderRouter?.view)
        attachChild(orderRouter)
    }

    fun detachOrder() {
        orderRouter ?: return
        detachChild(orderRouter)
        shopParentView.removeView(orderRouter?.view)
        orderRouter = null
    }

    fun attachShopSelection() {
        shopSelectionRouter = shopSelectionBuilder.build(shopParentView)
        shopParentView.addView(shopSelectionRouter?.view)
        attachChild(shopSelectionRouter)
    }

    fun detachShopSelection() {
        shopSelectionRouter ?: return
        detachChild(shopSelectionRouter)
        shopParentView.removeView(shopSelectionRouter?.view)
        shopSelectionRouter = null
    }
}
