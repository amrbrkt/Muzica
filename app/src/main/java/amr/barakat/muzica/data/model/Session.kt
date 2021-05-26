package amr.barakat.muzica.data.model

data class Session(
    val current_track: CurrentTrack,
    val genres: List<String>,
    val listener_count: Int,
    val name: String
)