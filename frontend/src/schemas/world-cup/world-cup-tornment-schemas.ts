import { WordlCupCountry } from "./country-schema";
import { WorldCupMatches } from "./match-schema";

export interface WorldCup {
    id: number,
    title: string,
    imgBannerUrl: string
}

export interface DetailedWorldCup extends WorldCup {
    status: string;
    startDate: string;
    endDate: string;

    hostCountries: string;

    finalStandings: {
        firstPlace: string;
        secondPlace: string;
        thirdPlace: string;
    };

    awards: {
        bestPlayer: string | null;
        topScorer: string | null;
        topScorerGoals: number | null;
        bestGoalkeeper: string | null;
    };

    statistics: {
        totalMatches: number | null;
        totalGoals: number | null;
        attendance: string | null;
    } | null;

    groupStage: WorldCupStageGroup[];

    knockoutStage: KnockoutStage;
}

export interface WorldCupStageGroup {
    groupName: string;
    teams: WordlCupCountry[];
    matches: WorldCupMatches[]

}

export interface KnockoutStage {

    finalMatch: WorldCupMatches
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
    roundOf16: {
        matches: WorldCupMatches[]
    }

}

