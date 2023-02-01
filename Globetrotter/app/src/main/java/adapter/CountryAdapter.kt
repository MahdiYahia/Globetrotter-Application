package hu.bme.aut.mobweb.wf72qq.globetrotter.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import hu.bme.aut.mobweb.wf72qq.globetrotter.CountryListActivity
import hu.bme.aut.mobweb.wf72qq.globetrotter.data.CountryData
import hu.bme.aut.mobweb.wf72qq.globetrotter.data.CountryDatabase
import hu.bme.aut.mobweb.wf72qq.globetrotter.databinding.CountryItemBinding

class CountryAdapter : RecyclerView.Adapter<CountryAdapter.ViewHolder> {


    inner class ViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root)
    {
    }
    var countryItems= mutableListOf<CountryData>()
    var context: Context

    constructor(context: Context, dbCountries: List<CountryData>)
    {
        this.context = context
        countryItems.addAll(dbCountries)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(CountryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return  countryItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentCountry = countryItems[position]
        
        holder.binding.tvCountryName.text= currentCountry.name.common
        holder.binding.tvCountryCode.text= currentCountry.cca3
        Glide.with(this.context).load(currentCountry.flags.png).into(holder.binding.ivFlag)
        
        holder.binding.btnDelete.setOnClickListener { 
            deleteCountry(holder.adapterPosition)
        }
    }

    private fun deleteCountry(position: Int) {
        Thread{
            CountryDatabase.getInstance(context).countryDao().deleteCountry(countryItems.get(position))
            (context as CountryListActivity).runOnUiThread {
                countryItems.removeAt(position)
                notifyItemRemoved(position)
            }
        }.start()
    }
    fun addCountry(country: CountryData){
        Thread{

            try {
                CountryDatabase.getInstance(context).countryDao().insertCountry(country)
                (context as CountryListActivity).runOnUiThread{
                    countryItems.add(country)
                    notifyItemInserted(countryItems.lastIndex)
                }
            } catch (e: Exception) {
                Snackbar.make( (context as CountryListActivity).binding.root,e.localizedMessage, Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
            }
        }.start()
    }
}