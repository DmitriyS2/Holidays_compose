package com.sd.holidays.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sd.holidays.dto.DataHoliday
import com.sd.holidays.dto.DataLongWeekEnd
import com.sd.holidays.dto.InfoAboutCountry
import com.sd.holidays.model.ModelCountryCode
import com.sd.holidays.model.ModelDataHoliday
import com.sd.holidays.model.ModelDataLongWeekEnd
import com.sd.holidays.model.ModelInfoAboutCountry
import com.sd.holidays.repository.Repository
import com.sd.holidays.util.getDay
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _stateNotification = MutableLiveData(false)
    val stateNotification:LiveData<Boolean>
        get() = _stateNotification

    private var originListCountry: List<String> = emptyList()

    private val _dataModelCountry = MutableLiveData<ModelCountryCode>()
    val dataModelCountry: LiveData<ModelCountryCode>
        get() = _dataModelCountry

    private val _mapCountry: MutableMap<String, String> = mutableMapOf()
    val mapCountry: Map<String, String>
        get() = _mapCountry

    private val _mapCode:MutableMap<String, String> = mutableMapOf()

    private val _infoCountry: MutableLiveData<ModelInfoAboutCountry> = MutableLiveData()
    val infoCountry: LiveData<ModelInfoAboutCountry>
        get() = _infoCountry


    private var _selectedCountry = "" //название страны
    val selectedCountry: String
        get() = _selectedCountry

    private val _dataModelLongWeekEnd = MutableLiveData<ModelDataLongWeekEnd>()
    val dataModelLongWeekEnd: LiveData<ModelDataLongWeekEnd>
        get() = _dataModelLongWeekEnd

    private val _dataModelHoliday = MutableLiveData<ModelDataHoliday>()
    val dataModelHoliday: LiveData<ModelDataHoliday>
        get() = _dataModelHoliday

    private val _dataModelNextHoliday = MutableLiveData<ModelDataHoliday>()
    val dataModelNextHoliday:LiveData<ModelDataHoliday>
        get() = _dataModelNextHoliday

    init {
        loadDataCountry()
        initStateNotification()
    }

    private fun initStateNotification() {
        _stateNotification.value = repository.getStateNotification()
    }

    fun setStateNotification(state:Boolean) {
        _stateNotification.value = state
        repository.setStateNotification(state)
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
                    _dataModelCountry.value = ModelCountryCode(listCountry = originListCountry)
                    temp.forEach {
                        _mapCountry[it.name] = it.countryCode
                        _mapCode[it.countryCode] = it.name
                    }
                } else {
                    _dataModelCountry.value = ModelCountryCode(error = true)
                }
            } catch (e: Exception) {
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

    fun getListLongWeeEnd(year: String) {
        viewModelScope.launch {
            try {
                _dataModelLongWeekEnd.value = ModelDataLongWeekEnd(loading = true)
                val temp = repository.getLongWeekEnd(year, _mapCountry[selectedCountry] ?: "")
                    .map {
                        DataLongWeekEnd(
                            startDate = (it.startDate.split("-").reversed().joinToString("-")).getDay(),
                            endDate = (it.endDate.split("-").reversed().joinToString("-")).getDay(),
                            dayCount = it.dayCount
                        )
                    }

                _dataModelLongWeekEnd.value = ModelDataLongWeekEnd(listDataLongWeekEnd = temp)
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

    fun getListHoliday(year: String) {
        viewModelScope.launch {
            try {
                _dataModelHoliday.value = ModelDataHoliday(loading = true)
                val temp = repository.getHoliday(year, _mapCountry[selectedCountry] ?: "")
                    .map {
                        DataHoliday(
                            date = (it.date.split("-").reversed().joinToString("-")).getDay(),
                            localName = it.localName,
                            name = it.name,
                            countryCode = it.countryCode,
                            types = it.types
                        )
                    }

                _dataModelHoliday.value = ModelDataHoliday(
                    listDataHoliday = temp
                )
                if (_dataModelHoliday.value?.listDataHoliday.isNullOrEmpty()) {
                    _dataModelHoliday.value = ModelDataHoliday(error = true)
                }
                Log.d(
                    "MyLog",
                    "_dataModelHoliday=${_dataModelHoliday.value?.listDataHoliday} null=${_dataModelHoliday.value?.listDataHoliday.isNullOrEmpty()}"
                )
            } catch (e: Exception) {
                _dataModelHoliday.value = ModelDataHoliday(error = true)
            }
        }
    }

    fun getListNextHoliday() {
        viewModelScope.launch {
            try {
                _dataModelNextHoliday.value = ModelDataHoliday(loading = true)
                val temp = repository.getNextHoliday()
                    .map {
                        DataHoliday(
                            date = (it.date.split("-").reversed().joinToString("-")),
                            localName = it.localName,
                            name = it.name,
                            countryCode = _mapCode[it.countryCode] ?: "", //название страны
                            types = it.types
                        )
                    }

                _dataModelNextHoliday.value = ModelDataHoliday(
                    listDataHoliday = temp
                )
                if (_dataModelNextHoliday.value?.listDataHoliday.isNullOrEmpty()) {
                    _dataModelNextHoliday.value = ModelDataHoliday(error = true)
                }
                Log.d(
                    "MyLog",
                    "_dataModelNextHoliday=${_dataModelNextHoliday.value?.listDataHoliday} null=${_dataModelNextHoliday.value?.listDataHoliday.isNullOrEmpty()}"
                )
            } catch (e: Exception) {
                _dataModelHoliday.value = ModelDataHoliday(error = true)
            }
        }
    }
}