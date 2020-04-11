package com.example.testsundevs.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testsundevs.GetData
import com.example.testsundevs.R
import com.example.testsundevs.adapters.MainAdapter
import com.example.testsundevs.models.ComicResponse
import com.example.testsundevs.models.Result
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(),
    MainAdapter.Listener {
    val BASE_URL = "http://comicvine.gamespot.com/api/"
    private var myCompositeDisposable: CompositeDisposable? = null
    private var comicResponse: ComicResponse? = null
    private var myAdapter: MainAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            myCompositeDisposable = CompositeDisposable()
            initRecyclerView()
            getData()
        } else {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show()
        }
    }

    private fun initRecyclerView() {
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvComic.layoutManager = layoutManager
    }
    private fun getData() {
        val requestInterface = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetData::class.java)

        myCompositeDisposable?.add(
            requestInterface.getComicList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }

    private fun handleResponse(comicResponseData: ComicResponse) {
        progressBarMain.visibility= View.GONE
        comicResponse = comicResponseData
        myAdapter =
            MainAdapter(
                comicResponse?.results,
                this
            )
        rvComic.adapter = myAdapter
    }

    override fun onItemClick(result: Result) {
        Toast.makeText(this, result.volume.name, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, DetailComicActivity::class.java)
        intent.putExtra("URL_DETAIL",result.api_detail_url)
        startActivity(intent)

    }

    override fun onDestroy() {
        super.onDestroy()
        myCompositeDisposable?.clear()
    }
}


