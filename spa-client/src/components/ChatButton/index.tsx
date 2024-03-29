import React, {useEffect, useState} from 'react';
import useChatService from "../../hooks/useChatService";
import useUserService from "../../hooks/useUserService";
import {toast} from "react-toastify";
import {useAuth} from "../../contexts/AuthContext";
import {Link} from "react-router-dom";

type ChatButtonProps ={
    tradeId:number,
    unSeenMessages:number
}

const ChatButton:React.FC<ChatButtonProps> = ({tradeId, unSeenMessages}) => {
    const [qUnseenMessagesSeller, setqUnseenMessagesSeller] = useState<number>(0);


    useEffect(()=>{
       setqUnseenMessagesSeller(unSeenMessages);
    },[unSeenMessages])

    return (
        <div className="flex flex-row mx-auto">
            <Link to={`/chat/${tradeId}`}
               className="mx-2 rounded-full my-auto cursor-pointer">
                <svg xmlns="http://www.w3.org/2000/svg" className="h-8 w-8 mx-auto" fill="none"
                     viewBox="0 0 24 24" stroke="currentColor" strokeWidth="2">
                    <path strokeLinecap="round" strokeLinejoin="round"
                          d="M8 12h.01M12 12h.01M16 12h.01M21 12c0 4.418-4.03 8-9 8a9.863 9.863 0 01-4.255-.949L3 20l1.395-3.72C3.512 15.042 3 13.574 3 12c0-4.418 4.03-8 9-8s9 3.582 9 8z"/>
                </svg>
            </Link>
            { qUnseenMessagesSeller > 0 &&
                <div
                    className="-ml-4 w-6 h-5 bg-frostl border-2 font-sans rounded-full flex justify-center items-center">
                    <p className="text-xs">
                        {qUnseenMessagesSeller}
                    </p>
                </div>
            }
        </div>
    );
};

export default ChatButton;