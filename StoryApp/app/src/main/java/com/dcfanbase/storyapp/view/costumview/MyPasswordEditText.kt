package com.dcfanbase.storyapp.view.costumview

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.dcfanbase.storyapp.R
import com.google.android.material.textfield.TextInputEditText

class MyPasswordEditText : TextInputEditText {

    constructor(context : Context) : super(context)
    constructor(context : Context, attr : AttributeSet) : super(context,attr)
    constructor(context: Context,attr: AttributeSet,defStyleAttr : Int) : super(context,attr,defStyleAttr)

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        val draw = ContextCompat.getDrawable(this.context, R.drawable.baseline_priority_high_24) as Drawable
        if(text!!.toString().length < 8){
            setError(resources.getString(R.string.password_warning), draw)
        }else{
            this.error = null
        }
    }
}