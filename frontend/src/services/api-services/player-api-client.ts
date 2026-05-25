import { DetailedCoachPlayer, DetailedSoccerPlayer } from "../../schemas/world-cup/soccer-player-schema";
import { API_CONFIG } from "./api-config";

export const playerApiClient = {
    getDetailedPlayerById: getDetailedPlayerById,
    getDetailedCoachById: getDetailedCoachById
}

export async function getDetailedPlayerById(id: string) {
    const playerResponse = await fetch(`${API_CONFIG.base_url}/players/${id}`);
    const player = JSON.parse(await playerResponse.text()) as DetailedSoccerPlayer;
    console.log(JSON.stringify(player))
    return player;

}

export async function getDetailedCoachById(id: string) {
    const coachResponse = await fetch(`${API_CONFIG.base_url}/coach/${id}`);
    const coach = JSON.parse(await coachResponse.text()) as DetailedCoachPlayer;
    return coach;
}