import { WorldCup } from "../../schemas/world_cup_schemas";

export const worldCupService = {
    getWolrdlCupById: getWolrdlCupById
}

async function getWolrdlCupById(id: number) {
    //mocado
    const worldCup: WorldCup = MOCK
    return worldCup;
}


const MOCK: WorldCup = {
    title: "FIFA World Cup Qatar 2022",

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
