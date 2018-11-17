package st.teamcataly.lokalocalcustomer.root

import st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.model.Coffee
import java.util.*

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class TransactionRepository {
    private var transactions = mutableListOf<Transaction>()
    fun newTransaction(coffee: Coffee, quantity: Int, shopName: String) {
        transactions.add(Transaction(
                coffee = coffee,
                quantity = quantity,
                total = coffee.price * quantity,
                date = Date(),
                shopName = shopName
        ))
    }

    fun getRecentTransactions(): List<Transaction> {
        return transactions.sortedByDescending { it.date }
    }
}