import { SoccerPlayer } from "@/src/schemas/world-cup/soccer-player-schema";
import { useRouter } from "expo-router";
import { TouchableOpacity } from "react-native";
import { ThemedText } from "../common/themed-text";

export default function SoccerPlayerLink({ player, children }: { player: SoccerPlayer, children?: React.ReactNode }) {
    const router = useRouter()

    return (
        <TouchableOpacity onPress={() => { router.push({ pathname: `/world-cup/soccer-player-page`, params: { id: player.id } }) }} >
            <ThemedText type="link">{children}</ThemedText>
        </TouchableOpacity>
    )

}