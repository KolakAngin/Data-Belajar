package com.dcfanbase.storyapp.view.login

import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.core.widget.doOnTextChanged
import com.dcfanbase.storyapp.view.costumview.MyCustomButton
import com.dcfanbase.storyapp.view.costumview.MyEmailEditText
import com.dcfanbase.storyapp.view.costumview.MyPasswordEditText


fun checkRequireChar2(myEmailEditText: MyEmailEditText,myPasswordEditText: MyPasswordEditText,myCustomButton: MyCustomButton){

    myEmailEditText.addTextChangedListener(object : TextWatcher{
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

        }

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            if(Patterns.EMAIL_ADDRESS.matcher(text!!).matches()){
                myPasswordEditText.addTextChangedListener(object :TextWatcher{
                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {

                            myCustomButton.isEnabled = text!!.length >= 8

                    }

                    override fun afterTextChanged(p0: Editable?) {

                    }

                })
            }else{
                myCustomButton.isEnabled = false
            }
        }

        override fun afterTextChanged(p0: Editable?) {

        }

    })
}

fun checkRequireChar(myEmailEditText: MyEmailEditText,myPasswordEditText: MyPasswordEditText,myCustomButton: MyCustomButton){
    myEmailEditText.doOnTextChanged{text1,_,_,_ ->
        if(Patterns.EMAIL_ADDRESS.matcher(text1!!).matches()){
            myEmailEditText.doOnTextChanged { text2, _, _, _ ->
                myCustomButton.isEnabled = text2!!.length >= 8
            }
        }else{
            myCustomButton.isEnabled = false
        }

    }
}