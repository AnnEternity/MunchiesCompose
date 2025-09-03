package com.example.munchiescompose.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("filters")
@Parcelize
data class FilterEntity(
    @PrimaryKey(autoGenerate = true) val key: Int = 0,
    val id: String?,
    val name: String?,
    val url: String?
) : Parcelable
