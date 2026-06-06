import { ThemedText } from "@/src/components/common/themed-text";
import { ThemedView } from "@/src/components/common/themed-view";
import CountryIcon from "@/src/components/icons/country-icon";
import SoccerPlayerLink from "@/src/components/links/soccer-player-link";
import { WorldCupDetailedContry } from "@/src/schemas/world-cup/country-schema";
import { worldCupApiClient } from "@/src/services/api-services/world-cup-api-client";
import { useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet } from "react-native";

export default function ContryPage() {
    const { id } = useLocalSearchParams();
    const [countryData, setCountryData] = useState<WorldCupDetailedContry | null>(null);

    async function loadData() {
        const countryDataResponse = await worldCupApiClient.getContryDataById(id as string);
        setCountryData(countryDataResponse)
    }

    useEffect(() => {
        loadData()
    }, [])

    return (<ScrollView>
        <ThemedView style={
            {
                flex: 1,
                flexWrap: "wrap",
                alignItems: "center",
                justifyContent: "center",
                padding: 16,
                paddingTop: 48,
                width: "100%",
                gap: 12,
            }
        } >
            {!countryData

                ? <ThemedView style={[styles.titleContainer, styles.flexBox]} >
                    <ThemedText type="title">Loading...</ThemedText>
                </ThemedView>

                : <ThemedView style={[styles.titleContainer, styles.flexBox, { flexWrap: "wrap" }]} >
                    <ThemedText type="title">
                        <CountryIcon country={countryData} />
                        {countryData.name}
                    </ThemedText>

                    <ThemedView
                        style={{
                            padding: 16,
                            gap: 16,
                        }}
                    >

                        <ThemedText>
                            FIFA Code: {countryData.fifaCode}
                        </ThemedText>

                        <ThemedText>
                            Confederation: {countryData.confederation}
                        </ThemedText>

                        <ThemedText>
                            Coach: {countryData.coach}
                        </ThemedText>

                        <ThemedText>
                            Captain: {countryData.captain.name}
                        </ThemedText>

                        <ThemedText>
                            FIFA Ranking: {countryData.fifaRanking}
                        </ThemedText>

                        <ThemedText>
                            World Cup Appearances: {countryData.worldCupAppearances}
                        </ThemedText>

                        <ThemedText>
                            Best World Cup Finish: {countryData.bestWorldCupFinish}
                        </ThemedText>

                        <ThemedView
                            style={{
                                gap: 8,
                                borderWidth: 1,
                                borderColor: "gray",
                                padding: 12,
                                borderRadius: 8,
                            }}
                        >
                            <ThemedText type="subtitle">
                                Titles
                            </ThemedText>

                            <ThemedText>
                                World Cups: {countryData.titles.worldCups}
                            </ThemedText>

                            <ThemedText>
                                Continental Cups: {countryData.titles.continentalCups}
                            </ThemedText>

                            <ThemedText>
                                Confederations Cup: {countryData.titles.confederationsCup}
                            </ThemedText>

                            <ThemedText>
                                Olympic Gold Medals: {countryData.titles.olympicGoldMedals}
                            </ThemedText>
                        </ThemedView>

                        <ThemedView
                            style={{
                                gap: 8,
                                borderWidth: 1,
                                borderColor: "gray",
                                padding: 12,
                                borderRadius: 8,
                            }}
                        >
                            <ThemedText type="subtitle">
                                All Time Statistics
                            </ThemedText>

                            <ThemedText>
                                Matches: {countryData.allTimeStatistics.matches}
                            </ThemedText>

                            <ThemedText>
                                Wins: {countryData.allTimeStatistics.wins}
                            </ThemedText>

                            <ThemedText>
                                Draws: {countryData.allTimeStatistics.draws}
                            </ThemedText>

                            <ThemedText>
                                Losses: {countryData.allTimeStatistics.losses}
                            </ThemedText>

                            <ThemedText>
                                Goals Scored: {countryData.allTimeStatistics.goalsScored}
                            </ThemedText>

                            <ThemedText>
                                Goals Conceded: {countryData.allTimeStatistics.goalsConceded}
                            </ThemedText>
                        </ThemedView>

                        <ThemedView
                            style={{
                                gap: 8,
                            }}
                        >
                            <ThemedText type="subtitle">
                                Legendary Players
                            </ThemedText>

                            {countryData.legendaryPlayers?.map((player) => (
                                <ThemedView
                                    key={player.id}
                                    style={{
                                        borderWidth: 1,
                                        borderColor: "gray",
                                        padding: 12,
                                        borderRadius: 8,
                                    }}
                                >
                                    <ThemedText>
                                        <SoccerPlayerLink player={player}>{player.name}</SoccerPlayerLink>
                                    </ThemedText>

                                    <ThemedText>
                                        Number: {player.number}
                                    </ThemedText>

                                    <ThemedText>
                                        Position: {player.position}
                                    </ThemedText>
                                </ThemedView>
                            ))}
                        </ThemedView>

                        <ThemedView
                            style={{
                                gap: 12,
                            }}
                        >
                            <ThemedText type="subtitle">
                                World Cup History
                            </ThemedText>

                            {countryData.worldCupHistory?.map((history) => (
                                <ThemedView
                                    key={history.worldCupYear}
                                    style={{
                                        borderWidth: 1,
                                        borderColor: "gray",
                                        padding: 12,
                                        borderRadius: 8,
                                        gap: 4,
                                    }}
                                >
                                    <ThemedText>
                                        Year: {history.worldCupYear}
                                    </ThemedText>

                                    <ThemedText>
                                        Host: {history.hostCountry}
                                    </ThemedText>

                                    <ThemedText>
                                        Final Position: {history.finalPosition}
                                    </ThemedText>

                                    <ThemedText>
                                        Coach: {history.coach}
                                    </ThemedText>

                                    <ThemedText>
                                        Captain: {history.captain.name}
                                    </ThemedText>

                                    <ThemedText>
                                        Summary: {history.campaignSummary}
                                    </ThemedText>

                                    <ThemedText>
                                        Top Scorer: <SoccerPlayerLink player={history.topScorer.player}>{history.topScorer.player.name}</SoccerPlayerLink> (
                                        {history.topScorer.goals} goals)
                                    </ThemedText>
                                </ThemedView>
                            ))}
                        </ThemedView>
                    </ThemedView>
                </ThemedView>}
        </ThemedView>
    </ScrollView>
    )

}
const styles = StyleSheet.create({
    flexBox: {
        display: "flex",
        flexDirection: "column"
    },

    titleContainer: {
        flexDirection: "row",
        alignItems: "center",
        gap: 8,
    },

    stepContainer: {
        gap: 8,
        marginBottom: 8,
    },

    reactLogo: {
        height: 178,
        width: 290,
        bottom: 0,
        left: 0,
        position: "absolute",
    },
});