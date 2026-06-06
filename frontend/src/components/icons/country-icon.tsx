import { WordlCupCountry, WorldCupDetailedContry } from "@/src/schemas/world-cup/country-schema";
import { API_CONFIG } from "@/src/services/api-services/api-config";
import { Image } from "react-native";

export default function CountryIcon({ country }: { country: WordlCupCountry | WorldCupDetailedContry }) {

    return (<Image

        source={country.flagUrl ? {
            uri: API_CONFIG.resource_base_url
                + country.flagUrl

        } : require("@/assets/images/not-found.png")}

        style={{
            width: 32,
            height: 24,
        }} />)
}