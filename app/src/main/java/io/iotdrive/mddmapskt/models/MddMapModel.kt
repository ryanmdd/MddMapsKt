package io.iotdrive.mddmapskt.models

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import com.airbnb.epoxy.ModelProp
import com.airbnb.epoxy.ModelView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.*
import io.iotdrive.mddmapskt.R

@ModelView(autoLayout = ModelView.Size.MATCH_WIDTH_WRAP_HEIGHT)
class MddMapModel @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var googleMap: GoogleMap? = null
    private lateinit var mMarkers: List<LatLng>
    private var mapView: MapView

    init {
        inflate(context, R.layout.view_map_mdd, this)
        mapView = findViewById<MapView>(R.id.id_view_map_mdd)
        mapView.isLongClickable = false
        mapView.isClickable = false
        mapView.onCreate(null)
        mapView.getMapAsync {
            googleMap = it
            googleMap?.uiSettings?.isMapToolbarEnabled = false
            googleMap?.mapType = GoogleMap.MAP_TYPE_HYBRID
        }
        Log.d("TAG", "INIT")
    }

    @ModelProp
    fun setCenter(index: Int) {
        mapView.getMapAsync {
            googleMap?.animateCamera(
                CameraUpdateFactory.newCameraPosition(
                    CameraPosition
                        .Builder()
                        .target(mMarkers[index])
                        .zoom(16f)
                        .build()
                )
            )
        }
    }

    @ModelProp
    fun setMarkers(markers: List<LatLng>) {
        mMarkers = markers
        mapView.getMapAsync {
            googleMap = it
            googleMap?.uiSettings?.isMapToolbarEnabled = false
            mMarkers.forEach { item ->
                googleMap?.addMarker(
                    MarkerOptions()
                        .position(item)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
                )
            }
        }
    }
}