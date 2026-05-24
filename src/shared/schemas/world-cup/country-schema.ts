import { SoccerPlayer } from "./soccer-player-schema";

export interface CountryWorldCupHistory {
    worldCupYear: string;
    hostCountry: string;
    finalPosition: string;
    coach: string;
    captain: string;
    campaignSummary: string;

    statistics: {
        matches: string;
        wins: string;
        draws: string;
        losses: string;
        goalsScored: string;
        goalsConceded: string;
    };

    topScorer: {
        playerName: string;
        goals: string;
    };

    squad: SoccerPlayer[];
}


export interface CountryTitles {
    worldCups: number;
    continentalCups: number;
    confederationsCup?: number;
    olympicGoldMedals?: number;
}

export interface WordlCupCountry {
    id: string;
    name: string;
    endRatingPoint: string;
}

export interface WorldCupDetailedContry
    extends Omit<WordlCupCountry, "endRatingPoint"> {

    fifaCode: string;

    flagUrl: string;

    confederation: string;

    coach: string;

    captain: string;

    fifaRanking: string;

    worldCupAppearances: string;

    bestWorldCupFinish: string;

    titles: CountryTitles;

    allTimeStatistics: {
        matches: string;
        wins: string;
        draws: string;
        losses: string;
        goalsScored: string;
        goalsConceded: string;
    };

    allTimeSquad: {
        currentSquad: SoccerPlayer[];
        allPlayers: SoccerPlayer[];
    }

    legendaryPlayers: SoccerPlayer[];

    worldCupHistory: CountryWorldCupHistory[];
}
