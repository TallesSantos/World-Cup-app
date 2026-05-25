import { ThemedText } from "@/src/components/common/themed-text";
import { ThemedView } from "@/src/components/common/themed-view";
import { DetailedSoccerPlayer } from "@/src/schemas/world-cup/soccer-player-schema";
import { playerApiClient } from "@/src/services/api-services/player-api-client";
import { useLocalSearchParams } from "expo-router";
import { useEffect, useState } from "react";
import { Image, ScrollView, StyleSheet } from "react-native";

export default function PlayerPage() {

    const { id } = useLocalSearchParams();
    const [playerData, setPlayerData] = useState<DetailedSoccerPlayer | null>(null);

    async function loadData() {
        const playerDataResponse = await playerApiClient.getDetailedPlayerById(id as string);
        setPlayerData(playerDataResponse)
    }

    useEffect(() => {
        loadData()
    }, [])

    return (
        <ScrollView>
            <ThemedView style={
                {
                    flex: 1,
                    flexWrap: "wrap",
                    alignItems: "center",
                    justifyContent: "center",
                    padding: 16,
                    paddingTop: 48,
                    width: "100%",
                    gap: 12,
                }
            } >
                {!playerData

                    ? <ThemedView style={[styles.titleContainer, styles.flexBox]} >
                        <ThemedText type="title">Loading...</ThemedText>
                    </ThemedView>

                    : <ThemedView style={[styles.titleContainer, styles.flexBox, { flexWrap: "wrap" }]} >
                        <ThemedView
                            style={{
                                padding: 16,
                                gap: 16,
                            }}
                        >
                            <ThemedView
                                style={{
                                    alignItems: "center",
                                    gap: 12,
                                }}
                            >
                                <Image
                                    source={{
                                        uri: playerData.profileImageUrl,
                                    }}
                                    style={{
                                        width: 120,
                                        height: 120,
                                        borderRadius: 60,
                                    }}
                                />

                                <ThemedText type="title">
                                    {playerData.name}
                                </ThemedText>

                                <ThemedText>
                                    {playerData.position} • #{playerData.number}
                                </ThemedText>
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 6,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Basic Information
                                </ThemedText>

                                <ThemedText>
                                    Full name: {playerData.fullName}
                                </ThemedText>

                                <ThemedText>
                                    Nickname: {playerData.nickname || "N/A"}
                                </ThemedText>

                                <ThemedText>
                                    Country: {playerData.country}
                                </ThemedText>

                                <ThemedText>
                                    Birth date: {playerData.birthDate}
                                </ThemedText>

                                <ThemedText>
                                    Age: {playerData.age}
                                </ThemedText>

                                <ThemedText>
                                    Height: {playerData.height}
                                </ThemedText>

                                <ThemedText>
                                    Weight: {playerData.weight}
                                </ThemedText>

                                <ThemedText>
                                    Preferred foot: {playerData.preferredFoot}
                                </ThemedText>

                                <ThemedText>
                                    Current club: {playerData.currentClub}
                                </ThemedText>

                                <ThemedText>
                                    Market value: {playerData.marketValue}
                                </ThemedText>

                                <ThemedText>
                                    Captain: {playerData.captain ? "Yes" : "No"}
                                </ThemedText>

                                <ThemedText>
                                    Retired: {playerData.retired ? "Yes" : "No"}
                                </ThemedText>
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 6,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Attributes
                                </ThemedText>

                                <ThemedText>
                                    Pace: {playerData.attributes?.pace}
                                </ThemedText>

                                <ThemedText>
                                    Shooting: {playerData.attributes?.shooting}
                                </ThemedText>

                                <ThemedText>
                                    Passing: {playerData.attributes?.passing}
                                </ThemedText>

                                <ThemedText>
                                    Dribbling: {playerData.attributes?.dribbling}
                                </ThemedText>

                                <ThemedText>
                                    Defending: {playerData.attributes?.defending}
                                </ThemedText>

                                <ThemedText>
                                    Physical: {playerData.attributes?.physical}
                                </ThemedText>
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 6,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Career Statistics
                                </ThemedText>

                                <ThemedText>
                                    Matches: {playerData.careerStatistics.matches}
                                </ThemedText>

                                <ThemedText>
                                    Goals: {playerData.careerStatistics.goals}
                                </ThemedText>

                                <ThemedText>
                                    Assists: {playerData.careerStatistics.assists}
                                </ThemedText>

                                <ThemedText>
                                    Yellow cards: {playerData.careerStatistics.yellowCards}
                                </ThemedText>

                                <ThemedText>
                                    Red cards: {playerData.careerStatistics.redCards}
                                </ThemedText>

                                <ThemedText>
                                    Minutes played: {playerData.careerStatistics.minutesPlayed}
                                </ThemedText>
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 6,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    National Team Statistics
                                </ThemedText>

                                <ThemedText>
                                    Matches: {playerData.nationalTeamStatistics.matches}
                                </ThemedText>

                                <ThemedText>
                                    Goals: {playerData.nationalTeamStatistics.goals}
                                </ThemedText>

                                <ThemedText>
                                    Assists: {playerData.nationalTeamStatistics.assists}
                                </ThemedText>
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 8,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Clubs History
                                </ThemedText>

                                {playerData.clubsHistory.map((club, index) => (
                                    <ThemedView
                                        key={index}
                                        style={{
                                            borderWidth: 1,
                                            borderColor: "gray",
                                            padding: 12,
                                            borderRadius: 8,
                                            gap: 4,
                                        }}
                                    >
                                        <ThemedText>
                                            Club: {club.clubName}
                                        </ThemedText>

                                        <ThemedText>
                                            Period: {club.startYear} - {club.endYear || "Present"}
                                        </ThemedText>

                                        <ThemedText>
                                            Matches: {club.matches}
                                        </ThemedText>

                                        <ThemedText>
                                            Goals: {club.goals}
                                        </ThemedText>
                                    </ThemedView>
                                ))}
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 8,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    World Cup History
                                </ThemedText>

                                {playerData.worldCupHistory.map((cup, index) => (
                                    <ThemedView
                                        key={index}
                                        style={{
                                            borderWidth: 1,
                                            borderColor: "gray",
                                            padding: 12,
                                            borderRadius: 8,
                                            gap: 4,
                                        }}
                                    >
                                        <ThemedText>
                                            Year: {cup.year}
                                        </ThemedText>

                                        <ThemedText>
                                            Host country: {cup.hostCountry}
                                        </ThemedText>

                                        <ThemedText>
                                            Final position: {cup.finalPosition}
                                        </ThemedText>

                                        <ThemedText>
                                            Matches: {cup.matches}
                                        </ThemedText>

                                        <ThemedText>
                                            Goals: {cup.goals}
                                        </ThemedText>

                                        <ThemedText>
                                            Assists: {cup.assists}
                                        </ThemedText>
                                    </ThemedView>
                                ))}
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 8,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Awards
                                </ThemedText>

                                {playerData.awards.map((award, index) => (
                                    <ThemedView
                                        key={index}
                                        style={{
                                            borderWidth: 1,
                                            borderColor: "gray",
                                            padding: 12,
                                            borderRadius: 8,
                                        }}
                                    >
                                        <ThemedText>
                                            {award.title} ({award.year})
                                        </ThemedText>
                                    </ThemedView>
                                ))}
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 8,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Injuries History
                                </ThemedText>

                                {playerData.injuriesHistory.map((injury, index) => (
                                    <ThemedView
                                        key={index}
                                        style={{
                                            borderWidth: 1,
                                            borderColor: "gray",
                                            padding: 12,
                                            borderRadius: 8,
                                            gap: 4,
                                        }}
                                    >
                                        <ThemedText>
                                            Injury: {injury.injury}
                                        </ThemedText>

                                        <ThemedText>
                                            Start: {injury.startDate}
                                        </ThemedText>

                                        <ThemedText>
                                            End: {injury.endDate}
                                        </ThemedText>
                                    </ThemedView>
                                ))}
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 6,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Social Media
                                </ThemedText>

                                <ThemedText>
                                    Instagram: {playerData.socialMedia?.instagram || "N/A"}
                                </ThemedText>

                                <ThemedText>
                                    Twitter: {playerData.socialMedia?.twitter || "N/A"}
                                </ThemedText>

                                <ThemedText>
                                    Facebook: {playerData.socialMedia?.facebook || "N/A"}
                                </ThemedText>
                            </ThemedView>

                            <ThemedView
                                style={{
                                    gap: 6,
                                }}
                            >
                                <ThemedText type="subtitle">
                                    Biography
                                </ThemedText>

                                <ThemedText>
                                    {playerData.biography}
                                </ThemedText>
                            </ThemedView>
                        </ThemedView>
                    </ThemedView>}
            </ThemedView>
        </ScrollView>

    );
}

const styles = StyleSheet.create({
    flexBox: {
        display: "flex",
        flexDirection: "column"
    },

    titleContainer: {
        flexDirection: "row",
        alignItems: "center",
        gap: 8,
    },

    stepContainer: {
        gap: 8,
        marginBottom: 8,
    },

    reactLogo: {
        height: 178,
        width: 290,
        bottom: 0,
        left: 0,
        position: "absolute",
    },
});