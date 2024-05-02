package com.sd.holidays.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.holidays.dto.CountryCode
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

    private val _mapCountry: MutableMap<String, String> = mutableMapOf()
    val mapCountry: Map<String, String>
        get() = _mapCountry

    init {
        loadDataCountry()
    }

    private fun loadDataCountry() {
        viewModelScope.launch {
            try {
                val temp = repository.loadDataCountry()
                _dataCountry.value = temp.map {
                    it.name
                }
                temp.forEach {
                    //     _mapCountry[it.countryCode] = it.name
                    _mapCountry[it.name] = it.countryCode
                }
                //     Log.d("MyLog", "$_mapCountry")
            } catch (e: Exception) {
                _dataCountry.value = emptyList()
            }
        }
    }
}