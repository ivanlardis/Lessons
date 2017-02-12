package com.example.i_larin.pixabayreader.presentation.presenter

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.i_larin.pixabayreader.di.DI
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView.State
import com.example.i_larin.pixabayreader.repository.IPixabayImageRepository
import rx.android.schedulers.AndroidSchedulers
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

/**
 * Created by i_larin on 28.01.17.
 */

@InjectViewState
class PixabayImagesPresenter : MvpPresenter<PixabayImagesView>() {

    private var compositeSubscription: CompositeSubscription = CompositeSubscription()
    @Inject
    lateinit var pixabayImageRepository: IPixabayImageRepository

    init {
        DI.componentManager().businessLogicComponent().inject(this)
    }

    companion object {
        val TAG = "PixabayImagesPresenter"
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        Log.d(TAG, "onFirstViewAttach: pixabayImageRepository")
        viewState.showPullRefreshEnabled(true)
        compositeSubscription.add(
                pixabayImageRepository
                        .getObserverDataChange()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            viewState.showPullRefreshEnabled(false)
                            viewState.showLoadingMoreProgress(false)
                            viewState.setTitleActionBar(it.pixabayImagesVisual.tag)

                            Log.d(TAG, "attachView: pixabayImageRepository subscribe" + it.state.name)

                            when (it.state) {
                                IPixabayImageRepository.State.END_ITEMS -> {
                                    viewState.showData(State.SHOW_IS_DATA_NULL, it.pixabayImagesVisual.pixabayImageList)
                                    viewState.notifyUser("Конец загрузки")
                                }
                                IPixabayImageRepository.State.NEXT_ITEMS -> {
                                    viewState.showData(State.SHOW_MORE, it.pixabayImagesVisual.pixabayImageList)
                                }
                                IPixabayImageRepository.State.NEW_ITEMS -> {
                                    Log.d(TAG, "attachView: pixabayImageRepository" + it.pixabayImagesVisual.pixabayImageList.size)
                                    viewState.showData(State.SHOW, it.pixabayImagesVisual.pixabayImageList)
                                }
                                IPixabayImageRepository.State.ERROR -> {
                                    Log.d(TAG, "attachView: pixabayImageRepository ERROR")
                                    viewState.notifyUser(it.state.description)
                                    viewState.showData(State.SHOW_IS_DATA_NULL, it.pixabayImagesVisual.pixabayImageList)
                                }
                            }

                        }, {}, {})
        )
    }

    fun loadDataMore() {
        Log.e(TAG, "loadDataMore: pixabayImageRepository")
        pixabayImageRepository.loadMore(null, false)
        viewState.showLoadingMoreProgress(true)
    }

    fun loadData(tag: String?) {
        Log.e(TAG, "loadData: pixabayImageRepository")
        pixabayImageRepository.loadMore(tag, true)
        viewState.showPullRefreshEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeSubscription.clear()
        Log.d(TAG, "onDestroy: PixabayImagesPresenter ")
    }

}