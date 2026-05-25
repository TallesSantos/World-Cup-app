import { WorldCupDetailedMatches } from "@/src/schemas/world-cup/match-schema";
import { API_CONFIG } from "./api-config";


export async function getMatchById(id: string) {
    const matchResponse = await fetch(`${API_CONFIG.base_url}/matches/${id}`);
    const match: WorldCupDetailedMatches = await JSON.parse(await matchResponse.text()) as WorldCupDetailedMatches;
    return match;
}
