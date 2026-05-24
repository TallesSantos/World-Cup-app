export interface Player {
    id: string;
    name: string
}

export interface CoachPlayer extends Player {
}

export interface SoccerPlayer extends Player {
    number: string;
    position: string;
}

export interface DetailedSoccerPlayer extends SoccerPlayer {

    fullName: string;

    nickname?: string;

    profileImageUrl: string;

    country: string;

    birthDate: string;

    age: number;

    height: string;

    weight: string;

    preferredFoot: "Right" | "Left" | "Both";

    currentClub: string;

    marketValue: string;

    captain: boolean;

    retired: boolean;

    shirtHistory: {
        club: string;
        number: string;
        startYear: string;
        endYear?: string;
    }[];

    positionsHistory: {
        position: string;
        startYear: string;
        endYear?: string;
    }[];

    attributes: {
        pace: number;
        shooting: number;
        passing: number;
        dribbling: number;
        defending: number;
        physical: number;
    };

    careerStatistics: {
        matches: string;
        goals: string;
        assists: string;
        yellowCards: string;
        redCards: string;
        minutesPlayed: string;
    };

    nationalTeamStatistics: {
        matches: string;
        goals: string;
        assists: string;
    };

    clubsHistory: {
        clubName: string;
        startYear: string;
        endYear?: string;
        matches: string;
        goals: string;
    }[];

    worldCupHistory: {
        year: string;
        hostCountry: string;
        finalPosition: string;
        matches: string;
        goals: string;
        assists: string;
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
    };

    biography: string;
}