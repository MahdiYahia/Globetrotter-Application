package hu.bme.aut.mobweb.wf72qq.globetrotter

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.mobweb.wf72qq.globetrotter.adapter.CountryAdapter
import hu.bme.aut.mobweb.wf72qq.globetrotter.data.CountryData
import hu.bme.aut.mobweb.wf72qq.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.wf72qq.globetrotter.databinding.ActivityCountryListBinding
import hu.bme.aut.mobweb.wf72qq.globetrotter.fragment.CountryDialog
import hu.bme.aut.mobweb.wf72qq.globetrotter.network.NetworkManager
import retrofit2.Call
import retrofit2.Response
import retrofit2.Callback

class CountryListActivity : AppCompatActivity(), CountryDialog.CountryHandler {

    private lateinit var countryAdapter: CountryAdapter
    lateinit var binding: ActivityCountryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCountryListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(findViewById(R.id.toolbar))

        Snackbar.make(binding.root,intent.getStringExtra("KEY_NAME").toString(), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()

        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener {
            CountryDialog().show(supportFragmentManager,"Dialog")

        }

        Thread{

            var dbCountries= CountryDatabase.getInstance(this).countryDao().getAllCountries()
            this.runOnUiThread {
                countryAdapter = CountryAdapter(this,dbCountries)
                binding.recyclerCountry.adapter= countryAdapter

            }
        }.start()
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_country_list, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onBackPressed() {
        Snackbar.make( binding.root,getString(R.string.logoutSnackbar), Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.map -> {
                Snackbar.make( binding.root,"map", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            R.id.about -> {
                Snackbar.make( binding.root,getString(R.string.mahdi_yahia_wf72qq_2022), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
            R.id.logout -> {
                finish()
            }
        }
        return true
    }

    override fun countryAdded(country: String) {
           NetworkManager.getCountryByName(country).enqueue(object : Callback<List<CountryData>?>{
               override fun onResponse(
                   call: Call<List<CountryData>?>,
                   response: Response<List<CountryData>?>
               ){
                   if (response.isSuccessful){
                       countryAdapter.addCountry(response.body()!![0])
                   } else{
                       Snackbar.make(binding.root,"Error"+response.message(),
                            Snackbar.LENGTH_LONG).show()
                   }
               }

               override fun onFailure(call: Call<List<CountryData>?>,t:Throwable){
                    t.printStackTrace()
                    Snackbar.make(binding.root,"Network request error occurred, check L06",
                            Snackbar.LENGTH_LONG).show()

               }
           })
    }
}