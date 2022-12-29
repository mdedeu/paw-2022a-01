import React from 'react';
import Message from "../Message";
type ChatSnippetProps = {
    otherUserActive:boolean
}

const ChatSnippet:React.FC<ChatSnippetProps> = ({otherUserActive}) => {
    return (
        <div className="flex flex-row h-full w-full justify-around mr-10 mt-10">
            <div className="container mx-10 h-4/5 F border-gray-200">
                <div className=" border rounded bg-[#FAFCFF]">
                    <div>
                        <div className=" flex flex-col w-full px-3">
                            <div className="flex relative items-center py-3 border-b border-gray-300 justify-between">
                                <div className={"flex"}>
                                    <img className="object-cover w-10 h-10 my-auto rounded-full"
                                         src="imagen de perfil" alt="username"/>
                                    <div className="flex flex-col ml-4">
                                        <span className="block font-bold text-gray-600 text-justify ">username</span>
                                        {otherUserActive && <> <span className="font-sans text-gray-400 text-sm text-justify ">Ultimo login</span>
                                            <span className="text-left text-sm text-justify ">2022-02-1023</span> </>}
                                    </div>
                                </div>
                                {otherUserActive &&  <span className="absolute w-3 h-3 bg-green-600 rounded-full left-7  top-6 "></span>}

                                <h1 className="text-right font-sans font-bold justify-self-end">
                                  Trade proposal # 45
                                </h1>
                            </div>
                            <div className="relative w-full p-6 overflow-y-auto h-[25rem]">

                                <ul className="space-y-2">
                                    <Message left={false} content={"hola"}/>
                                    <Message left={true} content={"hola campeon del mundo"}/>
                                </ul>
                            </div>

                            <div className="flex justify-end w-full p-3  border-gray-300">

                                <form className="flex flex-row w-full">
                                    <input type="text" placeholder="Message"
                                           className="block w-full py-2 pl-4 mr-3 bg-gray-100 rounded-lg outline-none focus:text-gray-700"
                                           name="message" required/>
                                    <input type="hidden" value="${param.tradeId}"/>
                                    <input type="hidden" value="${param.senderId}"/>

                                    <button type="submit">
                                        <svg className="w-5 h-5 text-gray-500 origin-center transform rotate-90"
                                             xmlns="http://www.w3.org/2000/svg"
                                             viewBox="0 0 20 20" fill="currentColor">
                                            <path
                                                d="M10.894 2.553a1 1 0 00-1.788 0l-7 14a1 1 0 001.169 1.409l5-1.429A1 1 0 009 15.571V11a1 1 0 112 0v4.571a1 1 0 00.725.962l5 1.428a1 1 0 001.17-1.408l-7-14z"/>
                                        </svg>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default ChatSnippet;