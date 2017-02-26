package com.example.i_larin.pixabayreader.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.i_larin.pixabayreader.di.DI
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView

import com.example.i_larin.pixabayreader.repository.IPixabayImageRepository
import com.example.i_larin.pixabayreader.repository.model.ResponceRepository
import com.example.i_larin.pixabayreader.repository.model.StateRepository
import com.example.i_larin.pixabayreader.repository.model.StateRepository.*
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by i_larin on 28.01.17.
 */

@InjectViewState
class PixabayImagesPresenter : MvpPresenter<PixabayImagesView>() {

    private var compositeSubscription = CompositeSubscription()
    @Inject
    lateinit var pixabayImageRepository: IPixabayImageRepository

    init {
        DI.componentManager().businessLogicComponent().inject(this)
    }


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        compositeSubscription.add(
                pixabayImageRepository
                        .getObserverDataChange()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ showData(it) }, {}, {})
        )
    }

    private fun showData(it: ResponceRepository) = with(viewState)
    {
        stopRefresh()

        when (it.state) {
            NEW_ITEMS -> {
                showData(it.pixabayImagesVisual)
                notifyUser("Данные загрузились")
            }

            ERROR -> {
                notifyUser(it.state.description)
            }
        }
    }


    fun loadData() {

        pixabayImageRepository.loadData()
    }


    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }

}