package com.example.testsundevs.models

import com.example.testsundevs.models.*

data class Results (
    val character_credits : List<CharacterCredit>,
    val team_credits : List<TeamsCredits>,
    val location_credits : List<LocationCredits>,
    val concept_credits: List<ConceptsCredits>?,
    val image: Image

)