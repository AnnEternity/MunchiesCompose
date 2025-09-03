package com.example.munchiescompose.ui.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.munchiescompose.R

val helvetica = FontFamily(Font(R.font.helvetica))
val helvetica_bold = FontFamily(Font(R.font.helvetica_bold))
val inter = FontFamily(Font(R.font.inter))
val poppins = FontFamily(Font(R.font.poppins_regular))

val title1 = TextStyle(
    fontFamily = helvetica,
    fontWeight = FontWeight.W400,
    fontSize = 18.sp,
    lineHeight = 18.sp,
    letterSpacing = 0.sp,
)

val title2 = TextStyle(
    fontFamily = poppins,
    fontWeight = FontWeight.W500,
    fontSize = 14.sp,
    lineHeight = 20.sp,
    letterSpacing = 0.sp,
)

val subtitle1 = TextStyle(
    fontFamily = helvetica_bold,
    fontWeight = FontWeight.W700,
    fontSize = 12.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
)

val footer1 = TextStyle(
    fontFamily = inter,
    fontWeight = FontWeight.W500,
    fontSize = 10.sp,
    letterSpacing = 0.sp,
)

val headline1 = TextStyle(
    fontFamily = helvetica,
    fontWeight = FontWeight.W400,
    fontSize = 24.sp,
    lineHeight = 24.sp,
    letterSpacing = 0.sp,
)

val headline2 = TextStyle(
    fontFamily = helvetica,
    fontWeight = FontWeight.W400,
    fontSize = 16.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.sp,
)

val rating = TextStyle(
    fontFamily = inter,
    fontWeight = FontWeight.W700,
    fontSize = 10.sp,
    letterSpacing = 0.sp,
)
