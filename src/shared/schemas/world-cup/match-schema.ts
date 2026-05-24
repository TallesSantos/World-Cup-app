import { WordlCupCountry } from "./country-schema";
import { SoccerPlayer } from "./soccer-player-schema";

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

export interface Statistic {
    name: string;
    value: string
}

export interface comparativeStatistics {
    name: string,
    homeTeamValue: string,
    visitingTeamValue: string
}

export interface MatchVideo {
    title: string,
    url: string
}
