package com.example.testsundevs.models

data class ComicResponse(
    val error: String,
    val limit: Int,
    val number_of_page_results: Int,
    val number_of_total_results: Int,
    val results: List<Result>,
    val status_code: Int
)