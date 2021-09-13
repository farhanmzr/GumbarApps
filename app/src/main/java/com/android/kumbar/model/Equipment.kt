package com.android.kumbar.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Equipment (
    var name: String? = null,
    var imgEquipment: String? = null,
    var type: String? = null,
    var description: String? = null,
) : Parcelable