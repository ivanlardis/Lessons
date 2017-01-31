package com.example.i_larin.pixabayreader.ui.fragment

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.MenuItemCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.example.i_larin.pixabayreader.R
import com.example.i_larin.pixabayreader.model.app.PixabayImage
import com.example.i_larin.pixabayreader.presentation.presenter.PixabayImagesPresenter
import com.example.i_larin.pixabayreader.presentation.view.PixabayImagesView
import com.jcodecraeer.xrecyclerview.XRecyclerView
import jp.wasabeef.recyclerview.animators.LandingAnimator
import kotlinx.android.synthetic.main.pxabay_images_fragment.*


/**
 * Created by i_larin on 28.01.17.
 */
class PixabayImagesFragment : Fragment(), PixabayImagesView, XRecyclerView.LoadingListener, SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String): Boolean = false


    override fun onQueryTextChange(newText: String): Boolean {

        Log.d("TAG", "onQueryTextChange: adapter")
        if(newText.length>2)presenter.loadData(newText)
        return false
    }

    override fun showData(pixabayImage: List<PixabayImage>) {
        adapter.updatePixabayImage(pixabayImage)
    }

    override fun showMoreData(pixabayImage: List<PixabayImage>) {
        adapter.addPixabayImage(pixabayImage)
    }

    override fun showLoadingMoreProgress(show: Boolean) {
        if (!show) {
            pixabayImagesRecyclerView.loadMoreComplete()
        } else {

            pixabayImagesRecyclerView.setLoadingMoreEnabled(false)
        }

    }

    override fun showPullRefreshEnabled(show: Boolean) {
        if (!show) {
            pixabayImagesRecyclerView.refreshComplete()
        } else {

            pixabayImagesRecyclerView.setPullRefreshEnabled(true)
        }

    }


    override fun onLoadMore() {
        presenter.loadDataMore()
    }

    override fun onRefresh() {
        presenter.loadData(null)
    }


    companion object {
        fun newInstance(): PixabayImagesFragment {
            return PixabayImagesFragment()
        }
    }


    lateinit var adapter: PixabayImagesAdapter
    var presenter = PixabayImagesPresenter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true);
        //TODO !!  - это принудительный каст к not null. его лучше не использовать вообще
        //тут можно сделать просто inflater?.inflate(R.layout.pxabay_images_fragment, container, false)
        return inflater!!.inflate(R.layout.pxabay_images_fragment, container, false)
    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.pixabay_images, menu)
        val menuItem = menu?.findItem(R.id.action_search)
        val sv = MenuItemCompat.getActionView(menuItem) as? SearchView
        sv?.setOnQueryTextListener(this)
    }


    override fun setTitleActionBar(title: String) {
        val actionBar = (activity as AppCompatActivity).supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false)
            actionBar.title = title
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.attachView(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.detachView()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureRecyclerViewAndAdapter()

    }


    private fun configureRecyclerViewAndAdapter() {
        adapter = PixabayImagesAdapter(this)
        pixabayImagesRecyclerView.setLayoutManager(LinearLayoutManager(activity.applicationContext))
        pixabayImagesRecyclerView.setItemAnimator(LandingAnimator())
        pixabayImagesRecyclerView.addItemDecoration(DividerItemDecoration(activity,
                DividerItemDecoration.VERTICAL))
        pixabayImagesRecyclerView.setAdapter(adapter)
        pixabayImagesRecyclerView.setLoadingListener(this)
        pixabayImagesRecyclerView.refresh()
    }


    override fun notifyUser(message: String) {
        Snackbar.make(pixabayImagesRecyclerView, message, Snackbar.LENGTH_LONG).show()
    }

}