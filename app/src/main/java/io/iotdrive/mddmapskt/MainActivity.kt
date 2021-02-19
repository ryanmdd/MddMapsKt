package io.iotdrive.mddmapskt

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.EpoxyVisibilityTracker
import io.iotdrive.mddmapskt.controller.MddMapCarouselController
import io.iotdrive.mddmapskt.databinding.ActivityMainBinding
import io.iotdrive.mddmapskt.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val controller = MddMapCarouselController()
        binding.idViewRecyclerEpoxyMdd.setController(controller)
        EpoxyVisibilityTracker().attach(binding.idViewRecyclerEpoxyMdd)
        Carousel.setDefaultGlobalSnapHelperFactory(object : Carousel.SnapHelperFactory() {
            override fun buildSnapHelper(context: Context?): SnapHelper {
                return PagerSnapHelper()
            }
        })
        viewModel.viewState.observe(this, { viewState ->

            controller.setData(viewState)
        })

        if (null == savedInstanceState) {
            viewModel.loadData()
        }
    }
}