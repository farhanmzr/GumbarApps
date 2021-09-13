package com.android.kumbar.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Mount (
    var name: String? = null,
    var imgMount: String? = null,
    var location: String? = null,
    var lat: Double? = null,
    var lon: Double? = null,
    var altitude: String? = null,
    var description: String? = null,
    var hikingTrails: String? = null,
    var infoMount: String? = null,
) : Parcelable