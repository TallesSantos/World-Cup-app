import { ScrollView, StyleSheet, TouchableOpacity, View } from "react-native";

import React, { useEffect, useState } from "react";

import { ThemedText } from "@/src/components/common/themed-text";
import { ThemedView } from "@/src/components/common/themed-view";
import GroupStageMatches from "@/src/components/group-stage-matches";
import KnockoutStageMatches from "@/src/components/knockut-stage-matches";
import { DetailedWorldCup } from "@/src/schemas/world-cup/world-cup-tornment-schemas";
import { worldCupApiClient } from "@/src/services/api-services/world-cup-api-client";
import { useLocalSearchParams } from "expo-router";

export default function Store() {
    const { id } = useLocalSearchParams();
    const [wordlCupData, setWordCupData] = useState<DetailedWorldCup | null>();
    const [openGroupStageContainer, setOpenGroupStageContainer] = useState(true);
    const [openKnockupStageContainer, setOpenKnockupStageContainer] = useState(true);

    async function loadPage() {
        const wordlCupResponse = await worldCupApiClient.getWolrdlCupById(Number(id));
        setWordCupData(wordlCupResponse)
    }

    useEffect(() => {
        loadPage()
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
                {!wordlCupData

                    ? <ThemedView style={[styles.titleContainer, styles.flexBox]} >
                        <ThemedText type="title">Loading...</ThemedText>
                    </ThemedView>

                    : <ThemedView style={[styles.titleContainer, styles.flexBox, { flexWrap: "wrap" }]} >
                        <ThemedText type="title">{wordlCupData.title}</ThemedText>

                        <ThemedView
                            style={[
                                styles.titleContainer,
                                styles.flexBox,
                                {
                                    flexDirection: "row",
                                    flexWrap: "wrap",
                                    alignItems: "flex-start",
                                    width: "100%",
                                },
                            ]}
                        >
                            <View style={{ width: "48%" }}>
                                <ThemedText>basic statistics:</ThemedText>
                                <ThemedText>
                                    country: {wordlCupData.hostCountries}
                                </ThemedText>
                                <ThemedText>
                                    first place: {wordlCupData.statistics?.totalGoals ? wordlCupData.statistics.totalGoals : ""}
                                </ThemedText>

                            </View>

                            <View style={{ width: "48%" }}>
                                <ThemedText>other statistics:</ThemedText>


                            </View>
                        </ThemedView>

                        <ThemedText type="title">  Cup matches </ThemedText>
                        <ThemedView
                            style={[
                                styles.titleContainer,
                                styles.flexBox,
                                {
                                    flexDirection: "column",
                                    flexWrap: "wrap",
                                    alignItems: "flex-start",
                                    width: "100%",
                                },
                            ]}
                        >


                            <View>

                                <TouchableOpacity
                                    onPress={() => setOpenGroupStageContainer(!openGroupStageContainer)}
                                >
                                    <ThemedText type="subtitle">Group Stage Matches   {openGroupStageContainer ? "[close]" : "[open]"}
                                    </ThemedText>
                                </TouchableOpacity>

                                {openGroupStageContainer && <GroupStageMatches data={wordlCupData.groupStage} />}
                            </View>

                            <View>

                                <TouchableOpacity
                                    onPress={() => setOpenKnockupStageContainer(!openKnockupStageContainer)}
                                >
                                    <ThemedText type="subtitle" >Knockout stage matches  {openKnockupStageContainer ? "[close]" : "[open]"}
                                    </ThemedText>
                                </TouchableOpacity>
                                {openKnockupStageContainer && <KnockoutStageMatches data={wordlCupData.knockoutStage} />}
                            </View>
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