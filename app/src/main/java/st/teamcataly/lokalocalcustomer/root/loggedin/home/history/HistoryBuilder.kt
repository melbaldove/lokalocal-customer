package st.teamcataly.lokalocalcustomer.root.loggedin.home.history

import android.view.LayoutInflater
import android.view.ViewGroup
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import st.teamcataly.lokalocalcustomer.R
import st.teamcataly.lokalocalcustomer.root.TransactionRepository
import st.teamcataly.lokalocalcustomer.util.AndroidEventsService
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link HistoryScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class HistoryBuilder(dependency: ParentComponent) : ViewBuilder<HistoryView, HistoryRouter, HistoryBuilder.ParentComponent>(dependency) {

    /**
     * Builds a new [HistoryRouter].
     *
     * @param parentViewGroup parent view group that this router's view will be added to.
     * @return a new [HistoryRouter].
     */
    fun build(parentViewGroup: ViewGroup): HistoryRouter {
        val view = createView(parentViewGroup)
        val interactor = HistoryInteractor()
        val component = DaggerHistoryBuilder_Component.builder()
                .parentComponent(dependency)
                .view(view)
                .interactor(interactor)
                .build()
        return component.historyRouter()
    }

    override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): HistoryView? {
        return inflater.inflate(R.layout.history_rib, parentViewGroup, false) as HistoryView
    }

    interface ParentComponent {
        fun androidEventsService(): AndroidEventsService
        fun historyListener(): HistoryInteractor.Listener
        fun transactionRepository(): TransactionRepository
    }

    @dagger.Module
    abstract class Module {

        @HistoryScope
        @Binds
        internal abstract fun presenter(view: HistoryView): HistoryInteractor.HistoryPresenter

        @dagger.Module
        companion object {

            @HistoryScope
            @Provides
            @JvmStatic
            internal fun router(
                    component: Component,
                    view: HistoryView,
                    interactor: HistoryInteractor): HistoryRouter {
                return HistoryRouter(view, interactor, component)
            }
        }

        // TODO: Create provider methods for dependencies created by this Rib. These should be static.
    }

    @HistoryScope
    @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
    interface Component : InteractorBaseComponent<HistoryInteractor>, BuilderComponent {

        @dagger.Component.Builder
        interface Builder {
            @BindsInstance
            fun interactor(interactor: HistoryInteractor): Builder

            @BindsInstance
            fun view(view: HistoryView): Builder

            fun parentComponent(component: ParentComponent): Builder
            fun build(): Component
        }
    }

    interface BuilderComponent {
        fun historyRouter(): HistoryRouter
    }

    @Scope
    @Retention(CLASS)
    internal annotation class HistoryScope

    @Qualifier
    @Retention(CLASS)
    internal annotation class HistoryInternal
}
