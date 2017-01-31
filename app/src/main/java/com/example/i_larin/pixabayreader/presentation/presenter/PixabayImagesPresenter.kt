package com.example.i_larin.pixabayreader.presentation.presenter

import android.util.Log
import com.example.i_larin.pixabayreader.di.DI
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.network.ApiFactoty
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView
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

    @Inject
    lateinit var pixabayImageRepository: PixabayImageRepository

    init {
        DI.componentManager().businessLogicComponent().inject(this)
    }


    fun loadDataMore() {
        //TODO забыл отписаться при уничтожении презентера
        pixabayImageRepository
                .getDataMore()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ n ->
                    if(n.complete)     getView()?.notifyUser("Конец загрузки")
                        else
                    getView()?.showMoreData(n.pixabayImages)
                }
                        , { e ->
                    getView()?.notifyUser(e.toString())
                    getView()?.showLoadingMoreProgress(false)
                }
                        , { getView()?.showLoadingMoreProgress(false) }

                )
    }


    fun loadData(tag: String?) {
        getView()?.showPullRefreshEnabled(true)
        Log.d("TAG", "onQueryTextChange: loadData= "+tag)
        //TODO забыл отписаться при уничтожении презентера
        pixabayImageRepository
                .getData(tag)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ n ->
                    getView()?.showData(n.pixabayImages)
                    getView()?.setTitleActionBar(n.tag)

                }
                        , { e ->
                    getView()?.notifyUser(e.toString())
                    getView()?.showPullRefreshEnabled(false)
                }
                        , { getView()?.showPullRefreshEnabled(false) }

                )

    }

}