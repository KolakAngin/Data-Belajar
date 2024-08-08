package com.dcfanbase.storyapp.view.costumview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Patterns
import androidx.core.content.ContextCompat
import com.dcfanbase.storyapp.R
import com.google.android.material.textfield.TextInputEditText

class MyEmailEditText : TextInputEditText {


    constructor(context : Context) : super(context)
    constructor(context: Context,attr : AttributeSet) : super(context,attr)
    constructor(context : Context,attr : AttributeSet, defStyleAttr : Int) : super(context,attr,defStyleAttr)


    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        val draw = ContextCompat.getDrawable(this.context, R.drawable.baseline_priority_high_24) as Drawable
        if(!Patterns.EMAIL_ADDRESS.matcher(text).matches()){
            this.setError("Input Tidak sesuai",draw)
        }else{
            this.error = null
        }
    }


}