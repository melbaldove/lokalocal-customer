package st.teamcataly.lokalocalcustomer.root.loggedin.home

import com.uber.rib.core.Builder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorBaseComponent
import java.lang.annotation.Retention

import javax.inject.Qualifier
import javax.inject.Scope

import dagger.Provides
import dagger.BindsInstance
import st.teamcataly.lokalocalcustomer.root.loggedin.LoggedInEpoxyController

import java.lang.annotation.RetentionPolicy.CLASS

class HomeBuilder(dependency: ParentComponent) : Builder<HomeRouter, HomeBuilder.ParentComponent>(dependency) {

  /**
   * Builds a new [HomeRouter].
   *
   * @return a new [HomeRouter].
   */
  fun build(): HomeRouter {
    val interactor = HomeInteractor()
    val component = DaggerHomeBuilder_Component.builder()
        .parentComponent(dependency)
        .interactor(interactor)
        .build()

    return component.homeRouter()
  }

  interface ParentComponent {
    fun loggedInEpoxyController(): LoggedInEpoxyController
  }


  @dagger.Module
  abstract class Module {

    @dagger.Module
    companion object {

      @HomeScope
      @Provides
      @JvmStatic
      internal fun presenter(): EmptyPresenter {
        return EmptyPresenter()
      }

      @HomeScope
      @Provides
      @JvmStatic
      internal fun router(component: Component, interactor: HomeInteractor): HomeRouter {
        return HomeRouter(interactor, component)
      }

      // TODO: Create provider methods for dependencies created by this Rib. These methods should be static.
    }
  }


  @HomeScope
  @dagger.Component(modules = arrayOf(Module::class), dependencies = arrayOf(ParentComponent::class))
  interface Component : InteractorBaseComponent<HomeInteractor>, BuilderComponent {

    @dagger.Component.Builder
    interface Builder {
      @BindsInstance
      fun interactor(interactor: HomeInteractor): Builder

      fun parentComponent(component: ParentComponent): Builder
      fun build(): Component
    }

  }

  interface BuilderComponent {
    fun homeRouter(): HomeRouter
  }

  @Scope
  @Retention(CLASS)
  internal annotation class HomeScope


  @Qualifier
  @Retention(CLASS)
  internal annotation class HomeInternal
}
