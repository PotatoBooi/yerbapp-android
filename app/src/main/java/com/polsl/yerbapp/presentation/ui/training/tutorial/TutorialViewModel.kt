package com.polsl.yerbapp.presentation.ui.training.tutorial

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.polsl.yerbapp.R
import com.polsl.yerbapp.presentation.base.BaseViewModel
import com.polsl.yerbapp.presentation.base.NavigationProps

class TutorialViewModel() : BaseViewModel() {
    private val slides = arrayOf (
        TutorialSlide(R.drawable.ic_tutorial_1, R.string.TUTORIAL_1),
        TutorialSlide(R.drawable.ic_tutorial_2, R.string.TUTORIAL_2),
        TutorialSlide(R.drawable.ic_tutorial_3, R.string.TUTORIAL_3),
        TutorialSlide(R.drawable.ic_tutorial_4, R.string.TUTORIAL_4),
        TutorialSlide(R.drawable.ic_tutorial_5, R.string.TUTORIAL_5),
        TutorialSlide(R.drawable.ic_tutorial_6, R.string.TUTORIAL_6))

   // private val currentSlideId = ObservableField(0)
   private var currentSlideId = 0

    val currentSlide = ObservableField<TutorialSlide>(slides[0])
    private val isLastSlide: Boolean
        get() = currentSlideId == slides.size

    fun onNextClick(){
        // handle next slide or navigate to the training
        if(isLastSlide){
            val navigationId = R.id.action_tutorialFragment_to_trainingFragment
            _navigationProps.value = NavigationProps(navigationId, null)
        }else{
            currentSlideId++
            currentSlide.set(slides[currentSlideId])
        }
    }
}
data class TutorialSlide(val imageRes: Int, val descriptionRes: Int)