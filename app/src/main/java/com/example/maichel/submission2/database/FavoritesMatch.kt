package com.example.maichel.submission2.database

data class FavoritesMatch(val matchId: String?,
                          val DateMatch: String?,
                          val TimeMatch: String?,
                          val teamHomeName: String?,
                          val teamAwayName: String?,
                          val teamHomeBadge: String?,
                          val teamAwayBadge: String?,
                          val TeamHomeScore: String?,
                          val TeamAwayScore: String?,
                          val HomeTotalShoot: String?,
                          val AwayTotalShoot: String?,
                          val HomeGoalDetail: String?,
                          val AwayGoalDetail: String?,
                          val HomeGoalKeeper: String?,
                          val AwayGoalKeeper: String?,
                          val HomeDef: String?,
                          val AwayDef: String?,
                          val HomeMid: String?,
                          val AwayMid: String?,
                          val HomeFront: String?,
                          val AwayFront: String?,
                          val HomeSubtitutes: String?,
                          val AwaySubtitutes: String?,
                          val HomeYellowCards: String?,
                          val AwayYellowCards: String?,
                          val HomeRedCards: String?,
                          val AwayRedCards: String?,
                          val TeamHomeFormation: String?,
                          val TeamAwayFormation: String?,
                          val TeamHomeId: String?,
                          val TeamAwayId: String?,
                          val Season: String?
                     ) {

    companion object {
        const val TABLE_FAVORITE: String = "TABLE_FAVORITE"
        const val MATCH_ID: String = "MATCH_ID"
        const val DATE: String = "DATE"
        const val TIME: String = "TIME"
        const val TEAM_HOME_NAME: String = "TEAM_HOME_NAME"
        const val TEAM_AWAY_NAME: String = "TEAM_AWAY_NAME"
        const val TEAM_HOME_BADGE: String = "TEAM_HOME_BADGE"
        const val TEAM_AWAY_BADGE: String = "TEAM_AWAY_BADGE"
        const val TEAM_HOME_SCORE: String = "TEAM_HOME_SCORE"
        const val TEAM_AWAY_SCORE: String = "TEAM_AWAY_SCORE"
        const val TEAM_HOME_SHOOT: String = "TEAM_HOME_SHOOT"
        const val TEAM_AWAY_SHOOT: String = "TEAM_AWAY_SHOOT"
        const val TEAM_HOME_GOAL_DETAIL: String = "TEAM_HOME_GOAL_DETAIL"
        const val TEAM_AWAY_GOAL_DETAIL: String = "TEAM_AWAY_GOAL_DETAIL"
        const val TEAM_HOME_GOAL_KEEPER: String = "TEAM_HOME_GOAL_KEEPER"
        const val TEAM_AWAY_GOAL_KEEPER: String = "TEAM_AWAY_GOAL_KEEPER"
        const val TEAM_HOME_DEF: String = "TEAM_HOME_DEF"
        const val TEAM_AWAY_DEF: String = "TEAM_AWAY_DEF"
        const val TEAM_HOME_MID: String = "TEAM_HOME_MID"
        const val TEAM_AWAY_MID: String = "TEAM_AWAY_MID"
        const val TEAM_HOME_FRONT: String = "TEAM_HOME_FRONT"
        const val TEAM_AWAY_FRONT: String = "TEAM_AWAY_FRONT"
        const val TEAM_HOME_SUBS: String = "TEAM_HOME_SUBS"
        const val TEAM_AWAY_SUBS: String = "TEAM_AWAY_SUBS"
        const val TEAM_HOME_YELLOW_CARD: String = "TEAM_HOME_YELLOW_CARD"
        const val TEAM_AWAY_YELLOW_CARD: String = "TEAM_AWAY_YELLOW_CARD"
        const val TEAM_HOME_RED_CARD: String = "TEAM_HOME_RED_CARD"
        const val TEAM_AWAY_RED_CARD: String = "TEAM_AWAY_RED_CARD"
        const val TEAM_HOME_FORMATION: String = "TEAM_HOME_FORMATION"
        const val TEAM_AWAY_FORMATION: String = "TEAM_AWAY_FORMATION"
        const val TEAM_HOME_ID: String = "TEAM_HOME_ID"
        const val TEAM_AWAY_ID: String = "TEAM_AWAY_ID"
        const val SEASON: String = "SEASON"
    }
}