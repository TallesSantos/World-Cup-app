import { WorldCupMatches } from "@/src/schemas/world-cup/match-schema"
import { useRouter } from "expo-router"
import { TouchableOpacity } from "react-native"
import { ThemedText } from "../common/themed-text"

export default function MatchLink({ match }: { match: WorldCupMatches }) {

    const router = useRouter()

    return (<TouchableOpacity onPress={() => { router.push({ pathname: `/world-cup/match-page`, params: { id: match.id } }, { relativeToDirectory: true }) }}>
        <ThemedText type="link">{match.homeTeam.name} x {match.visitingTeam.name} - {match.homeTeamScore || "?"} x {match.visitingTeamScore || "?"}
        </ThemedText></TouchableOpacity>
    )
}