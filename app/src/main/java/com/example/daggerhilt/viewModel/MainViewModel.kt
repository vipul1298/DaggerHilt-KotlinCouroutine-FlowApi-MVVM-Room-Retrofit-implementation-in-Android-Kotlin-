package com.example.daggerhilt.viewModel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.daggerhilt.models.Blog
import com.example.daggerhilt.repository.MainRepository
import com.example.daggerhilt.utils.DataState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MainViewModel
@ViewModelInject
constructor(
    private val mainRepository: MainRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
):ViewModel() {
    private  val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState:LiveData<DataState<List<Blog>>>
    get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent){
        viewModelScope.launch {
            when(mainStateEvent){
                is MainStateEvent.GetBlogEvents -> {
                    mainRepository.getBlog()
                        .onEach { dataState ->
                            _dataState.value=dataState
                        }
                        .launchIn(viewModelScope)
                }
                is MainStateEvent.None -> {

                }
            }
        }
    }
}

sealed class MainStateEvent{
    object GetBlogEvents : MainStateEvent()
    object None: MainStateEvent()
}