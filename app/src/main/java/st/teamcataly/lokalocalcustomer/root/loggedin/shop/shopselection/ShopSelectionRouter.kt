package st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection

import android.view.View

import com.uber.rib.core.ViewRouter

/**
 * Adds and removes children of {@link ShopSelectionBuilder.ShopSelectionScope}.
 *
 * TODO describe the possible child configurations of this scope.
 */
class ShopSelectionRouter(
    view: ShopSelectionView,
    interactor: ShopSelectionInteractor,
    component: ShopSelectionBuilder.Component) : ViewRouter<ShopSelectionView, ShopSelectionInteractor, ShopSelectionBuilder.Component>(view, interactor, component)
