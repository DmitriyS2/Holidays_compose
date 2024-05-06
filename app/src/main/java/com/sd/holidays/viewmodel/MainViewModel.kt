package com.sd.holidays.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.holidays.dto.CountryCode
import com.sd.holidays.dto.DataLongWeekEnd
import com.sd.holidays.dto.InfoAboutCountry
import com.sd.holidays.model.ModelCountryCode
import com.sd.holidays.model.ModelDataLongWeekEnd
import com.sd.holidays.model.ModelInfoAboutCountry
import com.sd.holidays.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

//    private val _dataCountry = MutableLiveData<List<String>>(emptyList())
//    val dataCountry: LiveData<List<String>>
//        get() = _dataCountry

    private var originListCountry: List<String> = emptyList()

    private val _dataModelCountry = MutableLiveData<ModelCountryCode>()
    val dataModelCountry: LiveData<ModelCountryCode>
        get() = _dataModelCountry

    private val _mapCountry: MutableMap<String, String> = mutableMapOf()
    val mapCountry: Map<String, String>
        get() = _mapCountry

    private val _infoCountry: MutableLiveData<ModelInfoAboutCountry> = MutableLiveData()
    val infoCountry: LiveData<ModelInfoAboutCountry>
        get() = _infoCountry

    private var _selectedCountry = ""
    val selectedCountry: String
        get() = _selectedCountry

    private val _dataModelLongWeekEnd = MutableLiveData<ModelDataLongWeekEnd>()
    val dataModelLongWeekEnd: LiveData<ModelDataLongWeekEnd>
        get() = _dataModelLongWeekEnd

    init {
        loadDataCountry()
    }

    fun loadDataCountry() {
        viewModelScope.launch {
            try {
                _dataModelCountry.value = ModelCountryCode(loading = true)
                val temp = repository.loadDataCountry()
                if (temp.isNotEmpty()) {
                    originListCountry = temp.map {
                        it.name
                    }
//                _dataModelCountry.value = ModelCountryCode(listCountry = temp.map{
//                    it.name
//                })
                    _dataModelCountry.value = ModelCountryCode(listCountry = originListCountry)
//                _dataCountry.value = temp.map {
//                    it.name
//                }
                    temp.forEach {
                        //     _mapCountry[it.countryCode] = it.name
                        _mapCountry[it.name] = it.countryCode
                    }
                } else {
                    _dataModelCountry.value = ModelCountryCode(error = true)
                }
                //     Log.d("MyLog", "$_mapCountry")
            } catch (e: Exception) {
                //     _dataCountry.value = emptyList()
                _dataModelCountry.value = ModelCountryCode(error = true)
            }
        }
    }

    fun getInfoAboutCountry(code: String?) {
        if (code == null) {
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
            } catch (e: Exception) {
                _infoCountry.value = ModelInfoAboutCountry(error = true)
            }
        }
    }

    fun searchCountry(text: String) {
        _dataModelCountry.value = ModelCountryCode(listCountry = originListCountry.filter {
            it.lowercase().startsWith(text.lowercase())
        })
    }

    fun changeSelectedCountry(name: String) {
        _selectedCountry = name
    }

    fun getListLongWeeEnd(value: String) {
        viewModelScope.launch {
            try {
                _dataModelLongWeekEnd.value = ModelDataLongWeekEnd(loading = true)
                val temp = repository.getLongWeekEnd(value, _mapCountry[selectedCountry] ?: "")
                    .map {
                        DataLongWeekEnd(
                            startDate = it.startDate.split("-").reversed().joinToString("-"),
                            endDate = it.endDate.split("-").reversed().joinToString("-"),
                            dayCount = it.dayCount
                        )
                    }

                _dataModelLongWeekEnd.value = ModelDataLongWeekEnd(
                    listDataLongWeekEnd =
                        temp
//                    repository.getLongWeekEnd(
//                        value,
//                        _mapCountry[selectedCountry] ?: "ru"
//                    )
                )
                if (_dataModelLongWeekEnd.value?.listDataLongWeekEnd.isNullOrEmpty()) {
                    _dataModelLongWeekEnd.value = ModelDataLongWeekEnd(error = true)
                }
                Log.d(
                    "MyLog",
                    "_dataModelLongWeekEnd=${_dataModelLongWeekEnd.value?.listDataLongWeekEnd} null=${_dataModelLongWeekEnd.value?.listDataLongWeekEnd.isNullOrEmpty()}"
                )
            } catch (e: Exception) {
                _dataModelLongWeekEnd.value = ModelDataLongWeekEnd(error = true)
            }
        }
    }
}