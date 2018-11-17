package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class OrderRequest(
        val items: List<Order>,
        val card: String
)