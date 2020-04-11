package com.example.testsundevs

import com.example.testsundevs.models.ComicDetailResponse
import com.example.testsundevs.models.ComicResponse
import io.reactivex.Observable
import retrofit2.http.GET

interface GetData {

    @GET("issues/?api_key=0cf351072e6596ffe56e88d5976ba1957cc9154d&format=json")
    fun getComicList(): Observable<ComicResponse>

    @GET("?api_key=0cf351072e6596ffe56e88d5976ba1957cc9154d&format=json")
    fun getDetailComic() : Observable<ComicDetailResponse>
}