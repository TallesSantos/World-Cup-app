import { WorldCupDetailedContry } from "@/src/schemas/world-cup/country-schema";
import { API_CONFIG } from "./api-config";


export async function getContryDataById(id: string) {
    const countryResponse = await fetch(`${API_CONFIG.base_url}/countries/${id}`);
    const country: WorldCupDetailedContry = await JSON.parse(await countryResponse.text()) as WorldCupDetailedContry;
    return country;
}
