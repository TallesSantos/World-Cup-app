import { WordlCupCountry } from "./country-schema";
import { WorldCupMatches } from "./match-schema";

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

