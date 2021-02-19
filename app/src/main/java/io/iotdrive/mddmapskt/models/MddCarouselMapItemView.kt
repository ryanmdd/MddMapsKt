package io.iotdrive.mddmapskt.models

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import android.widget.TextView
import com.airbnb.epoxy.*
import io.iotdrive.mddmapskt.R

@ModelView(autoLayout = ModelView.Size.WRAP_WIDTH_WRAP_HEIGHT)
class MddCarouselMapItemView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :  FrameLayout(context, attrs, defStyleAttr) {
    private val textView: TextView

    init {
        inflate(context, R.layout.model_pager_item, this)
        textView = (findViewById(R.id.textTitle))
    }

    @TextProp
    lateinit var title: CharSequence

    @AfterPropsSet
    fun useProps() {
        textView.text = title
    }

    companion object {
        private const val TAG = "MddCarouselMapItemView"
    }

}