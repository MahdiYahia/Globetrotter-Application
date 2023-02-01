package hu.bme.aut.mobweb.wf72qq.globetrotter.network

import hu.bme.aut.mobweb.wf72qq.globetrotter.data.CountryData
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    val retrofit: Retrofit

    val countryApi: CountryApi

    private const val SERVICE_URL= "https://restcountries.com/"
    init {

        retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        countryApi = retrofit.create(CountryApi::class.java)
    }
    fun getCountryByName(name: String): Call<List<CountryData>?> {
        return countryApi.getCountryByName(name)
    }
}