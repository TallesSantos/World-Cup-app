import { VideoView, useVideoPlayer } from "expo-video";
import { Dimensions, FlatList } from "react-native";
import YoutubePlayer from "react-native-youtube-iframe";
import { MatchVideo } from "../schemas/world-cup/match-schema";
import { ThemedView } from "./common/themed-view";

const { width } = Dimensions.get("window");

export function VideoItem({ url }: { url: string }) {
    const player = useVideoPlayer(url, (player) => {
        player.loop = true;
    });

    return (
        <VideoView
            player={player}
            nativeControls
            style={{
                width: 350,
                height: 300,
            }}
        />
    );
}
export function YoutubeVideoItem({ url: key }: { url: string }) {
    return (
        <YoutubePlayer
            height={300}
            width={350}
            play={false}
            videoId={key}
        />
    )
}

export default function VideoCarousel({ data }: { data: MatchVideo[] }) {
    return (
        <FlatList
            data={data}
            horizontal
            pagingEnabled
            keyExtractor={(item) => item.title}
            renderItem={({ item }) => {

                return (
                    <ThemedView style={{
                        width,
                        justifyContent: "center",
                        alignItems: "center",
                        backgroundColor: "#EEE",
                        padding: 16,

                    }}>
                        {item.plataform === 'resource-server' &&
                            <VideoItem url={item.url} />
                        }

                        {item.plataform === 'youtube' &&
                            <YoutubeVideoItem url={item.url} />
                        }
                    </ThemedView>
                )
            }}
        />
    );
}