package com.example.i_larin.pixabayreader.repository

import android.util.Log
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.model.app.PixabayImages
import com.example.i_larin.pixabayreader.model.converter.PixabayImageConverter
import com.example.i_larin.pixabayreader.network.PixabayImageApi
import com.example.i_larin.pixabayreader.repository.model.ResponceRepository
import com.example.i_larin.pixabayreader.repository.model.StateRepository
import com.example.i_larin.pixabayreader.repository.model.StateRepository.*
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

    var tags = arrayOf("Кот",
            "Собака",
            "Города",
            "Машина",
            "Пейзаж",
            "Лето",
            "Еда",
            "Животные",
            "Растения",
            "Одежда",
            "Техника",
            "Цветы"
    )

    private val api: PixabayImageApi

    private val rxSharedPreferences: RxSharedPreferences

    private var subjectDataChange = BehaviorSubject.create<ResponceRepository>();

    private var pageNo = 1
    private var pageSize = 10


    constructor(api: PixabayImageApi, rxSharedPreferences: RxSharedPreferences) {
        this.api = api
        this.rxSharedPreferences = rxSharedPreferences
        loadData()
    }

    companion object {
        val TAG = "PixabayImageRepository"
    }


    override fun getObserverDataChange(): Observable<ResponceRepository>
            = subjectDataChange.asObservable()

    override fun loadData() {
        Log.d(TAG, "loadData: PIXABAY_API_KEY")
        Observable.from(tags)
                .subscribeOn(Schedulers.io())
                .flatMap { getData(it) }
                .toList()
                .subscribe(
                        {
                            subjectDataChange.onNext(ResponceRepository(NEW_ITEMS, it))
                        },
                        {
                            var state = ERROR
                            state.description = it.toString()
                            subjectDataChange.onNext(ResponceRepository(state, null))
                        },
                        { })

    }


    private fun getData(text: String): Observable<PixabayImages> =
            api.getImages(PIXABAY_API_KEY,
                    text,
                    pageNo,
                    pageSize)
                    .map { it.hits }
                    .flatMap { Observable.from(it) }
                    .map { PixabayImageConverter.fromNetwork(it) }
                    .toList()
                    .map { PixabayImages(text, it) }

}