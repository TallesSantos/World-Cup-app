import { WorldCupMatches } from "@/src/schemas/world-cup/match-schema"
import { useRouter } from "expo-router"
import { TouchableOpacity } from "react-native"
import { ThemedText } from "../common/themed-text"
import CountryIcon from "../icons/country-icon"

export default function MatchLink({ match }: { match: WorldCupMatches }) {

    const router = useRouter()

    return (<TouchableOpacity onPress={() => { router.push({ pathname: `/world-cup/match-page`, params: { id: match.id } }, { relativeToDirectory: true }) }}>
        <ThemedText type="link">
            <CountryIcon country={match.homeTeam} />{match.homeTeam.name} x <CountryIcon country={match.visitingTeam} />{match.visitingTeam.name} - {match.homeTeamScore || "?"} x {match.visitingTeamScore || "?"}
        </ThemedText>
    </TouchableOpacity>
    )
}