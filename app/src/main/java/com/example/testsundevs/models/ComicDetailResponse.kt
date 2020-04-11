package com.example.testsundevs.models

data class ComicDetailResponse(
    val error: String,
    val limit: Int,
    val number_of_page_results: Int,
    val number_of_total_results: Int,
    val results: Results,
    val status_code: Int
)