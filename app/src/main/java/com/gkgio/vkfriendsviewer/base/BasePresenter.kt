package com.gkgio.vkfriendsviewer.base

interface BasePresenter<in T: BaseView> {
    fun attachView(view: T)
    fun detachView()
}