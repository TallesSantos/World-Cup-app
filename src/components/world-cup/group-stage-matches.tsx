import { WorldCupStageGroup } from "@/src/shared/schemas/world_cup_schemas";
import { ThemedText } from "../themed-text";
import { ThemedView } from "../themed-view";

export default function GroupStageMatches({ data }: { data: WorldCupStageGroup[] }) {
    return (
        <ThemedView style={{ gap: 12, marginBottom: 12 }}>

            {data.map((group: WorldCupStageGroup, index) => (
                <ThemedView key={index + group.groupName} style={{ flexDirection: "column", justifyContent: "space-between", borderBottomColor: "gray", borderBottomWidth: 1, paddingBottom: 12 }}>
                    <ThemedText>{group.groupName} </ThemedText>
                    <ThemedView style={{ display: "flex", flexDirection: "row", gap: 8 }}>{group.teams.map((team, index) => {
                        return (
                            <ThemedText key={index + team.id}>{team.name} |{team.endRatingPoint}pt|</ThemedText>
                        )
                    })}
                    </ThemedView>
                    <ThemedText>Matches: </ThemedText>
                    {group.matches.map((match, index) => {
                        return (
                            <ThemedView key={index + match.id}>
                                <ThemedText>{match.homeTeam.name} x {match.visitingTeam.name} - {match.homeTeamScore || "?"} x {match.visitingTeamScore || "?"}
                                </ThemedText>
                            </ThemedView>
                        )
                    })}
                </ThemedView>
            ))}
        </ThemedView>
    )
}