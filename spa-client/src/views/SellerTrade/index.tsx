import React, {useEffect, useState} from 'react';
import ChatSnippet from "../../components/ChatSnippet";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";
import {Link, useNavigate, useParams} from "react-router-dom";
import useTradeService from "../../hooks/useTradeService";
import TransactionModel from "../../types/TransactionModel";
import useUserService from "../../hooks/useUserService";
import UserModel from "../../types/UserModel";
import i18n from "../../i18n";
import {toast} from "react-toastify";
import {AxiosError} from "axios";

const SellerTrade = () => {
    const tradeService = useTradeService();
    const userService = useUserService();

    const params = useParams();
    const [trade, setTrade] = useState<TransactionModel>();
    const [counterPart, setCounterPart] = useState<UserModel>();
    const navigate = useNavigate();

    async function fetchTrade(){
        const tradeId = params.id;
        try{
            const resp = await tradeService.getTradeInformation(Number(tradeId));
            setTrade(resp);
        }catch (e) {
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error(i18n.t('connectionError') + i18n.t('failedToFetch') + i18n.t('trades'));
        }
    }
    async function fetchCounterPart(){
        if(trade){
            try{
                const resp = await userService.getUser(userService.getUsernameFromURI(trade.buyer));
                setCounterPart(resp);
            }catch (e) {
                toast.error(i18n.t('connectionError'));
            }
        }
    }

    useEffect(()=>{
        fetchTrade();
    },[])

    useEffect(()=>{
        fetchCounterPart();
    },[trade])

    return (
        <div className="flex flex-row mt-7">
            <ChatSnippet  counterPart={counterPart} tradeId={trade?.tradeId!}/>
            <div className="flex flex-col justify-center px-10">
                <div className="flex flex-col justify-center px-10">
                    <div
                        className="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between w-full mb-3 text-polar">
                        {i18n.t('aboutTheOffer')}
                    </div>
                    <OfferInformationForSeller trade={trade!} chat={false}/>
                </div>

            <div className="flex flex-row mx-auto">

                <button  onClick={()=>{navigate(-1)}} className=" cursor-pointer  font-bold bg-gray-300 mx-2 px-6 py-3  rounded-lg text-white">
                    {i18n.t('back')}
                </button>
                <button className="bg-gray-200 hover:bg-gray-300 text-polard p-3 font-sans rounded-lg mx-2 font-bold" onClick={()=>navigate("/trade/"+trade?.tradeId+"/support")}>{i18n.t('iHadAProblema')}</button>


            </div>
            </div>
        </div>

    );
};

export default SellerTrade;