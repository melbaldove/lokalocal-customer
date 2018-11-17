package st.teamcataly.lokalocalcustomer.root.loggedin

import com.airbnb.epoxy.EpoxyController

/**
 * @author Melby Baldove
 * melbourne.baldove@owtoph.com
 */
class LoggedInEpoxyController : EpoxyController() {
    private val modelInitializers: MutableList<ModelInitializer> = mutableListOf()

    override fun buildModels() {
        modelInitializers.forEach { it() }
    }

    fun addModel(modelInitializer: ModelInitializer) {
        modelInitializers.add(modelInitializer)
        requestModelBuild()
    }

    fun removeModel(modelInitializer: ModelInitializer) {
        modelInitializers.remove(modelInitializer)
        requestModelBuild()
    }

    fun replaceModel(old: ModelInitializer, new: ModelInitializer) {
        val index = modelInitializers.indexOf(old)
        modelInitializers.removeAt(index)
        modelInitializers.add(index, new)
        requestModelBuild()
    }
}

typealias ModelInitializer = EpoxyController.() -> Unit