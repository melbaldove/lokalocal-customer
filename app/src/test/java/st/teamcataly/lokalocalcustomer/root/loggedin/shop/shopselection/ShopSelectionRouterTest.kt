package st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.RouterHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ShopSelectionRouterTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var component: ShopSelectionBuilder.Component
  @Mock internal lateinit var interactor: ShopSelectionInteractor
  @Mock internal lateinit var view: ShopSelectionView

  private var router: ShopSelectionRouter? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    router = ShopSelectionRouter(view, interactor, component)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use RouterHelper to drive your router's lifecycle.
    RouterHelper.attach(router!!)
    RouterHelper.detach(router!!)

    throw RuntimeException("Remove this test and add real tests.")
  }

}

