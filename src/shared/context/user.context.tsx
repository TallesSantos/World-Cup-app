import React, {
    createContext,
    ReactNode,
    use,
    useEffect
} from "react";
import { UserDataSchema } from "../schemas/user";



type UserContextType = {
    userData: UserDataSchema | null
    setUserData: React.Dispatch<React.SetStateAction<UserDataSchema>>
};

const UserContext = createContext<UserContextType | null>(
    null
);

export const UserProvider = ({
    children,
}: {
    children: ReactNode;
}) => {
    const [userData, setUserData] = React.useState<UserDataSchema | null>(null);

    useEffect(() => {
    }, []);
    return (
        <UserContext.Provider
            value={{
                userData,
                setUserData
            }}
        >
            {children}
        </UserContext.Provider>
    );
};

export const useUserContext = () => {
    const ctx = use(UserContext);
    if (!ctx)
        throw new Error(
            "useUserContext deve ser usado dentro de um Provider"
        );
    return ctx;
};
