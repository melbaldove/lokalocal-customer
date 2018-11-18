package st.teamcataly.lokalocalcustomer.root.loggedin.home.qr

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.root.loggedout.model.LoginResponse
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link QRScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class QRBuilder(dependency: ParentComponent) : ViewBuilder<QRView, QRRouter, QRBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [QRRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [QRRouter].
     */
    fun build(parentViewGroup: ViewGroup): QRRouter {
        val view = createView(parentViewGroup)
        val interactor = QRInteractor()
        val component = DaggerQRBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .build()
        return component.qrRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): QRView? {
        return inflater.inflate(R.layout.qr_rib, parentViewGroup, false) as QRView
    }

    interface ParentComponent {
        fun loginResponse(): LoginResponse
        fun androidEventsService(): AndroidEventsService
        fun qrListener(): QRInteractor.Listener
    }

    @dagger.Module
    abstract class Module {

        @QRScope
        @Binds
        internal abstract fun presenter(view: QRView): QRInteractor.QRPresenter

        @dagger.Module
        companion object {

            @QRScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: QRView,
                    interactor: QRInteractor): QRRouter {
                return QRRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @QRScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<QRInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: QRInteractor): Builder

            @BindsInstance
            fun view(view: QRView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun qrRouter(): QRRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class QRScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class QRInternal
}
