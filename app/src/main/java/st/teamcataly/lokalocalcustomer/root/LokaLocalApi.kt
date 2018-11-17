package st.teamcataly.lokalocalcustomer.root

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.model.Shop
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.OrderRequest
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginDetails
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import st.teamcataly.lokalocalcustomer.root.loggedout.onboarding.model.RegistrationDetails

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

    @POST("customer/register")
    fun register(@Body request: RegistrationDetails): Completable

    @POST("customer/login")
    fun login(@Body request: LoginDetails): Single<LoginResponse>

    @GET("balance/byCardNumber")
    fun getBalance(@Query("qrId") id: String): Single<BalanceResponse>
}