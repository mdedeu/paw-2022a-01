import React, {useEffect} from "react";
import UserModel from "../types/UserModel";
import {internalAuthProvider} from "../scripts/auth";
import useUserService from "../hooks/useUserService";

interface AuthContextType {
    user: UserModel | null;
    setUser: React.Dispatch<React.SetStateAction<UserModel | null>>;
    signin: (
        user: UserModel,
        callback: VoidFunction
    ) => void;
    signout: (callback: VoidFunction) => void;
}

const AuthContext = React.createContext<AuthContextType>(null!);

export function AuthProvider({ children }: { children: React.ReactNode }) {
    const userService = useUserService();
    let [user, setUser] = React.useState<UserModel | null>(null);

    function fetchUser(){
        const username = userService.getLoggedInUser();
        if(username){
            userService.getUser(username).then((user) => {
                setUser(user);
            });
        }
    }

    useEffect(() => {
      fetchUser();
    },[]);


    let signin = (
        newUser: UserModel,
        callback: VoidFunction
    ) => {
        return internalAuthProvider.signin(() => {
            setUser(newUser);
            callback();
        });
    };

    let signout = (callback: VoidFunction) => {
        return internalAuthProvider.signout(() => {
            setUser(null);
            localStorage.removeItem("refreshToken");
            localStorage.removeItem("accessToken");
            callback();
        });
    };

    let value = { user, setUser, signin, signout };

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
    return React.useContext(AuthContext);
}