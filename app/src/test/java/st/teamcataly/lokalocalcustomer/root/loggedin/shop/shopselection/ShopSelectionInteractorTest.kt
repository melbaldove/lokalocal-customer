package st.teamcataly.lokalocalcustomer.root.loggedin.shop.shopselection

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class ShopSelectionInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: ShopSelectionInteractor.ShopSelectionPresenter
  @Mock internal lateinit var router: ShopSelectionRouter

  private var interactor: ShopSelectionInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestShopSelectionInteractor.create(presenter)
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach<ShopSelectionInteractor.ShopSelectionPresenter, ShopSelectionRouter>(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}