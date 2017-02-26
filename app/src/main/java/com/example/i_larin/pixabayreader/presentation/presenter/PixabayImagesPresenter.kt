package com.example.i_larin.pixabayreader.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.i_larin.pixabayreader.di.DI
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView.State.*
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
        showRefresh()
        compositeSubscription.add(
                pixabayImageRepository
                        .getObserverDataChange()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ showData(it) }, {}, {})
        )
    }

    private fun showData(it: ResponceRepository) =
            with(viewState)
            {
                stopProgressView()
                setTitleActionBar(it.pixabayImagesVisual.tag)
                when (it.state) {
                    END_ITEMS -> {
                        showData(SHOW_IS_DATA_NULL, it.pixabayImagesVisual.pixabayImageList)
                        notifyUser("Конец загрузки")
                    }
                    NEXT_ITEMS -> {
                        showData(SHOW_MORE, it.pixabayImagesVisual.pixabayImageList)
                    }
                    NEW_ITEMS -> {
                        showData(SHOW, it.pixabayImagesVisual.pixabayImageList)
                    }
                    ERROR -> {
                        notifyUser(it.state.description)
                        showData(SHOW_IS_DATA_NULL, it.pixabayImagesVisual.pixabayImageList)
                    }
                }
            }


    private fun PixabayImagesView.stopProgressView() {
        showPullRefreshEnabled(false)
        showLoadingMoreProgress(false)
    }

    fun loadDataMore() {
        showLoadingMore()
        pixabayImageRepository.loadMore(null, false)
    }

    fun loadData(tag: String?) {
        showRefresh()
        pixabayImageRepository.loadMore(tag, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
    }

    private fun showLoadingMore() {
        viewState.showLoadingMoreProgress(true)
    }

    private fun showRefresh() {
        viewState.showPullRefreshEnabled(true)
    }
}