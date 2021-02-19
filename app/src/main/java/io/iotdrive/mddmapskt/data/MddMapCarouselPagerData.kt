package io.iotdrive.mddmapskt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class MddMapCarouselPagerData(var items: Array<String> = emptyArray(),  var visibleItemIndex: Int = -1) : Parcelable