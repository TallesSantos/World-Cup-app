import { WordlCupCountry } from "./country-schema";
import { SoccerPlayer } from "./soccer-player-schema";
import { Statistic, comparativeStatistics } from "./statistics-schema";

export interface WorldCupMatches {
    id: string;
    homeTeam: WordlCupCountry;
    homeTeamScore?: string;
    visitingTeam: WordlCupCountry;
    visitingTeamScore?: string;
}

export interface WorldCupDetailedMatches extends Omit<WorldCupMatches, "id"> {
    id: string,
    stadium: string;
    basicStatistics: Statistic[],
    comparativeStatistics: comparativeStatistics[],
    homeTeamMatchPlayers: SoccerPlayer[],
    visitingTeamMatchPlayers: SoccerPlayer[],
    matchVideos: MatchVideo[]
}

export interface MatchVideo {
    title: string,
    url: string
}
