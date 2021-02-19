package io.iotdrive.mddmapskt.models

import android.view.View
import android.widget.TextView
import androidx.annotation.NonNull
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import com.airbnb.epoxy.VisibilityState
import io.iotdrive.mddmapskt.R


@EpoxyModelClass(layout = R.layout.model_pager_item)
abstract class MddPagerItemModel: EpoxyModelWithHolder<MddPagerItemModel.Holder>() {

    @EpoxyAttribute
    lateinit var title: String

    @EpoxyAttribute
    lateinit var onClickListener: () -> Unit

    override fun bind(holder: Holder) {
        super.bind(holder)
        holder.textTitle.text = title
        holder.view.setOnClickListener { onClickListener() }
    }

    class Holder : BaseEpoxyHolder() {
        val textTitle: TextView by bind(R.id.textTitle)
        val view: View by bind(R.id.id_view_model_pager_item)
    }
}