import { ImageSourcePropType } from "react-native";

export const imageService = {
    getImageByUrl: getImageByUrl
}

async function getImageByUrl(url: string): Promise<ImageSourcePropType> {

    return {
        uri: url,
    };
}
