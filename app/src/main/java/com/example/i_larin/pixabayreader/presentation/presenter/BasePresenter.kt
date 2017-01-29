package com.example.i_larin.pixabayreader.presentation.presenter

import java.lang.ref.WeakReference

/**
 * Created by black-sony on 29.01.17.
 */


abstract class BasePresenter<IView> {

    private var view: WeakReference<IView>? = null


    fun attachView(view: IView) {
        this.view = WeakReference(view)
    }


    fun detachView() {
        if (view != null) {
            view!!.clear()
            view = null
        }
    }

    protected fun getView(): IView? {
        if (view != null) {
            return view!!.get()
        } else {
            return null
        }
    }
}