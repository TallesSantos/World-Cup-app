import { Image } from "expo-image";
import { FlatList, StyleSheet, View } from "react-native";

import React from "react";

import ParallaxScrollView from "../../src/components/parallax-scroll-view";
import { ThemedText } from "../../src/components/themed-text";
import { ThemedView } from "../../src/components/themed-view";
import { useUserContext } from "../../src/shared/context/user.context";

export default function Store() {
    const { userData } = useUserContext();

    return (
        <ParallaxScrollView
            headerBackgroundColor={{ light: "#A1CEDC", dark: "#1D3D47" }}
            headerImage={
                <Image
                    source={require("@/assets/images/partial-react-logo.png")}
                    style={styles.reactLogo}
                />
            }
        >
            <ThemedView>

            </ThemedView>
            <ThemedView style={[styles.titleContainer, styles.flexBox]} >
                <View>
                    <ThemedText type="title">Store</ThemedText>
                    <ThemedText> Aba onde de fato centralizara as funcionalidades do aplicativo</ThemedText>
                </View>
                <FlatList data={[{ name: 'teste1', description: 'teste1' }, { name: 'teste2', description: 'teste2' }]} renderItem={
                    ({ item }) => {
                        return (
                            <ThemedView>
                                <ThemedText>{item.name}</ThemedText>
                                <ThemedText>{item.description}</ThemedText>

                            </ThemedView>
                        );
                    }
                } />

            </ThemedView>

        </ParallaxScrollView>
    );
}

const styles = StyleSheet.create({
    flexBox: { display: "flex", flexDirection: "column" },
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
