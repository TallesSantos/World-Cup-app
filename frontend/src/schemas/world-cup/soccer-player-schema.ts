import { Statistic } from "./statistics-schema";

export interface Player {
    id: string;
    name: string
}

export interface CoachPlayer extends Player {
    statistics: Statistic[]
}

export interface DetailedCoachPlayer extends CoachPlayer {
    alreadSoccerPlayer: boolean;
    playerHistory?: DetailedSoccerPlayer
}

export interface SoccerPlayer extends Player {
    number: string;
    position: string;
}

export interface DetailedSoccerPlayer extends SoccerPlayer {

    fullName: string;

    nickname?: string | null;

    profileImageUrl: string;

    country: string;

    birthDate?: string | null;

    age?: number | null;

    height?: string | null;

    weight?: string | null;

    preferredFoot?: "Right" | "Left" | "Both" | null;

    currentClub?: string | null;

    marketValue?: string | null;

    captain: boolean;

    retired?: boolean | null;

    shirtHistory: {
        club: string;
        number: string;
        startYear: string;
        endYear?: string | null;
    }[];

    positionsHistory: {
        position: string;
        startYear: string;
        endYear?: string | null;
    }[];

    attributes?: {
        pace: number;
        shooting: number;
        passing: number;
        dribbling: number;
        defending: number;
        physical: number;
    } | null;

    careerStatistics: {
        matches: number;
        goals: number;
        assists?: number | null;
        yellowCards: number;
        redCards: number;
        minutesPlayed?: number | null;
    };

    nationalTeamStatistics: {
        matches: number;
        goals: number;
        assists?: number | null;
    };

    clubsHistory: {
        clubName: string;
        startYear: string;
        endYear?: string | null;
        matches: number;
        goals: number;
    }[];

    worldCupHistory: {
        year: string;
        hostCountry: string;
        finalPosition: string;
        matches: number;
        goals: number;
        assists?: number | null;
    }[];

    awards: {
        title: string;
        year: string;
    }[];

    injuriesHistory: {
        injury: string;
        startDate: string;
        endDate: string;
    }[];

    socialMedia?: {
        instagram?: string;
        twitter?: string;
        facebook?: string;
    } | null;

    biography: string;
}