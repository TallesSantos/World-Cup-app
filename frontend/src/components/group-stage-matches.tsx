import { WorldCupStageGroup } from "@/src/schemas/world-cup/world-cup-tornment-schemas";
import { ThemedText } from "./common/themed-text";
import { ThemedView } from "./common/themed-view";
import CountryIcon from "./icons/country-icon";
import CountryLink from "./links/country-link";
import MatchLink from "./links/match-link";

export default function GroupStageMatches({ data }: { data: WorldCupStageGroup[] }) {

    return (
        <ThemedView style={{ gap: 12, marginBottom: 12 }}>

            {data.map((group: WorldCupStageGroup, index) => (
                <ThemedView key={index + group.groupName} style={{ flexDirection: "column", justifyContent: "space-between", borderBottomColor: "gray", borderBottomWidth: 1, paddingBottom: 12 }}>
                    <ThemedText>{group.groupName} </ThemedText>
                    <ThemedView style={{ display: "flex", flexDirection: "row", gap: 8 }}>{group.teams.map(async (team, index) => {

                        return (
                            <ThemedText key={index + team.id}><CountryLink country={team}>
                                <ThemedText type="link"> <CountryIcon country={team} /> |{team.endRatingPoint}pt</ThemedText>
                            </CountryLink>|</ThemedText>
                        )
                    })}
                    </ThemedView>
                    <ThemedText>Matches: </ThemedText>
                    {group.matches.map((match, index) => {
                        return (
                            <ThemedView key={index + match.id}>
                                <MatchLink match={match} />
                            </ThemedView>
                        )
                    })}
                </ThemedView>
            ))}
        </ThemedView>
    )
}