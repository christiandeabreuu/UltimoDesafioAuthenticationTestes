package br.com.zup.marvel.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Marvel(
    var image: Int,
    var name: String,
    var detail: String
) : Parcelable