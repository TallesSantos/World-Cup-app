import { SoccerPlayer } from "@/src/shared/schemas/world-cup/soccer-player-schema";
import { useRouter } from "expo-router";
import { TouchableOpacity } from "react-native";
import { ThemedText } from "../../themed-text";

export default function SoccerPlayerLink({ player, children }: { player: SoccerPlayer, children?: React.ReactNode }) {
    const router = useRouter()

    return (
        <TouchableOpacity onPress={() => { router.push({ pathname: `/world-cup/player-page`, params: { id: player.id } }) }} >
            <ThemedText type="link">{children}</ThemedText>
        </TouchableOpacity>
    )

}