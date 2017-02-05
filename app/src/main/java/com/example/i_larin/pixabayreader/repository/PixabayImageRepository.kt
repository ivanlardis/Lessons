package com.example.i_larin.pixabayreader.repository

import android.util.Log
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.model.app.PixabayImages
import com.example.i_larin.pixabayreader.model.converter.PixabayImageConverter
import com.example.i_larin.pixabayreader.network.PixabayImageApi
import com.example.i_larin.pixabayreader.repository.IPixabayImageRepository.PixabayImageResponce
import com.example.i_larin.pixabayreader.repository.IPixabayImageRepository.State
import com.f2prateek.rx.preferences.RxSharedPreferences
import rx.Observable
import rx.schedulers.Schedulers
import rx.subjects.BehaviorSubject
import java.util.*
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicInteger


/**
 * Created by i_larin on 28.01.17.
 */

class PixabayImageRepository : IPixabayImageRepository {

    private val PIXABAY_API_KEY = "4386506-63329db38400319250ee539d9"
    private val DEFAULT_TEXT = "ROBOT"
    private val DEFAULT_TEXT_SEARCH_SP = "TEXT_SEARCH"

    private val api: PixabayImageApi

    private val rxSharedPreferences: RxSharedPreferences

    private var subjectDataChange = BehaviorSubject.create<PixabayImageResponce>();

    private val pixabayImageList: CopyOnWriteArrayList<PixabayImage> =
            CopyOnWriteArrayList<PixabayImage>()

    private var pageNo = 1
    private var pageSize = 20
    private var total: AtomicInteger = AtomicInteger(-1)

    companion object {
        val TAG = "PixabayImageRepository"
    }

    constructor(api: PixabayImageApi, rxSharedPreferences: RxSharedPreferences) {
        this.api = api
        this.rxSharedPreferences = rxSharedPreferences
        /**
         * чтобы Subject не был пустым
         */
        loadMore(null, true)
    }

    override fun getObserverDataChange(): Observable<PixabayImageResponce>
            = subjectDataChange.asObservable()


    override fun loadMore(query: String?, aNew: Boolean) {
        if (query != null) rxSharedPreferences.getString(DEFAULT_TEXT_SEARCH_SP, DEFAULT_TEXT).set(query)

        if (aNew) {
            pixabayImageList.clear()
            pageNo = 1
        } else {
            pageNo++
            if (isEndItems()) {
                subjectDataChange.onNext(PixabayImageResponce(State.END_ITEMS,
                        PixabayImages(getTextQuery(), cloneCopyOnWriteArrayListToArray(pixabayImageList))))
            }
        }

        if (!(isEndItems() && !aNew))

            getData().subscribeOn(Schedulers.io())
                    .subscribe(
                            {
                                Log.d(TAG, "loadMore: next")
                                pixabayImageList.addAll(it)
                                subjectDataChange.onNext(PixabayImageResponce(
                                        if (aNew) State.NEW_ITEMS
                                        else State.NEXT_ITEMS,
                                        PixabayImages(getTextQuery(), cloneCopyOnWriteArrayListToArray(pixabayImageList))))
                            },
                            {
                                var state = State.ERROR
                                pixabayImageList.clone()

                                state.description = it.toString()
                                subjectDataChange.onNext(PixabayImageResponce(state,
                                        PixabayImages(getTextQuery(), cloneCopyOnWriteArrayListToArray(pixabayImageList))))
                            }, { })
    }


    private fun getData(): Observable<List<PixabayImage>> = api.getImages(PIXABAY_API_KEY, getTextQuery(), pageNo, pageSize)
            .map {
                total.set(it.total)
                return@map it.hits
            }
            .flatMap {
                Observable.from(it)
            }
            .map {
                PixabayImageConverter.fromNetwork(it)
            }
            .toList()


    private fun getTextQuery(): String {
        return rxSharedPreferences.getString(DEFAULT_TEXT_SEARCH_SP, DEFAULT_TEXT).get()!!

    }

    private fun isEndItems(): Boolean = (total.get() > 0 && total.get() < (pageNo - 1) * pageSize)

    private fun cloneCopyOnWriteArrayListToArray(pixabayImageList: CopyOnWriteArrayList<PixabayImage>)
            : ArrayList<PixabayImage> {
        var list: ArrayList<PixabayImage> = ArrayList<PixabayImage>()
        list.addAll(pixabayImageList)
        return list
    }

}