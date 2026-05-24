import { WorldCupDetailedContry } from "../../schemas/world-cup/country-schema";
import { WorldCupDetailedMatches } from "../../schemas/world-cup/match-schema";
import { DetailedWorldCup, WorldCup } from "../../schemas/world-cup/world-cup-tornment-schemas";

export const worldCupApiClient = {
    listWordlCups: listWordlCups,
    getWolrdlCupById: getWolrdlCupById,
    getMatchById: getMatchById,
    getContryDataById: getContryDataById
}

async function listWordlCups() {
    //mocado
    const WordlCups: WorldCup[] = [{ id: MOCK.id, title: MOCK.title, imgBannerUrl: MOCK.imgBannerUrl }]
    return WordlCups;
}

async function getWolrdlCupById(id: number) {
    //mocado
    const worldCup: DetailedWorldCup = MOCK
    return worldCup;
}

async function getMatchById(id: string) {

    //mocado
    const match: WorldCupDetailedMatches = MOCK_MATCH;
    return match;
}

async function getContryDataById(id: string) {
    //mocado

    const country: WorldCupDetailedContry = MOCK_COUNTRY
    return country;
}

const MOCK_COUNTRY: WorldCupDetailedContry = {
    id: "argentina",

    name: "Argentina",

    fifaCode: "ARG",

    flagUrl: "https://flagcdn.com/ar.svg",

    confederation: "CONMEBOL",

    coach: "Lionel Scaloni",

    captain: { id: "1", name: "Lionel Messi", number: "10", position: "Forward" },

    fifaRanking: "1",

    worldCupAppearances: "18",

    bestWorldCupFinish: "Champion",

    titles: {
        worldCups: 3,
        continentalCups: 15,
        confederationsCup: 1,
        olympicGoldMedals: 2,
    },

    allTimeStatistics: {
        matches: "88",
        wins: "47",
        draws: "17",
        losses: "24",
        goalsScored: "152",
        goalsConceded: "101",
    },

    allTimeSquad: {
        currentSquad: [
            {
                id: "messi",
                name: "Lionel Messi",
                number: "10",
                position: "Forward",
            },
            {
                id: "julian-alvarez",
                name: "Julián Álvarez",
                number: "9",
                position: "Forward",
            },
            {
                id: "enzo-fernandez",
                name: "Enzo Fernández",
                number: "24",
                position: "Midfielder",
            },
        ],

        allPlayers: [
            {
                id: "messi",
                name: "Lionel Messi",
                number: "10",
                position: "Forward",
            },
            {
                id: "maradona",
                name: "Diego Maradona",
                number: "10",
                position: "Midfielder",
            },
            {
                id: "batistuta",
                name: "Gabriel Batistuta",
                number: "9",
                position: "Striker",
            },
            {
                id: "riquelme",
                name: "Juan Román Riquelme",
                number: "8",
                position: "Midfielder",
            },
        ],
    },

    legendaryPlayers: [
        {
            id: "maradona",
            name: "Diego Maradona",
            number: "10",
            position: "Midfielder",
        },
        {
            id: "messi",
            name: "Lionel Messi",
            number: "10",
            position: "Forward",
        },
        {
            id: "batistuta",
            name: "Gabriel Batistuta",
            number: "9",
            position: "Striker",
        },
    ],

    worldCupHistory: [
        {
            worldCupYear: "1978",

            hostCountry: "Argentina",

            finalPosition: "Champion",

            coach: "César Luis Menotti",

            captain: { id: '1', name: "Daniel Passarella", number: '8', position: "Midfield" },

            campaignSummary:
                "Argentina won its first FIFA World Cup title by defeating the Netherlands in the final.",

            statistics: {
                matches: "7",
                wins: "5",
                draws: "1",
                losses: "1",
                goalsScored: "15",
                goalsConceded: "4",
            },

            topScorer: {
                player: { id: '1', name: "Mario Kempes", number: '10', position: "Forward" },
                goals: "6",
            },

            squad: [
                {
                    id: "kempes",
                    name: "Mario Kempes",
                    number: "10",
                    position: "Forward",
                },
                {
                    id: "passarella",
                    name: "Daniel Passarella",
                    number: "19",
                    position: "Defender",
                },
            ],
        },

        {
            worldCupYear: "1986",

            hostCountry: "Mexico",

            finalPosition: "Champion",

            coach: "Carlos Bilardo",

            captain: { id: '1', name: "Diego Maradona", number: '10', position: "Midfielder" },

            campaignSummary:
                "Led by Diego Maradona, Argentina defeated West Germany in the final and secured its second world title.",

            statistics: {
                matches: "7",
                wins: "6",
                draws: "1",
                losses: "0",
                goalsScored: "14",
                goalsConceded: "5",
            },

            topScorer: {
                player: { id: '1', name: "Jorge Valdano", number: '11', position: "Forward" },
                goals: "4",
            },

            squad: [
                {
                    id: "maradona",
                    name: "Diego Maradona",
                    number: "10",
                    position: "Midfielder",
                },
                {
                    id: "valdano",
                    name: "Jorge Valdano",
                    number: "11",
                    position: "Forward",
                },
            ],
        },

        {
            worldCupYear: "2022",

            hostCountry: "Qatar",

            finalPosition: "Champion",

            coach: "Lionel Scaloni",

            captain: { id: '1', name: "Lionel Messi", number: '10', position: 'Forward' },

            campaignSummary:
                "Argentina won the World Cup after defeating France on penalties in one of the greatest finals in football history.",

            statistics: {
                matches: "7",
                wins: "6",
                draws: "0",
                losses: "1",
                goalsScored: "15",
                goalsConceded: "8",
            },

            topScorer: {
                player: { name: "Lionel Messi", id: '1', number: '10', position: 'Forward' },
                goals: "7",
            },

            squad: [
                {
                    id: "messi",
                    name: "Lionel Messi",
                    number: "10",
                    position: "Forward",
                },
                {
                    id: "julian-alvarez",
                    name: "Julián Álvarez",
                    number: "9",
                    position: "Forward",
                },
                {
                    id: "enzo-fernandez",
                    name: "Enzo Fernández",
                    number: "24",
                    position: "Midfielder",
                },
                {
                    id: "emiliano-martinez",
                    name: "Emiliano Martínez",
                    number: "23",
                    position: "Goalkeeper",
                },
            ],
        },
    ],
};

const MOCK_MATCH: WorldCupDetailedMatches = {
    id: "final-2022",

    homeTeam: {
        id: "argentina",
        name: "Argentina",
        endRatingPoint: "Champion",
    },

    homeTeamScore: "3",

    visitingTeam: {
        id: "france",
        name: "France",
        endRatingPoint: "Runner-up",
    },

    visitingTeamScore: "3",

    stadium: "Lusail Stadium",

    basicStatistics: [
        {
            name: "Competition",
            value: "FIFA World Cup Qatar 2022",
        },
        {
            name: "Stage",
            value: "Final",
        },
        {
            name: "Date",
            value: "18 December 2022",
        },
        {
            name: "Attendance",
            value: "88,966",
        },
    ],

    comparativeStatistics: [
        {
            name: "Possession",
            homeTeamValue: "54%",
            visitingTeamValue: "46%",
        },
        {
            name: "Shots",
            homeTeamValue: "20",
            visitingTeamValue: "10",
        },
        {
            name: "Shots on target",
            homeTeamValue: "9",
            visitingTeamValue: "5",
        },
        {
            name: "Corners",
            homeTeamValue: "6",
            visitingTeamValue: "5",
        },
    ],

    homeTeamMatchPlayers: [
        {
            id: "messi",
            name: "Lionel Messi",
            number: "10",
            position: "Forward",
        },
        {
            id: "alvarez",
            name: "Julián Álvarez",
            number: "9",
            position: "Forward",
        },
        {
            id: "dimaria",
            name: "Ángel Di María",
            number: "11",
            position: "Winger",
        },
    ],

    visitingTeamMatchPlayers: [
        {
            id: "mbappe",
            name: "Kylian Mbappé",
            number: "10",
            position: "Forward",
        },
        {
            id: "griezmann",
            name: "Antoine Griezmann",
            number: "7",
            position: "Midfielder",
        },
        {
            id: "giroud",
            name: "Olivier Giroud",
            number: "9",
            position: "Striker",
        },
    ],

    matchVideos: [{
        title: "Argentina vs France | Final Highlights | FIFA World Cup Qatar 2022",
        url: "aqz-KE-bpKQ",
        plataform: "youtube",
    }, {
        title: "Argentina vs France | Final Highlights | FIFA World Cup Qatar 2022 2",
        url: "aqz-KE-bpKQ",
        plataform: "youtube",
    }],
};

const MOCK: DetailedWorldCup = {
    id: '01',
    title: "FIFA World Cup Qatar 2022",
    imgBannerUrl: "world-cup-2022-banner.jpg",
    statistics: {
        fistPlace: "Argentina",
        secondPlace: "France",
        thirdPlace: "Croatia",
        hostCountry: "Qatar",
        bestPlayer: "Lionel Messi",
        topScore: "Kylian Mbappé",
        topScoreNumber: "8",
        bestGoalkeeper: "Emiliano Martínez",
    },

    groupStage: [
        {
            groupName: "Group A",

            teams: [
                { id: "netherlands", name: "Netherlands", endRatingPoint: "7" },
                { id: "senegal", name: "Senegal", endRatingPoint: "6" },
                { id: "ecuador", name: "Ecuador", endRatingPoint: "4" },
                { id: "qatar", name: "Qatar", endRatingPoint: "0" },
            ],

            matches: [
                {
                    id: "a1",
                    homeTeam: {
                        id: "qatar",
                        name: "Qatar",
                        endRatingPoint: "0",
                    },
                    homeTeamScore: "0",

                    visitingTeam: {
                        id: "ecuador",
                        name: "Ecuador",
                        endRatingPoint: "4",
                    },
                    visitingTeamScore: "2",
                },

                {
                    id: "a2",
                    homeTeam: {
                        id: "senegal",
                        name: "Senegal",
                        endRatingPoint: "6",
                    },
                    homeTeamScore: "0",

                    visitingTeam: {
                        id: "netherlands",
                        name: "Netherlands",
                        endRatingPoint: "7",
                    },
                    visitingTeamScore: "2",
                },

                {
                    id: "a3",
                    homeTeam: {
                        id: "qatar",
                        name: "Qatar",
                        endRatingPoint: "0",
                    },
                    homeTeamScore: "1",

                    visitingTeam: {
                        id: "senegal",
                        name: "Senegal",
                        endRatingPoint: "6",
                    },
                    visitingTeamScore: "3",
                },

                {
                    id: "a4",
                    homeTeam: {
                        id: "netherlands",
                        name: "Netherlands",
                        endRatingPoint: "7",
                    },
                    homeTeamScore: "1",

                    visitingTeam: {
                        id: "ecuador",
                        name: "Ecuador",
                        endRatingPoint: "4",
                    },
                    visitingTeamScore: "1",
                },

                {
                    id: "a5",
                    homeTeam: {
                        id: "ecuador",
                        name: "Ecuador",
                        endRatingPoint: "4",
                    },
                    homeTeamScore: "1",

                    visitingTeam: {
                        id: "senegal",
                        name: "Senegal",
                        endRatingPoint: "6",
                    },
                    visitingTeamScore: "2",
                },

                {
                    id: "a6",
                    homeTeam: {
                        id: "netherlands",
                        name: "Netherlands",
                        endRatingPoint: "7",
                    },
                    homeTeamScore: "2",

                    visitingTeam: {
                        id: "qatar",
                        name: "Qatar",
                        endRatingPoint: "0",
                    },
                    visitingTeamScore: "0",
                },
            ],
        },

        {
            groupName: "Group B",

            teams: [
                { id: "england", name: "England", endRatingPoint: "7" },
                { id: "usa", name: "USA", endRatingPoint: "5" },
                { id: "iran", name: "Iran", endRatingPoint: "3" },
                { id: "wales", name: "Wales", endRatingPoint: "1" },
            ],

            matches: [
                {
                    id: "b1",
                    homeTeam: {
                        id: "england",
                        name: "England",
                        endRatingPoint: "7",
                    },
                    homeTeamScore: "6",

                    visitingTeam: {
                        id: "iran",
                        name: "Iran",
                        endRatingPoint: "3",
                    },
                    visitingTeamScore: "2",
                },

                {
                    id: "b2",
                    homeTeam: {
                        id: "usa",
                        name: "USA",
                        endRatingPoint: "5",
                    },
                    homeTeamScore: "1",

                    visitingTeam: {
                        id: "wales",
                        name: "Wales",
                        endRatingPoint: "1",
                    },
                    visitingTeamScore: "1",
                },

                {
                    id: "b3",
                    homeTeam: {
                        id: "wales",
                        name: "Wales",
                        endRatingPoint: "1",
                    },
                    homeTeamScore: "0",

                    visitingTeam: {
                        id: "iran",
                        name: "Iran",
                        endRatingPoint: "3",
                    },
                    visitingTeamScore: "2",
                },

                {
                    id: "b4",
                    homeTeam: {
                        id: "england",
                        name: "England",
                        endRatingPoint: "7",
                    },
                    homeTeamScore: "0",

                    visitingTeam: {
                        id: "usa",
                        name: "USA",
                        endRatingPoint: "5",
                    },
                    visitingTeamScore: "0",
                },

                {
                    id: "b5",
                    homeTeam: {
                        id: "wales",
                        name: "Wales",
                        endRatingPoint: "1",
                    },
                    homeTeamScore: "0",

                    visitingTeam: {
                        id: "england",
                        name: "England",
                        endRatingPoint: "7",
                    },
                    visitingTeamScore: "3",
                },

                {
                    id: "b6",
                    homeTeam: {
                        id: "iran",
                        name: "Iran",
                        endRatingPoint: "3",
                    },
                    homeTeamScore: "0",

                    visitingTeam: {
                        id: "usa",
                        name: "USA",
                        endRatingPoint: "5",
                    },
                    visitingTeamScore: "1",
                },
            ],
        },
    ],

    knockoutStage: {
        grandFinal: {
            id: "final",

            homeTeam: {
                id: "argentina",
                name: "Argentina",
                endRatingPoint: "Champion",
            },
            homeTeamScore: "3",

            visitingTeam: {
                id: "france",
                name: "France",
                endRatingPoint: "Runner-up",
            },
            visitingTeamScore: "3",
        },

        semiFinals: {
            match1: {
                id: "sf1",

                homeTeam: {
                    id: "argentina",
                    name: "Argentina",
                    endRatingPoint: "3",
                },
                homeTeamScore: "3",

                visitingTeam: {
                    id: "croatia",
                    name: "Croatia",
                    endRatingPoint: "0",
                },
                visitingTeamScore: "0",
            },

            match2: {
                id: "sf2",

                homeTeam: {
                    id: "france",
                    name: "France",
                    endRatingPoint: "2",
                },
                homeTeamScore: "2",

                visitingTeam: {
                    id: "morocco",
                    name: "Morocco",
                    endRatingPoint: "0",
                },
                visitingTeamScore: "0",
            },

            thirdPlace: {
                id: "third-place",

                homeTeam: {
                    id: "croatia",
                    name: "Croatia",
                    endRatingPoint: "2",
                },
                homeTeamScore: "2",

                visitingTeam: {
                    id: "morocco",
                    name: "Morocco",
                    endRatingPoint: "1",
                },
                visitingTeamScore: "1",
            },
        },

        quarterFinals: {
            match1: {
                id: "qf1",

                homeTeam: {
                    id: "croatia",
                    name: "Croatia",
                    endRatingPoint: "1",
                },
                homeTeamScore: "1",

                visitingTeam: {
                    id: "brazil",
                    name: "Brazil",
                    endRatingPoint: "1",
                },
                visitingTeamScore: "1",
            },

            match2: {
                id: "qf2",

                homeTeam: {
                    id: "argentina",
                    name: "Argentina",
                    endRatingPoint: "2",
                },
                homeTeamScore: "2",

                visitingTeam: {
                    id: "netherlands",
                    name: "Netherlands",
                    endRatingPoint: "2",
                },
                visitingTeamScore: "2",
            },

            match3: {
                id: "qf3",

                homeTeam: {
                    id: "morocco",
                    name: "Morocco",
                    endRatingPoint: "1",
                },
                homeTeamScore: "1",

                visitingTeam: {
                    id: "portugal",
                    name: "Portugal",
                    endRatingPoint: "0",
                },
                visitingTeamScore: "0",
            },

            match4: {
                id: "qf4",

                homeTeam: {
                    id: "france",
                    name: "France",
                    endRatingPoint: "2",
                },
                homeTeamScore: "2",

                visitingTeam: {
                    id: "england",
                    name: "England",
                    endRatingPoint: "1",
                },
                visitingTeamScore: "1",
            },
        },

        RoundOf16: {
            matchs: [
                {
                    id: "r16-1",

                    homeTeam: {
                        id: "netherlands",
                        name: "Netherlands",
                        endRatingPoint: "3",
                    },
                    homeTeamScore: "3",

                    visitingTeam: {
                        id: "usa",
                        name: "USA",
                        endRatingPoint: "1",
                    },
                    visitingTeamScore: "1",
                },

                {
                    id: "r16-2",

                    homeTeam: {
                        id: "argentina",
                        name: "Argentina",
                        endRatingPoint: "2",
                    },
                    homeTeamScore: "2",

                    visitingTeam: {
                        id: "australia",
                        name: "Australia",
                        endRatingPoint: "1",
                    },
                    visitingTeamScore: "1",
                },

                {
                    id: "r16-3",

                    homeTeam: {
                        id: "france",
                        name: "France",
                        endRatingPoint: "3",
                    },
                    homeTeamScore: "3",

                    visitingTeam: {
                        id: "poland",
                        name: "Poland",
                        endRatingPoint: "1",
                    },
                    visitingTeamScore: "1",
                },

                {
                    id: "r16-4",

                    homeTeam: {
                        id: "england",
                        name: "England",
                        endRatingPoint: "3",
                    },
                    homeTeamScore: "3",

                    visitingTeam: {
                        id: "senegal",
                        name: "Senegal",
                        endRatingPoint: "0",
                    },
                    visitingTeamScore: "0",
                },
            ],
        },
    },
};
