import ParallaxScrollView from "@/src/components/common/parallax-scroll-view";
import { ThemedText } from "@/src/components/common/themed-text";
import WorldCupLink from "@/src/components/links/world-cup-link";
import { WorldCup } from "@/src/schemas/world-cup/world-cup-tornment-schemas";
import { API_CONFIG } from "@/src/services/api-services/api-config";
import { worldCupApiClient } from "@/src/services/api-services/world-cup-api-client";
import { useEffect, useState } from "react";
import { Image, StyleSheet } from "react-native";

export default function ListOfWorldCupsPage() {
    const [wordlCupsData, setWordCupsData] = useState<WorldCup[] | null>();

    async function loadPage() {
        const wordlCupsResponse = await worldCupApiClient.listWordlCups()
        setWordCupsData(wordlCupsResponse)
    }

    useEffect(() => {
        loadPage()
    }, [])



    return (<ParallaxScrollView

        headerBackgroundColor={{ light: "#A1CEDC", dark: "#1D3D47" }}
        headerImage={
            <Image
                source={require("@/assets/images/world-cup-2026-banner.jpg")}
                resizeMode="cover"
                style={styles.reactLogo}
            />
        }
    >
        <ThemedText type="title">World Cups</ThemedText>

        {!wordlCupsData
            ? <ThemedText type="title">Loading...</ThemedText>

            : wordlCupsData.map((worldCup: WorldCup) => {
                return (

                    <ParallaxScrollView key={worldCup.id}

                        headerBackgroundColor={{ light: "#A1CEDC", dark: "#1D3D47" }}
                        headerImage={
                            <Image
                                source={worldCup.imgBannerUrl ? { uri: API_CONFIG.resource_base_url + worldCup.imgBannerUrl } : require("@/assets/images/not-found.png")}
                                resizeMode="cover"
                                style={styles.reactLogo}
                            />
                        }
                    >
                        <WorldCupLink cup={worldCup}>
                            <ThemedText>{worldCup.title} </ThemedText>
                        </WorldCupLink>
                    </ParallaxScrollView>)
            }
            )
        }


    </ParallaxScrollView>
    )
}

const styles = StyleSheet.create({

    flexColumn: {
        display: "flex", flexDirection: "column"
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
        width: "100%",
        height: 300,
        bottom: 0,
        left: 0,
        position: "absolute",
    },
});