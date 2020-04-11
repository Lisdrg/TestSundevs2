package com.example.testsundevs.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.testsundevs.*
import com.example.testsundevs.adapters.CharactersAdapter
import com.example.testsundevs.adapters.ConceptsAdapter
import com.example.testsundevs.adapters.LocationsAdapter
import com.example.testsundevs.adapters.TeamsAdapter
import com.example.testsundevs.models.*
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_detail_comic.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class DetailComicActivity : AppCompatActivity(), CharactersAdapter.Listener, TeamsAdapter.Listener, LocationsAdapter.Listener, ConceptsAdapter.Listener {

    private var myCompositeDisposable: CompositeDisposable? = null
    private var comicResponse: ComicDetailResponse? = null
    private var charactersAdapter: CharactersAdapter? = null
    private var detailUrl: String? = null
    private var teamsAdapter: TeamsAdapter? = null
    private var locationsAdapter: LocationsAdapter? = null
    private var conceptsAdapter: ConceptsAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_comic)
        detailUrl = intent.extras.getString("URL_DETAIL");

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        if (networkInfo != null && networkInfo.isConnected) {
            myCompositeDisposable = CompositeDisposable()
            initRecyclerViews()
            getData()
        } else {
            Toast.makeText(this, "No hay conexión a internet", Toast.LENGTH_LONG).show()

        }
    }

    private fun initRecyclerViews() {
        val layoutManagerCharacters: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvCharacters.layoutManager = layoutManagerCharacters

        val layoutManagerTeams: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvTeams.layoutManager = layoutManagerTeams

        val layoutManagerLocations: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvLocations.layoutManager = layoutManagerLocations

        val layoutManagerConcepts: RecyclerView.LayoutManager = LinearLayoutManager(this)
        rvConcepts.layoutManager = layoutManagerConcepts

    }

    private fun getData() {
        val requestInterface = Retrofit.Builder()
            .baseUrl("$detailUrl")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(GetData::class.java)

        myCompositeDisposable?.add(
            requestInterface.getDetailComic()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse)
        )
    }


    private fun handleResponse(comicResponseData: ComicDetailResponse) {
        comicResponse = comicResponseData

        charactersAdapter = CharactersAdapter(
            comicResponse?.results?.character_credits,
            this
        )
        rvCharacters.adapter = charactersAdapter

        teamsAdapter = TeamsAdapter(
            comicResponse?.results?.team_credits,
            this
        )
        rvTeams.adapter = teamsAdapter

        locationsAdapter = LocationsAdapter(
            comicResponse?.results?.location_credits,
            this
        )
        rvLocations.adapter = locationsAdapter

        conceptsAdapter = ConceptsAdapter(
            comicResponse?.results?.concept_credits,
            this
        )
        rvConcepts.adapter = conceptsAdapter


        Picasso.get()
            .load(comicResponse!!.results.image.original_url)
            .into(ivComicDetail)
    }

    override fun onItemClick(result: CharacterCredit) {

    }

    override fun onItemClick(result: TeamsCredits) {
    }

    override fun onItemClick(result: LocationCredits) {

    }

    override fun onItemClick(result: ConceptsCredits) {

    }
}

