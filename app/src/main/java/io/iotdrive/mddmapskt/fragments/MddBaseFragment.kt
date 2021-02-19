package io.iotdrive.mddmapskt.fragments

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.annotation.IdRes
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyRecyclerView
import com.airbnb.epoxy.EpoxyVisibilityTracker
import com.airbnb.mvrx.Mavericks
import com.airbnb.mvrx.MavericksView
import com.airbnb.mvrx.activityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.iotdrive.mddmapskt.R
import io.iotdrive.mddmapskt.utils.MddEpoxyController
import io.iotdrive.mddmapskt.viewmodels.LayoutViewModel

abstract class MddBaseFragment : Fragment(), MavericksView {

//    protected val viewModel by activityViewModel(LayoutViewModel::class)
    protected lateinit var mCoordinatorLayout: CoordinatorLayout
    protected lateinit var mEpoxyRecyclerView: EpoxyRecyclerView
    protected lateinit var mNavigationView: BottomNavigationView
    protected lateinit var epoxyVisibilityTracker: EpoxyVisibilityTracker
    private val epoxyController by lazy { epoxyController() }

    abstract fun epoxyController(): MddEpoxyController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        epoxyController.onRestoreInstanceState(savedInstanceState)
        epoxyVisibilityTracker = EpoxyVisibilityTracker()
    }

    override fun onResume() {
        super.onResume()
        epoxyVisibilityTracker.requestVisibilityCheck()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        mCoordinatorLayout = view.findViewById(R.id.io_mdd_maps_id_fragment_main_coordinator_layout)
        mEpoxyRecyclerView = view.findViewById(R.id.id_view_recycler_epoxy_mdd)
        mEpoxyRecyclerView.setController(epoxyController)
        mNavigationView = view.findViewById(R.id.navigation_bottom)
        mNavigationView.visibility = View.INVISIBLE
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Carousel.setDefaultGlobalSnapHelperFactory(object : Carousel.SnapHelperFactory() {
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return PagerSnapHelper()
            }
        })
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        epoxyController.onSaveInstanceState(outState)
    }

    override fun invalidate() {
        mEpoxyRecyclerView.requestModelBuild()
    }

    protected fun navigate(@IdRes id: Int, args: Parcelable? = null) {
        findNavController().navigate(id, Bundle().apply { putParcelable(Mavericks.KEY_ARG, args) })
    }
}