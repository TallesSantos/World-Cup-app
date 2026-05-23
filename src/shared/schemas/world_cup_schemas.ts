export interface WorldCup {

    title: string;
    statistics: {
        fistPlace: string,
        secondPlace: string,
        thirdPlace: string,
        hostCountry: string,
        bestPlayer: string,
        topScore: string,
        topScoreNumber: string
        bestGoalkeeper: string
    },
    groupStage: WorldCupStageGroup[]
    knockoutStage: KnockoutStage


}

export interface WorldCupStageGroup {
    groupName: string;
    teams: WordlCupCountry[];
    matches: WorldCupMatches[]

}

export interface WordlCupCountry {
    id: string
    name: string
    endRatingPoint: string
}

export interface WorldCupMatches {
    id: string
    homeTeam: WordlCupCountry,
    homeTeamScore?: string
    visitingTeam: WordlCupCountry
    visitingTeamScore?: string
}

export interface KnockoutStage {

    grandFinal: WorldCupMatches
    semiFinals: {
        match1: WorldCupMatches,
        match2: WorldCupMatches,
        thirdPlace: WorldCupMatches
    }

    quarterFinals: {
        match1: WorldCupMatches,
        match2: WorldCupMatches,
        match3: WorldCupMatches,
        match4: WorldCupMatches
    },
    RoundOf16: {
        matchs: WorldCupMatches[]
    }

}