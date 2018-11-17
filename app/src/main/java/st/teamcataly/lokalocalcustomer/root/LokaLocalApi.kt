package st.teamcataly.lokalocalcustomer.root

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.OrderRequest

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
interface LokaLocalApi {
    @GET("partner/{id}/menu")
    fun getMenu(@Path("id") id: String): Single<List<Coffee>>

    @GET("partner/{id}")
    fun getShop(@Path("id") id: String): Single<Shop>

    @POST("partner/{id}/buy")
    fun buy(@Path("id") id: String, @Body request: OrderRequest): Completable
}