package st.teamcataly.lokalocalcustomer.root.loggedin.shop

import io.reactivex.Completable
import io.reactivex.Single
import st.teamcataly.lokalocalcustomer.root.LokaLocalApi
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Order

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

    fun order(order: List<Order>): Completable {
        return Completable.complete()
    }

    fun getShop(id: String): Single<Shop> {
        return api.getShop(id)
    }
}