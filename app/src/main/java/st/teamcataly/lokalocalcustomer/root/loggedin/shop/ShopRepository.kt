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
class ShopRepository(private val api: LokaLocalApi,
                     private val qrId: String) {
    fun getNearestShop(lat: Double, long: Double): Single<Shop> {
        return getShop("f3db9328-6947-41ef-875c-71b730cea08f")
    }

    fun getMenu(shop: Shop): Single<List<Coffee>> {
        return api.getMenu(shop.id).map {
            val bestSeller = it.firstOrNull { it.tag?.contains("BEST_SELLER") == true }
            bestSeller ?: return@map it
            val mutable = it.toMutableList()
            mutable.remove(bestSeller)
            mutable.add(0, bestSeller)
            mutable
        }
    }

    fun order(id: String, order: List<Order>): Completable {
        return api.buy(id, OrderRequest(order, qrId))
    }

    fun getShop(id: String): Single<Shop> {
        return api.getShop(id)
    }
}