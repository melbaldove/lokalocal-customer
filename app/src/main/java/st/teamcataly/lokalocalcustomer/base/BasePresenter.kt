package st.teamcataly.lokalocalcustomer.base

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
interface BasePresenter {
    fun shouldShowLoading(loadingOptions: LoadingOptions)
    fun showError(errorModel: ErrorModel)
}