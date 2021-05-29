package amr.barakat.muzica.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Session(
    val current_track: CurrentTrack,
    val genres: List<String>,
    val listener_count: Int,
    val name: String
) : Parcelable