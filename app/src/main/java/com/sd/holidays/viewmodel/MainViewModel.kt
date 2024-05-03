package com.sd.holidays.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.holidays.dto.CountryCode
import com.sd.holidays.dto.InfoAboutCountry
import com.sd.holidays.model.ModelCountryCode
import com.sd.holidays.model.ModelInfoAboutCountry
import com.sd.holidays.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dataCountry = MutableLiveData<List<String>>(emptyList())
    val dataCountry: LiveData<List<String>>
        get() = _dataCountry

    private val _dataModelCountry = MutableLiveData<ModelCountryCode>()
    val dataModelCountry:LiveData<ModelCountryCode>
        get() = _dataModelCountry

    private val _mapCountry: MutableMap<String, String> = mutableMapOf()
    val mapCountry: Map<String, String>
        get() = _mapCountry

    private val _infoCountry:MutableLiveData<ModelInfoAboutCountry> = MutableLiveData()
    val infoCountry:LiveData<ModelInfoAboutCountry>
        get() = _infoCountry

    init {
        loadDataCountry()
    }

    fun loadDataCountry() {
        viewModelScope.launch {
            try {
                _dataModelCountry.value = ModelCountryCode(loading = true)
                val temp = repository.loadDataCountry()
                if (temp.isNotEmpty()) {

                _dataModelCountry.value = ModelCountryCode(listCountry = temp.map{
                    it.name
                })
                _dataCountry.value = temp.map {
                    it.name
                }
                temp.forEach {
                    //     _mapCountry[it.countryCode] = it.name
                    _mapCountry[it.name] = it.countryCode
                }
            } else {
                _dataModelCountry.value = ModelCountryCode(error = true)
                }
                //     Log.d("MyLog", "$_mapCountry")
            } catch (e: Exception) {
                _dataCountry.value = emptyList()
                _dataModelCountry.value = ModelCountryCode(error = true)
            }
        }
    }

    fun getInfoAboutCountry(code:String?) {
        if (code==null) {
            _infoCountry.value = ModelInfoAboutCountry(error = true)
            return
        }
        viewModelScope.launch {
            try {
                _infoCountry.value = ModelInfoAboutCountry(loading = true)
                _infoCountry.value = ModelInfoAboutCountry(
                    info = repository.getInfoAboutCountry(code)
                )
                if (_infoCountry.value?.info == InfoAboutCountry()) {
                    _infoCountry.value = ModelInfoAboutCountry(error = true)
                }
//                val temp = repository.loadDataCountry()
//                _dataCountry.value = temp.map {
//                    it.name
//                }
//                temp.forEach {
//                    //     _mapCountry[it.countryCode] = it.name
//                    _mapCountry[it.name] = it.countryCode
//                }
                //     Log.d("MyLog", "$_mapCountry")
            } catch (e: Exception) {
                _infoCountry.value = ModelInfoAboutCountry(error = true)
            }
        }
    }
}