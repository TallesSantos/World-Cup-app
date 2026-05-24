import { WorldCupStageGroup } from "@/src/shared/schemas/world-cup/world-cup-tornment-schemas";
import { useRouter } from "expo-router";
import { ThemedText } from "../themed-text";
import { ThemedView } from "../themed-view";
import CountryIcon from "./country-icon/country-icon";

export default function GroupStageMatches({ data }: { data: WorldCupStageGroup[] }) {
    const router = useRouter();
    return (
        <ThemedView style={{ gap: 12, marginBottom: 12 }}>

            {data.map((group: WorldCupStageGroup, index) => (
                <ThemedView key={index + group.groupName} style={{ flexDirection: "column", justifyContent: "space-between", borderBottomColor: "gray", borderBottomWidth: 1, paddingBottom: 12 }}>
                    <ThemedText>{group.groupName} </ThemedText>
                    <ThemedView style={{ display: "flex", flexDirection: "row", gap: 8 }}>{group.teams.map(async (team, index) => {

                        return (
                            <ThemedText key={index + team.id}><CountryIcon /> |{team.endRatingPoint}pt|</ThemedText>
                        )
                    })}
                    </ThemedView>
                    <ThemedText>Matches: </ThemedText>
                    {group.matches.map((match, index) => {
                        return (
                            <ThemedView key={index + match.id}>
                                <ThemedText onPress={() => { router.push({ pathname: `/world-cup/match-page`, params: { id: match.id } }, { relativeToDirectory: true }) }}>{match.homeTeam.name} x {match.visitingTeam.name} - {match.homeTeamScore || "?"} x {match.visitingTeamScore || "?"}
                                </ThemedText>
                            </ThemedView>
                        )
                    })}
                </ThemedView>
            ))}
        </ThemedView>
    )
}