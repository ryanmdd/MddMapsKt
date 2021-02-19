package io.iotdrive.mddmapskt.models

import android.widget.TextView
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import io.iotdrive.mddmapskt.R

@EpoxyModelClass(layout = R.layout.model_empty_content)
abstract class EmptyContent: EpoxyModelWithHolder<EmptyContent.Holder>() {

    @EpoxyAttribute
    lateinit var title: String

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textTitle.text = title
    }

    class Holder : BaseEpoxyHolder() {
        val textTitle: TextView by bind(R.id.id_text_view)
    }
}