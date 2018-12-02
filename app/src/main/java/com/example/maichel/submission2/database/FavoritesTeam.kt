package com.example.maichel.submission2.database

data class FavoritesTeam (
    val teamId: String?,
    val teamName: String?,
    val teamYear: String?,
    val teamStadion: String?,
    val teamDescription: String?,
    val teamBadge: String?
){
    companion object {
        const val TABLE_TEAM_FAVORITE: String = "TABLE_TEAM_FAVORITE"
        const val TEAM_ID: String = "TEAM_ID"
        const val TEAM_NAME: String = "TEAM_NAME"
        const val TEAM_YEAR: String = "TEAM_YEAR"
        const val TEAM_STADION: String = "TEAM_STADION"
        const val TEAM_DESCRIPTION: String = "TEAM_DESC"
        const val TEAM_BADGE: String = "TEAM_BADGE"
    }
}