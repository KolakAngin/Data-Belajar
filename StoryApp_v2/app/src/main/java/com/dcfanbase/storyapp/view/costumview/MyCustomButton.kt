package com.dcfanbase.storyapp.view.costumview

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import com.dcfanbase.storyapp.R

class MyCustomButton : AppCompatButton {
    private var textColor : Int = 0
    private lateinit var enableBackground : Drawable
    private lateinit var disableBackground : Drawable

    constructor(context : Context) : super(context){init()}
    constructor(context : Context, attr : AttributeSet) : super(context,attr){init()}
    constructor(context: Context,attr: AttributeSet, defStyleAttr : Int) :  super(context,attr,defStyleAttr){init()}


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.background = if(isEnabled) enableBackground else disableBackground

        this.textSize = 12f
        this.setTextColor(textColor)
        this.gravity = Gravity.CENTER

    }


    fun init(){
        textColor = ContextCompat.getColor(context, androidx.appcompat.R.color.background_material_light)
        enableBackground = ContextCompat.getDrawable(context, R.drawable.bg_enable) as Drawable
        disableBackground = ContextCompat.getDrawable(context,R.drawable.bg_disable) as Drawable
    }
}