package amr.barakat.muzica.data.model


import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentTrack(
    val artwork_url: String,
    val title: String
): Parcelable