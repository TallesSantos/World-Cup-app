import { Image, StyleSheet } from 'react-native';

import { useRouter } from 'expo-router';
import ParallaxScrollView from '../../src/components/parallax-scroll-view';
import { ThemedText } from '../../src/components/themed-text';
import { ThemedView } from '../../src/components/themed-view';
import { useUserContext } from '../../src/shared/context/user.context';

export default function PrivateUserPage() {
  const router = useRouter();
  const { userData } = useUserContext();

  return (
    <ParallaxScrollView
      headerBackgroundColor={{ light: '#D0D0D0', dark: '#353636' }}
      headerImage={
        <Image
          source={require("@/assets/images/partial-react-logo.png")}
          style={styles.reactLogo}
        />
      }>
      <ThemedView style={styles.titleContainer}>
      </ThemedView>
      <ThemedText type="title">Pagina privada do usuario logado</ThemedText>
      <ThemedText>Nesta aba sera centralizado os dados pessoais do usuario</ThemedText>

    </ParallaxScrollView >
  );
}

const styles = StyleSheet.create({
  headerImage: {
    color: '#808080',
    bottom: -90,
    left: -35,
    position: 'absolute',
  },
  titleContainer: {
    flexDirection: 'row',
    gap: 8,
  },
  reactLogo: {
    height: 178,
    width: 290,
    bottom: 0,
    left: 0,
    position: "absolute",
  },
});
