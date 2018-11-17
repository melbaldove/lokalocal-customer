package st.teamcataly.lokalocalcustomer.root.loggedin.shop

import io.reactivex.Completable
import io.reactivex.Single
import st.teamcataly.lokalocalcustomer.root.LokaLocalApi
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Order
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.OrderRequest

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class ShopRepository(private val api: LokaLocalApi) {
    fun getNearestShop(lat: Double, long: Double): Single<Shop> {
        return getShop("f3db9328-6947-41ef-875c-71b730cea08f")
    }

    fun getMenu(shop: Shop): Single<List<Coffee>> {
        return api.getMenu(shop.id)
    }

    fun order(id: String, order: List<Order>): Completable {
        return api.buy(id, OrderRequest(order, "7666a7b9-66a5-450f-ba89-b4e19215d9c1"))
    }

    fun getShop(id: String): Single<Shop> {
        return api.getShop(id)
    }
}