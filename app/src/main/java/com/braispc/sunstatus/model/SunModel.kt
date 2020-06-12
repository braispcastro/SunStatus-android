package com.braispc.sunstatus.model

data class SunModel (

	val type : String,
	val crs : Crs,
	val features : List<Features>
)