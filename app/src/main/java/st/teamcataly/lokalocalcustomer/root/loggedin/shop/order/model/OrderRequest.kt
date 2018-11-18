package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class OrderRequest(
        val items: List<OrderRequest.Order>,
        val card: String
) {
    class Order(val itemId: String, val quantity: Int)
}

