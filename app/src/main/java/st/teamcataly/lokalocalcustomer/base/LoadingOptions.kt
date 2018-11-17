package st.teamcataly.lokalocalcustomer.base

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
data class LoadingOptions(
        val isLoading: Boolean,
        val title: String = "Loading...",
        val message: String = "Please wait..."
)