import { useEffect } from "react";
import { useAuth } from "./useAuth";

let useRefreshToken = ()=>(() => null);  // implement
let refresh = () => null 

const useAxiosPrivate = () => {

    // const auth = useAuth(); 
    
    // useEffect( ()=> {

    //     axiosPrivate.interceptors.request.clear();
    //     axiosPrivate.interceptors.response.clear();

    //     const requestIntercept = axiosPrivate.interceptors.request.use(
    //         (config: any) => {
    //             if ( ! config.headers?['Authorization'] : Boolean) { // Ese boolean esta menos chequeado 
    //                 // It is not a retry, it is the first attempt
    //                 // Just embed the already-calculated access token to the request 
    //                 config.headers['Authorization'] = `Digest begoxa:begoxa` //  ${auth?.user?.accessToken}`; 
    //             }
    //             return config; 
    //         }, 
    //         (error: any) => {
    //             Promise.reject(error)
    //         }
    //     )

    //     const responseIntercept = axiosPrivate.interceptors.response.use(
    //         response => response, // Everything SHOULD be okay ... 
    //         async (error) => {  // But if it is not, it must be because of the token 
    //             const previousRequest = error?.config; 
    //             if (error?.response?.status === '401' && !previousRequest.sent) {
    //                 previousRequest.set = true; // Avoid endless loop 
    //                 const newAccessToken = await refresh(); 
    //                 previousRequest.headers['Authorization'] = `Bearer ${newAccessToken}`; 
    //                 return axiosPrivate(previousRequest); 
    //             }
    //             return Promise.reject(error); 
    //         }
    //     )
    // }, [auth, refresh])

    // return axiosPrivate; 
}

export default useAxiosPrivate; 