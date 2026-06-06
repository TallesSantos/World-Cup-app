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
    id: number;
    title: string;

    fullStoragePath: string | null;
    storagePath: string | null;

    resourcePath: string | null;
    fullResourcePath: string | null;

    mediaContentType: string | null;

    mediaPlatform: MediaPlatform;

    youtubeVideoId: string | null;

    goalMetadata: GoalMetadata | null;

    youtubeUrl: string | null;
    youtubeEmbedUrl: string | null;

}

export type MediaPlatform =
    | "YOUTUBE"
    | "LOCAL"
    | "UNKNOWN";

export interface GoalMetadata {
    id: number;
    playerName: string;
    playerNationalTeam: string | null;
    againstNationalTeam: string | null;
}
