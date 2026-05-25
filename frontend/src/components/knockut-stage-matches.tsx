import { KnockoutStage } from "@/src/schemas/world-cup/world-cup-tornment-schemas";
import { useState } from "react";
import { TouchableOpacity, View } from "react-native";
import { ThemedText } from "./common/themed-text";
import { ThemedView } from "./common/themed-view";
import MatchLink from "./links/match-link";

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

            {round === "RoundOf16" && data.roundOf16.matches.map((match, index) => {
                return (
                    <ThemedView key={index + match.id}>
                        <MatchLink match={match} />
                    </ThemedView>
                )
            })}

            {round === "quarterFinals" &&
                <ThemedView>
                    <MatchLink match={data.quarterFinals.match1} />
                    <MatchLink match={data.quarterFinals.match2} />
                    <MatchLink match={data.quarterFinals.match3} />
                    <MatchLink match={data.quarterFinals.match4} />
                </ThemedView>
            }

            {round === "semiFinals" &&
                <ThemedView>
                    <MatchLink match={data.semiFinals.match1} />
                    <MatchLink match={data.semiFinals.match2} />
                </ThemedView>
            }

            {round === "grandFinal" &&
                <ThemedView>
                    <MatchLink match={data.finalMatch} />
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