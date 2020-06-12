package com.braispc.sunstatus.model

data class SunModel (
	val type : String,
	val crs : Crs,
	val features : List<Features>
)

data class Begin (
	val timeInstant : String
)

data class Crs (
	val type : String,
	val properties : Properties
)

data class Days (
	val timePeriod : TimePeriod,
	val variables : List<Variables>
)

data class End (
	val timeInstant : String
)

data class Features (
	val type : String,
	val geometry : Geometry,
	val properties : Properties
)

data class Geometry (
	val type : String,
	val coordinates : List<Double>
)

data class Properties (
	val days : List<Days>
)

data class TimePeriod (
	val begin : Begin,
	val end : End
)

data class Variables (
	val name : String,
	val sunrise : String,
	val midday : String,
	val sunset : String,
	val duration : String
)