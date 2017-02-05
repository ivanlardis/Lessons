package com.example.i_larin.pixabayreader.presentation.presenter

import rx.subscriptions.CompositeSubscription
import java.lang.ref.WeakReference

/**
 * Created by black-sony on 29.01.17.
 */


abstract class BasePresenter<IView> {
    var compositeSubscription = CompositeSubscription()

    private var view: WeakReference<IView>? = null


    fun attachView(view: IView) {
        this.view = WeakReference(view)
        attachView()
    }

    abstract fun attachView()


    fun detachView() {
        if (view != null) {
            compositeSubscription?.clear()
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