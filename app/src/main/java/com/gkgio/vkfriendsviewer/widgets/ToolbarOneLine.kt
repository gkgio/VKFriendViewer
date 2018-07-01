package com.gkgio.vkfriendsviewer.widgets

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.gkgio.vkfriendsviewer.R

class ToolbarOneLine : FrameLayout {
  private lateinit var textViewTitle: TextView
  private lateinit var iconLeft: AppCompatImageView
  lateinit var viewBack: View

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

  fun setOnBackClickListener(clickListener: () -> Unit) {
    viewBack.setOnClickListener {
      clickListener()
    }
  }

  private fun init(attributes: AttributeSet?) {
    View.inflate(context, R.layout.view_toolbar_one_line, this)

    textViewTitle = findViewById(R.id.textViewTitle)
    iconLeft = findViewById(R.id.iconLeft)
    viewBack = findViewById(R.id.ivLeftContainer)

    attributes?.let { attrs ->
      val typedArray = context.theme.obtainStyledAttributes(
          attrs,
          R.styleable.ToolbarOneLineView,
          0, 0
      )

      val iconLeftResId = typedArray.getResourceId(R.styleable.ToolbarOneLineView_iconLeft, -1)
      if (iconLeftResId > 0) {
        iconLeft.setImageResource(iconLeftResId)
      }

      val titleText = typedArray.getString(R.styleable.ToolbarOneLineView_titleText)
      if (!titleText.isNullOrEmpty()) {
        textViewTitle.text = titleText
      }
    }
  }
}