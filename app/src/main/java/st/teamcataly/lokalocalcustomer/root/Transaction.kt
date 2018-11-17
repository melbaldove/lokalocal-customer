package st.teamcataly.lokalocalcustomer.root

import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import java.util.*

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
data class Transaction(val coffee: Coffee, val quantity: Int, val total: Double, val date: Date, val shopName: String)