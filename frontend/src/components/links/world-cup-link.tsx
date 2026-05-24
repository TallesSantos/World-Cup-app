import { WorldCup } from "@/src/schemas/world-cup/world-cup-tornment-schemas";
import { useRouter } from "expo-router";
import { TouchableOpacity } from "react-native";
import { ThemedText } from "../common/themed-text";

export default function WorldCupLink({ cup, children }: { cup: WorldCup, children?: React.ReactNode }) {
    const router = useRouter()

    return (
        <TouchableOpacity onPress={() => { router.push({ pathname: `/world-cup/world-cup-page`, params: { id: cup.id } }) }} >
            <ThemedText type="link">{children}</ThemedText>
        </TouchableOpacity>
    )

}