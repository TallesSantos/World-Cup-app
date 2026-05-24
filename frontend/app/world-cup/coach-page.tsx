import { ThemedText } from "@/src/components/common/themed-text";
import { ThemedView } from "@/src/components/common/themed-view";
import { DetailedCoachPlayer } from "@/src/schemas/world-cup/soccer-player-schema";
import { playerApiClient } from "@/src/services/api-services/player-api-client";
import { useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { ScrollView, StyleSheet } from "react-native";

export default function CoachPage() {


    const { id } = useLocalSearchParams();
    const [coachData, setCoachData] = useState<DetailedCoachPlayer | null>(null);

    async function loadData() {
        const coachDataResponse = await playerApiClient.getDetailedCoachById(id as string);
        setCoachData(coachDataResponse)
    }

    useEffect(() => {
        loadData()
    }, [])

    return (
        <ScrollView>
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
                {!coachData

                    ? <ThemedView style={[styles.titleContainer, styles.flexBox]} >
                        <ThemedText type="title">Loading...</ThemedText>
                    </ThemedView>

                    : <ThemedView style={[styles.titleContainer, styles.flexBox, { flexWrap: "wrap" }]} >
                        <ThemedView
                            style={{
                                padding: 16,
                                gap: 16,
                            }}
                        >
                            <ThemedView
                                style={{
                                    alignItems: "center",
                                    gap: 12,
                                }}
                            ></ThemedView>
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