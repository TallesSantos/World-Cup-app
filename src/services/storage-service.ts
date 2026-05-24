import AsyncStorage from '@react-native-async-storage/async-storage';
import { UserDataSchema } from '../schemas/user';

async function setUser(user: UserDataSchema) {
    await AsyncStorage.setItem("userData", JSON.stringify(user));
}

async function getUser() {
    const userAsString = await AsyncStorage.getItem("userData");
    if (userAsString) {
        return JSON.parse(userAsString) as UserDataSchema;
    }
}

const storageService = {
    setUser,
    getUser
}
export default storageService;
