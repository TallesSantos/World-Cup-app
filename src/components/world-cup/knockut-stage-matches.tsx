import { KnockoutStage } from "@/src/shared/schemas/world-cup/world-cup-tornment-schemas";
import { useState } from "react";
import { TouchableOpacity, View } from "react-native";
import { ThemedText } from "../themed-text";
import { ThemedView } from "../themed-view";

type Round = "RoundOf16" | "quarterFinals" | "semiFinals" | "grandFinal"

const options = [
    { label: "16", value: "RoundOf16" },
    { label: "4", value: "quarterFinals" },
    { label: "semi", value: "semiFinals" },
    { label: "final", value: "grandFinal" },
];

export default function KnockoutStageMatches({ data }: { data: KnockoutStage }) {

    const [round, setRound] = useState<Round>("RoundOf16");
    return (
        <ThemedView style={{ gap: 12, marginBottom: 12, flexDirection: "column", justifyContent: "space-between", borderBottomColor: "gray", borderBottomWidth: 1, paddingBottom: 12 }}>

            <ThemedView style={{ backgroundColor: "#EEE", padding: 12, borderRadius: 8, gap: 12, alignItems: "center", justifyContent: "center" }}>
                <ThemedText>Select Knockout stage </ThemedText>
                <ThemedView style={{ display: "flex", flexDirection: "row", gap: 16 }}>

                    {options.map((option) => (
                        <TouchableOpacity
                            key={option.value}

                            onPress={() => setRound(option.value as Round)}
                        >
                            <ThemedView>
                                {round === option.value && (
                                    <View style={styles.innerCircle} />
                                )}
                            </ThemedView>

                            <ThemedText>{option.label}</ThemedText>
                        </TouchableOpacity>
                    ))}
                </ThemedView>
            </ThemedView>

            {round === "RoundOf16" && data.RoundOf16.matchs.map((match, index) => {
                return (
                    <ThemedView key={index + match.id}>
                        <ThemedText>{match.homeTeam.name} x {match.visitingTeam.name} - {match.homeTeamScore || "?"} x {match.visitingTeamScore || "?"}
                        </ThemedText>
                    </ThemedView>
                )
            })}

            {round === "quarterFinals" &&
                <ThemedView>
                    <ThemedText>{data.quarterFinals.match1.homeTeam.name} x {data.quarterFinals.match1.visitingTeam.name} - {data.quarterFinals.match1.homeTeamScore || "?"} x {data.quarterFinals.match1.visitingTeamScore || "?"}
                    </ThemedText>
                    <ThemedText>{data.quarterFinals.match2.homeTeam.name} x {data.quarterFinals.match2.visitingTeam.name} - {data.quarterFinals.match2.homeTeamScore || "?"} x {data.quarterFinals.match2.visitingTeamScore || "?"}
                    </ThemedText>
                    <ThemedText>{data.quarterFinals.match3.homeTeam.name} x {data.quarterFinals.match3.visitingTeam.name} - {data.quarterFinals.match3.homeTeamScore || "?"} x {data.quarterFinals.match3.visitingTeamScore || "?"}
                    </ThemedText>
                    <ThemedText>{data.quarterFinals.match4.homeTeam.name} x {data.quarterFinals.match4.visitingTeam.name} - {data.quarterFinals.match4.homeTeamScore || "?"} x {data.quarterFinals.match4.visitingTeamScore || "?"}
                    </ThemedText>
                </ThemedView>
            }

            {round === "semiFinals" &&
                <ThemedView>
                    <ThemedText>{data.semiFinals.match1.homeTeam.name} x {data.semiFinals.match1.visitingTeam.name} - {data.semiFinals.match1.homeTeamScore || "?"} x {data.semiFinals.match1.visitingTeamScore || "?"}
                    </ThemedText>
                    <ThemedText>{data.semiFinals.match2.homeTeam.name} x {data.semiFinals.match2.visitingTeam.name} - {data.semiFinals.match2.homeTeamScore || "?"} x {data.semiFinals.match2.visitingTeamScore || "?"}
                    </ThemedText>
                </ThemedView>
            }

            {round === "grandFinal" &&
                <ThemedView>
                    <ThemedText>{data.grandFinal.homeTeam.name} x {data.grandFinal.visitingTeam.name} - {data.grandFinal.homeTeamScore || "?"} x {data.grandFinal.visitingTeamScore || "?"}
                    </ThemedText>
                </ThemedView>
            }
        </ThemedView>
    )
}

const styles = {
    container: {
        padding: 24,
        marginTop: 50,
        gap: 16,
    },

    radioContainer: {
        flexDirection: "row",
        alignItems: "center",
        gap: 10,
    },

    outerCircle: {
        width: 24,
        height: 24,
        borderRadius: 12,
        borderWidth: 2,
        justifyContent: "center",
        alignItems: "center",
    },

    innerCircle: {
        width: 12,
        height: 12,
        borderRadius: 6,
        backgroundColor: "black",
    },

    selectedText: {
        marginTop: 20,
        fontSize: 16,
    },
};