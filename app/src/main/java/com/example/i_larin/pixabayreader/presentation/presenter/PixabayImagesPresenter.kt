package com.example.i_larin.pixabayreader.presentation.presenter

import android.util.Log
import com.example.i_larin.pixabayreader.di.DI
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.network.ApiFactoty
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView
import com.example.i_larin.pixabayreader.repository.IPixabayImageRepository
import com.example.i_larin.pixabayreader.repository.PixabayImageRepository
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by i_larin on 28.01.17.
 */
//TODO давай заменим два метода репозитория loadDataMore и loadData на следующих два:
// 1) Отдает обсервабл
// 2) Просит получить элементы по строке поиска

class PixabayImagesPresenter : BasePresenter<PixabayImagesView>() {
    companion object{
        val TAG = "PixabayImagesPresenter"
    }


    override fun attachView() {
        compositeSubscription.add(
                pixabayImageRepository

                        .getObserverDataChange()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            getView()?.showPullRefreshEnabled(false)
                            getView()?.showLoadingMoreProgress(false)
                            getView()?.setTitleActionBar(it.pixabayImagesVisual.tag)


                            Log.d(TAG, "attachView: pixabayImageRepository subscribe"+it.state.name)
                            when (it.state) {
                                PixabayImageRepository.State.END_ITEMS -> {

                                    getView()?.showIsDataNull(it.pixabayImagesVisual.pixabayImageList)

                                    getView()?.notifyUser("Конец загрузки")

                                }
                                PixabayImageRepository.State.NEXT_ITEMS -> {


                                    getView()?.showMoreData(it.pixabayImagesVisual.pixabayImageList)


                                }
                                PixabayImageRepository.State.NEW_ITEMS -> {
                                    Log.d(TAG, "attachView: pixabayImageRepository"+it.pixabayImagesVisual.pixabayImageList.size)

                                    getView()?.showData(it.pixabayImagesVisual.pixabayImageList)


                                }
                                PixabayImageRepository.State.ERROR -> {
                                    Log.d(TAG, "attachView: pixabayImageRepository ERROR")

                                    getView()?.notifyUser(it.state.description)

                                    getView()?.showIsDataNull(it.pixabayImagesVisual.pixabayImageList)

                                }


                            }

                        },
                                {},
                                {})

        )
    }

    @Inject
    lateinit var pixabayImageRepository: IPixabayImageRepository

    init {
        DI.componentManager().businessLogicComponent().inject(this)
    }


    fun loadDataMore() {
        Log.d(TAG, "loadDataMore: pixabayImageRepository")
        pixabayImageRepository.loadMore(null, false)

    }


    fun loadData(tag: String?) {
        Log.d(TAG, "loadData: pixabayImageRepository")
        pixabayImageRepository.loadMore(tag, true)
        getView()?.showPullRefreshEnabled(true)


    }

}