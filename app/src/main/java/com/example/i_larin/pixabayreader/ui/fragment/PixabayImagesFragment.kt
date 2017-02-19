package com.example.i_larin.pixabayreader.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.*
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.i_larin.pixabayreader.BuildConfig
import com.example.i_larin.pixabayreader.PixabayReaderApplication
import com.example.i_larin.pixabayreader.R
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.presentation.presenter.PixabayImagesPresenter
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView.State
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView.State.*
import com.jcodecraeer.xrecyclerview.XRecyclerView
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.pxabay_images_fragment.*


/**
 * Created by i_larin on 28.01.17.
 */

class PixabayImagesFragment : MvpAppCompatFragment(), PixabayImagesView, XRecyclerView.LoadingListener, SearchView.OnQueryTextListener {
    lateinit var adapter: PixabayImagesAdapter
    @InjectPresenter
    lateinit var presenter: PixabayImagesPresenter

    companion object {
        fun newInstance(): PixabayImagesFragment {
            return PixabayImagesFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (PixabayReaderApplication.isDemoContent()) setHasOptionsMenu(true);
        return inflater?.inflate(R.layout.pxabay_images_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.pixabay_images, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val sv = MenuItemCompat.getActionView(menuItem) as? SearchView
        sv?.setOnQueryTextListener(this)
    }


    override fun setTitleActionBar(title: String) {
        ((activity as AppCompatActivity).supportActionBar)?.let {
            it.setDisplayHomeAsUpEnabled(false)
            it.title = if (BuildConfig.DEMO_VERSION_CONTENTS) "Демо версия без поиска" else title
        }
    }

    override fun onQueryTextSubmit(query: String) = false

    override fun onQueryTextChange(newText: String): Boolean {
        if (newText.length > 2) presenter.loadData(newText)
        return false
    }

    override fun showData(state: State, pixabayImage: List<PixabayImage>) = when (state) {
        SHOW -> adapter.updatePixabayImage(pixabayImage)
        SHOW_MORE -> adapter.addPixabayImage(pixabayImage)
        SHOW_IS_DATA_NULL -> adapter.updateIsNullPixabayImage(pixabayImage)
    }

    override fun showLoadingMoreProgress(show: Boolean) = with(pixabayImagesRecyclerView) {
        if (show) onLoadMoreWithoutListener()
        else loadMoreComplete()
    }

    override fun showPullRefreshEnabled(show: Boolean) = with(pixabayImagesRecyclerView) {
        if (show) refreshWithoutListener()
        else refreshComplete()
    }

    override fun onLoadMore() {
        presenter.loadDataMore()
    }

    override fun onRefresh() {
        presenter.loadData(null)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerViewAndAdapter()
    }

    private fun configureRecyclerViewAndAdapter() {
        adapter = PixabayImagesAdapter(this)
        with(pixabayImagesRecyclerView)
        {
            setLayoutManager(LinearLayoutManager(activity.applicationContext))
            setItemAnimator(LandingAnimator())
            addItemDecoration(DividerItemDecoration(activity,
                    DividerItemDecoration.VERTICAL))
        }
        pixabayImagesRecyclerView.setAdapter(adapter)
        pixabayImagesRecyclerView.setLoadingListener(this)
    }

    override fun notifyUser(message: String) {
        Snackbar.make(pixabayImagesRecyclerView, message, Snackbar.LENGTH_LONG).show()
    }
}