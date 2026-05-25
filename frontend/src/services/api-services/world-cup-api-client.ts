import { WorldCupDetailedMatches } from "../../schemas/world-cup/match-schema";
import { DetailedWorldCup, WorldCup } from "../../schemas/world-cup/world-cup-tornment-schemas";
import { API_CONFIG } from "./api-config";

export const worldCupApiClient = {
    listWordlCups: listWordlCups,
    getWolrdlCupById: getWolrdlCupById,
    getMatchById: getMatchById,
    getContryDataById: getContryDataById
}

async function listWordlCups() {
    const wordlCupsResponse = await fetch(`${API_CONFIG.base_url}/world-cup`);
    const wordlCups: WorldCup[] = await JSON.parse(await wordlCupsResponse.text()) as WorldCup[];
    return wordlCups;
}

async function getWolrdlCupById(id: string) {
    const wordlCupResponse = await fetch(`${API_CONFIG.base_url}/world-cup/${id}`);
    const wordlCup: DetailedWorldCup = await JSON.parse(await wordlCupResponse.text()) as DetailedWorldCup;
    return wordlCup;
}

async function getMatchById(id: string) {
    const matchResponse = await fetch(`${API_CONFIG.base_url}/matches/${id}`);
    const match: WorldCupDetailedMatches = await JSON.parse(await matchResponse.text()) as WorldCupDetailedMatches;
    return match;
}

async function getContryDataById(id: string) {
    const countryResponse = await fetch(`${API_CONFIG.base_url}/countries/${id}`);
    const country: WorldCupDetailedMatches = await JSON.parse(await countryResponse.text()) as WorldCupDetailedMatches;
    return country;
}

