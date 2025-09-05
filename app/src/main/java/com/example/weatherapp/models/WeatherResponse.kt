package com.example.weatherapp

data class WeatherResponse(
    val main: Main,
    val wind: Wind,
    val name: String
) {
    data class Main(
        val temp: Double,
        val feels_like: Double,
        val humidity: Int
    )

    data class Wind(
        val speed: Double
    )
}
