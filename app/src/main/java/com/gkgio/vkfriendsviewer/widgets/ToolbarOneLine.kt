package com.gkgio.vkfriendsviewer.widgets

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.gkgio.vkfriendsviewer.R

class ToolbarOneLine : FrameLayout {
  private lateinit var textViewTitle: TextView

  constructor(context: Context) : super(context) {
    init(null)
  }

  constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
    init(attrs)
  }

  constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
    init(attrs)
  }

  fun setTitle(title: String) {
    textViewTitle.text = title
  }

  private fun init(attributes: AttributeSet?) {
    View.inflate(context, R.layout.view_toolbar_one_line, this)

    textViewTitle = findViewById(R.id.textViewTitle)

    attributes?.let { attrs ->
      val typedArray = context.theme.obtainStyledAttributes(
          attrs,
          R.styleable.ToolbarOneLineView,
          0, 0
      )
      val titleText = typedArray.getString(R.styleable.ToolbarOneLineView_titleText)
      if (!titleText.isNullOrEmpty()) {
        textViewTitle.text = titleText
      }
    }
  }
}