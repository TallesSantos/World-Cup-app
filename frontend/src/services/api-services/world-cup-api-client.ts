import { DetailedWorldCup, WorldCup } from "../../schemas/world-cup/world-cup-tornment-schemas";
import { API_CONFIG } from "./api-config";
import { getContryDataById } from "./country-api-client";
import { getMatchById } from "./match-api-client";

export const worldCupApiClient = {
    listWordlCups: listWordlCups,
    getWolrdlCupById: getWolrdlCupById,
    getMatchById: getMatchById,
    getContryDataById: getContryDataById
}

async function listWordlCups() {
    const wordlCupsResponse = await fetch(`${API_CONFIG.base_url}/world-cups`);
    const wordlCups: WorldCup[] = await JSON.parse(await wordlCupsResponse.text()) as WorldCup[];
    return wordlCups;
}

async function getWolrdlCupById(id: string) {
    const wordlCupResponse = await fetch(`${API_CONFIG.base_url}/world-cups/${id}`);
    const wordlCup: DetailedWorldCup = await JSON.parse(await wordlCupResponse.text()) as DetailedWorldCup;
    return wordlCup;
}
