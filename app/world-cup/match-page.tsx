import { ThemedText } from "@/src/components/themed-text";
import { ThemedView } from "@/src/components/themed-view";
import { WorldCupDetailedMatches } from "@/src/shared/schemas/world-cup/match-schema";
import { worldCupService } from "@/src/shared/services/api-services/world-cup-service";
import { useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet, View } from "react-native";

export default function MatchPage() {
    const { id } = useLocalSearchParams();
    const [matchData, setMatchData] = useState<WorldCupDetailedMatches | null>(null);

    async function loadData() {
        const matchResponse = await worldCupService.getMatchById(id as string);
        setMatchData(matchResponse)
    }

    useEffect(() => {
        loadData()
    }, [])

    return (
        <ScrollView>
            <ThemedView style={{
                padding: 16,
                paddingTop: 48,
                width: "100%",
                gap: 12,
            }}>
                {!matchData ?
                    <ThemedView>
                        <ThemedText type="title">Loading...</ThemedText>
                    </ThemedView>
                    : <ThemedView>
                        <ThemedText type="title">{matchData.homeTeam.name} x {matchData.visitingTeam.name}</ThemedText>

                        <ThemedText type="subtitle">Statistics</ThemedText>
                        <ThemedView
                            style={[
                                {
                                    flexDirection: "column",
                                    flexWrap: "wrap",

                                },
                            ]}
                        >
                            <View >
                                <ThemedText type="defaultSemiBold">basic statistics:</ThemedText>
                                <ThemedView>
                                    {matchData.basicStatistics.map((item, index) => {
                                        return <ThemedText key={index + item.name}> {item.name}: {item.value} </ThemedText>
                                    })}
                                </ThemedView>

                            </View>

                            <View>
                                <ThemedText type="defaultSemiBold">Comparative statistics:</ThemedText>
                                <ThemedView style={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
                                    <ThemedText>statistic:</ThemedText>
                                    <ThemedText>{matchData.homeTeam.name}</ThemedText>
                                    <ThemedText type="title">X</ThemedText>
                                    <ThemedText>{matchData.visitingTeam.name}</ThemedText>
                                </ThemedView>
                                {matchData.comparativeStatistics.map((item, index) => {
                                    return <ThemedView key={index + item.name} style={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
                                        <ThemedText>{item.name}:</ThemedText>
                                        <ThemedText>{item.homeTeamValue}</ThemedText>
                                        <ThemedText>x</ThemedText>
                                        <ThemedText>{item.visitingTeamValue}</ThemedText>
                                    </ThemedView>
                                })}
                            </View>
                        </ThemedView>
                        <ThemedView>
                            <ThemedText type="defaultSemiBold">Country players:</ThemedText>
                            <ThemedView style={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
                                <ThemedText>{matchData.homeTeam.name}</ThemedText>
                                <ThemedText type="title">X</ThemedText>
                                <ThemedText>{matchData.visitingTeam.name}</ThemedText>
                            </ThemedView>
                            <ThemedView style={{ display: "flex", flexDirection: "row", justifyContent: "space-between" }}>
                                <ThemedView style={{ display: "flex", flexDirection: "column", gap: 4 }}>
                                    {matchData.homeTeamMatchPlayers.map(player => {
                                        return <ThemedText key={player.id}>{player.name}  - {player.number}</ThemedText>
                                    })}
                                </ThemedView>
                                <ThemedView style={{ display: "flex", flexDirection: "column", gap: 4 }}>
                                    {matchData.visitingTeamMatchPlayers.map(player => {
                                        return <ThemedText key={player.id}>{player.name} - {player.number}</ThemedText>
                                    })}
                                </ThemedView>
                            </ThemedView>
                        </ThemedView>
                        <ThemedView>
                            <ThemedText type="defaultSemiBold">Gols of the match:</ThemedText>
                            {matchData.matchVideos.map((video, index) => {
                                return <ThemedText key={index + video.title}>url: {video.url}</ThemedText>
                            })}
                        </ThemedView>
                    </ThemedView>}
            </ThemedView>
        </ScrollView>
    );
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