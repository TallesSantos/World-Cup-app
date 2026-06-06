import { VideoView, useVideoPlayer } from "expo-video";
import { Dimensions, FlatList } from "react-native";
import YoutubePlayer from "react-native-youtube-iframe";
import { MatchVideo } from "../schemas/world-cup/match-schema";
import { ThemedText } from "./common/themed-text";
import { ThemedView } from "./common/themed-view";

const { width } = Dimensions.get("window");

export function VideoItem({ mediaEntity }: { mediaEntity: MatchVideo }) {
    const player = useVideoPlayer(mediaEntity.fullResourcePath, (player) => {
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
export function YoutubeVideoItem({ mediaEntity }: { mediaEntity: MatchVideo }) {
    return (
        <YoutubePlayer
            height={300}
            width={350}
            play={false}
            videoId={mediaEntity.youtubeVideoId}
        />
    )
}

export default function VideoCarousel({ data }: { data: MatchVideo[] }) {
    return (
        <FlatList
            data={data}
            horizontal
            pagingEnabled
            keyExtractor={(item, index) => index + Date.now() + item.title}
            renderItem={({ item }) => {

                return (
                    <ThemedView style={{
                        width,
                        justifyContent: "center",
                        alignItems: "center",
                        backgroundColor: "#EEE",
                        padding: 16,

                    }}>
                        <ThemedText> Title: {item.title != null ? item.title : "Dont have title"}
                        </ThemedText>
                        <ThemedText> Score player: {item.goalMetadata?.playerName != null ? item.goalMetadata?.playerName : "Dont have scored player"}
                        </ThemedText>
                        {item.mediaPlatform === 'LOCAL' &&
                            <VideoItem mediaEntity={item} />
                        }

                        {item.mediaPlatform === 'YOUTUBE' &&
                            <YoutubeVideoItem mediaEntity={item} />
                        }
                    </ThemedView>
                )
            }}
        />
    );
}