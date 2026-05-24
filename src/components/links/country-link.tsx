import { WordlCupCountry } from "@/src/schemas/world-cup/country-schema";
import { useRouter } from "expo-router";
import { TouchableOpacity } from "react-native";

export default function CountryLink({ country, children }: { country: WordlCupCountry, children: React.ReactNode }) {
    const router = useRouter();

    return (<TouchableOpacity onPress={() => { router.push({ pathname: `/world-cup/country-page`, params: { id: country.id } }) }} >

        {children}
    </TouchableOpacity>)
}