package com.example.i_larin.pixabayreader.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.black_sony.testrecyclerview.core.GroopAdapter
import com.example.black_sony.testrecyclerview.core.OnlyItemAdapter
import com.example.black_sony.testrecyclerview.view.PihabayImagesTitleItemView
import com.example.i_larin.pixabayreader.BuildConfig
import com.example.i_larin.pixabayreader.R
import com.example.i_larin.pixabayreader.model.app.PixabayImages
import com.example.i_larin.pixabayreader.presentation.presenter.PixabayImagesPresenter
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView
import com.example.i_larin.pixabayreader.ui.adapter.view.PixabayImagesItemView
import kotlinx.android.synthetic.main.pxabay_images_fragment.*


/**
 * Created by i_larin on 28.01.17.
 */

class PixabayImagesFragment : MvpAppCompatFragment(), PixabayImagesView {


    lateinit var groopAdapter: GroopAdapter

    @InjectPresenter
    lateinit var presenter: PixabayImagesPresenter

    companion object {
        fun newInstance(): PixabayImagesFragment {
            return PixabayImagesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater?.inflate(R.layout.pxabay_images_fragment, container, false)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groopAdapter = GroopAdapter()

        with(pixabayImagesSwipeRefreshLayout)
        {
            setOnRefreshListener { presenter.loadData() }
            setRefreshing(true)
        }

        configureRecyclerView()

        setTitleActionBar(getString(R.string.NameFirm))

    }


    override fun showData(pixabayImage: List<PixabayImages>?) {

        pixabayImage.let {

            groopAdapter.clearData()

            for (pixabayImages in it!!) {

                var groopTitleItemView = PihabayImagesTitleItemView(pixabayImages.tag)

                var pixabayImagesItem = pixabayImages
                        .pixabayImageList
                        .map { PixabayImagesItemView(it) }

                var pixabayImagesAdapter = OnlyItemAdapter()
                pixabayImagesAdapter.addItemViews(pixabayImagesItem)

                groopAdapter.addGroopData(groopTitleItemView, pixabayImagesAdapter)
            }

        }

    }


    private fun configureRecyclerView() = with(pixabayImagesRecyclerView)
    {
        setLayoutManager(LinearLayoutManager(activity.applicationContext))
        addItemDecoration(DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL))
        setAdapter(groopAdapter)
    }


    override fun notifyUser(message: String) {
        Snackbar.make(pixabayImagesRecyclerView, message, Snackbar.LENGTH_LONG).show()
    }

    override fun stopRefresh() {
        pixabayImagesSwipeRefreshLayout.setRefreshing(false)
    }

    fun setTitleActionBar(title: String) {
        ((activity as AppCompatActivity).supportActionBar)?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.title = if (BuildConfig.DEMO_VERSION_CONTENTS) "Демо версия без поиска" else title
        }
    }

}