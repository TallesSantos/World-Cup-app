import { DetailedSoccerPlayer } from "../../schemas/world-cup/soccer-player-schema";

export const playerApiClient = {
    getDetailedPlayerById: getDetailedPlayerById
}

export async function getDetailedPlayerById(id: string) {
    const player = MESSI;
    return player;

}

const MESSI: DetailedSoccerPlayer = {
    id: "messi",

    name: "Messi",

    fullName: "Lionel Andrés Messi",

    nickname: "La Pulga",

    profileImageUrl:
        "https://example.com/messi.png",

    number: "10",

    position: "Forward",

    country: "Argentina",

    birthDate: "24/06/1987",

    age: 38,

    height: "1.70m",

    weight: "72kg",

    preferredFoot: "Left",

    currentClub: "Inter Miami",

    marketValue: "25M €",

    captain: true,

    retired: false,

    shirtHistory: [
        {
            club: "Barcelona",
            number: "10",
            startYear: "2008",
            endYear: "2021",
        },
        {
            club: "Paris Saint-Germain",
            number: "30",
            startYear: "2021",
            endYear: "2023",
        },
    ],

    positionsHistory: [
        {
            position: "Right Winger",
            startYear: "2004",
            endYear: "2012",
        },
        {
            position: "False 9",
            startYear: "2012",
        },
    ],

    attributes: {
        pace: 85,
        shooting: 92,
        passing: 91,
        dribbling: 96,
        defending: 38,
        physical: 65,
    },

    careerStatistics: {
        matches: "1080",
        goals: "850",
        assists: "380",
        yellowCards: "110",
        redCards: "3",
        minutesPlayed: "89000",
    },

    nationalTeamStatistics: {
        matches: "191",
        goals: "112",
        assists: "58",
    },

    clubsHistory: [
        {
            clubName: "Barcelona",
            startYear: "2004",
            endYear: "2021",
            matches: "778",
            goals: "672",
        },
        {
            clubName: "Paris Saint-Germain",
            startYear: "2021",
            endYear: "2023",
            matches: "75",
            goals: "32",
        },
        {
            clubName: "Inter Miami",
            startYear: "2023",
            matches: "50",
            goals: "42",
        },
    ],

    worldCupHistory: [
        {
            year: "2006",
            hostCountry: "Germany",
            finalPosition: "Quarter-finals",
            matches: "3",
            goals: "1",
            assists: "1",
        },
        {
            year: "2014",
            hostCountry: "Brazil",
            finalPosition: "Runner-up",
            matches: "7",
            goals: "4",
            assists: "1",
        },
        {
            year: "2022",
            hostCountry: "Qatar",
            finalPosition: "Champion",
            matches: "7",
            goals: "7",
            assists: "3",
        },
    ],

    awards: [
        {
            title: "Ballon d'Or",
            year: "2023",
        },
        {
            title: "FIFA World Cup",
            year: "2022",
        },
        {
            title: "Copa América",
            year: "2021",
        },
    ],

    injuriesHistory: [
        {
            injury: "Hamstring injury",
            startDate: "10/01/2021",
            endDate: "28/01/2021",
        },
    ],

    socialMedia: {
        instagram: "@leomessi",
    },

    biography:
        "Lionel Messi is considered one of the greatest football players in history. He won the FIFA World Cup with Argentina in 2022 and multiple Ballon d'Or awards during his career.",
};