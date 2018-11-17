package st.teamcataly.lokalocalcustomer.root.loggedin.shop.order.coffee

import com.uber.rib.core.RibTestBasePlaceholder
import com.uber.rib.core.EmptyPresenter
import com.uber.rib.core.InteractorHelper

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class CoffeeInteractorTest : RibTestBasePlaceholder() {

  @Mock internal lateinit var presenter: EmptyPresenter
  @Mock internal lateinit var router: CoffeeRouter

  private var interactor: CoffeeInteractor? = null

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)

    interactor = TestCoffeeInteractor.create()
  }

  /**
   * TODO: Delete this example and add real tests.
   */
  @Test
  fun anExampleTest_withSomeConditions_shouldPass() {
    // Use InteractorHelper to drive your interactor's lifecycle.
    InteractorHelper.attach(interactor!!, presenter, router, null)
    InteractorHelper.detach(interactor!!)

    throw RuntimeException("Remove this test and add real tests.")
  }
}
