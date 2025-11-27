package com.example.pocket_road_ui.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.pocket_road_ui.data.repository.IAuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: IAuthRepository
) : ViewModel() {


}