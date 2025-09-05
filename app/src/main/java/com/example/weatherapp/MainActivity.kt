package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import com.example.weatherapp.WeatherApi
import com.example.weatherapp.WeatherResponse


class MainActivity : AppCompatActivity() {

    private lateinit var cityInput: EditText
    private lateinit var fetchButton: Button
    private lateinit var textView: TextView

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
        private const val API_KEY = "023a69e96b5084b3a8d3156a17df235a" // ⚠️ apna key yaha daalo
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cityInput = findViewById(R.id.cityInput)
        fetchButton = findViewById(R.id.fetchButton)
        textView = findViewById(R.id.textView)

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(WeatherApi::class.java)

        fetchButton.setOnClickListener {
            val city = cityInput.text.toString().trim()
            if (city.isNotEmpty()) {
                api.getWeather(city, API_KEY, "metric")
                    .enqueue(object : Callback<WeatherResponse> {
                        override fun onResponse(
                            call: Call<WeatherResponse>,
                            response: Response<WeatherResponse>
                        ) {
                            if (response.isSuccessful && response.body() != null) {
                                val weather = response.body()!!
                                val result = """
                                    City: ${weather.name}
                                    Temp: ${weather.main.temp} °C
                                    Feels Like: ${weather.main.feels_like} °C
                                    Humidity: ${weather.main.humidity} %
                                    Wind: ${weather.wind.speed} m/s
                                """.trimIndent()
                                textView.text = result
                            } else {
                                textView.text = "City not found!"
                            }
                        }

                        override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                            textView.text = "Error: ${t.message}"
                        }
                    })
            } else {
                textView.text = "Please enter a city"
            }
        }
    }
}
