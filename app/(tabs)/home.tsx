import { Image } from "expo-image";
import { Button, StyleSheet, View } from "react-native";

import { useRouter } from "expo-router";
import React from "react";
import { HelloWave } from "../../src/components/hello-wave";
import ParallaxScrollView from "../../src/components/parallax-scroll-view";
import { ThemedText } from "../../src/components/themed-text";
import { ThemedView } from "../../src/components/themed-view";
import { useUserContext } from "../../src/shared/context/user.context";

export default function HomeScreen() {
  const router = useRouter();
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
      <ThemedText type="title">Pagina de entrada da aplicação</ThemedText>
      <ThemedText>Quando o usuario estiver logado, ela pode deixar de existir/possuir outras funcionalidades alem de cadastro e login</ThemedText>

      {userData ?
        <ThemedText type="title">Welcome {userData.name}!  <HelloWave />
        </ThemedText>
        : <UnloggedUser router={router} />}
    </ParallaxScrollView>
  );
}

function UnloggedUser({ router }: { router: ReturnType<typeof useRouter> }) {
  return (<ThemedView style={[styles.titleContainer, styles.flexColumn, { gap: 100 }]} >
    <ThemedText type="title">Welcome!  <HelloWave /></ThemedText>
    <View>

      <>
        <Button title="Login" />
        <Button title="Sign-up" />
      </>
    </View>
  </ThemedView>)
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
    height: 178,
    width: 290,
    bottom: 0,
    left: 0,
    position: "absolute",
  },
});
