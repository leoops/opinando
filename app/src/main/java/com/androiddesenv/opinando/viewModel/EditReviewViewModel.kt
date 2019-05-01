package com.androiddesenv.opinando.viewModel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.androiddesenv.opinando.model.Review

class EditReviewViewModel: ViewModel() {

    var data: MutableLiveData<Review> = MutableLiveData()

}