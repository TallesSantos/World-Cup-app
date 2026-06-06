import { DetailedCoachPlayer, DetailedSoccerPlayer } from "../../schemas/world-cup/soccer-player-schema";
import { API_CONFIG } from "./api-config";

export const playerApiClient = {
    getDetailedPlayerById: getDetailedPlayerById,
    getDetailedCoachById: getDetailedCoachById
}

export async function getDetailedPlayerById(id: string) {
    console.log(id)
    const playerResponse = await fetch(`${API_CONFIG.base_url}/players/${id}`);
    console.log(playerResponse)
    const player = JSON.parse(await playerResponse.text()) as DetailedSoccerPlayer;

    return player;

}

export async function getDetailedCoachById(id: string) {
    const coachResponse = await fetch(`${API_CONFIG.base_url}/coach/${id}`);
    const coach = JSON.parse(await coachResponse.text()) as DetailedCoachPlayer;
    return coach;
}