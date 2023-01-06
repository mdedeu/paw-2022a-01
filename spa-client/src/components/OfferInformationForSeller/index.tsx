import React, {useEffect, useState} from 'react';
import TransactionModel from "../../types/TransactionModel";
import RatingStars from "../RatingStars";
import ChatButton from "../ChatButton";
import OfferModel from "../../types/OfferModel";
import UserModel from "../../types/UserModel";

type OfferInformationForSellerProps = {
    trade:TransactionModel,
    chat:boolean
}
const OfferInformationForSeller: React.FC<OfferInformationForSellerProps>= ({trade, chat}) => {
    const [tradeStatus, setTradeStatus] = useState<string>(trade.status);
    const [offer, setOffer] = useState<OfferModel>();
    const [buyer, setBuyer] = useState<UserModel>();

    async function fetchBuyer(){

    }

    useEffect(()=>{
        fetchBuyer();
    },[])

    async function fetchOffer(){
        //fetch offer from offer? . Split to get offer id or get directly
    }

    useEffect(()=>{
        fetchOffer();
    },[])


    function changeStatus(status:string, tradeId:number){
        //change with api , if ok then
        setTradeStatus(status);
    }

    return (
        <div className="flex flex-col justify-center px-10">
            <div
                className="bg-[#FAFCFF] text-center font-sans text-xl font-bold p-4 shadow-xl flex flex-col rounded-lg justify-between w-full mb-3 text-polar">
               Offer Information
            </div>
            <div className="bg-[#FAFCFF] p-4 shadow-xl flex flex-col rounded-lg justify-between mb-12 ">
                <div className="flex font-sans h-fit w-full mt-2">
                    {
                        tradeStatus === 'sold' &&
                        <div className="font-semibold bg-gray-400 w-full text-white text-center p-2 rounded-lg">
                           Sold
                        </div>
                    }
                    {
                        tradeStatus === 'pending' &&
                        <div className=" font-semibold bg-nyellow  w-full text-white text-center p-2 rounded-lg">
                            Pending
                        </div>
                    }
                    {
                        tradeStatus === 'rejected' &&
                        <div className=" font-semibold bg-nred/[0.6] w-full text-white  text-center p-2 rounded-lg">
                            Rejected
                        </div>
                    }
                    {
                        tradeStatus === 'accepted' &&
                        <div className=" font-semibold bg-ngreen  w-full text-white  text-center p-2 rounded-lg">
                            Accepted
                        </div>
                    }
                </div>

                <div className="flex flex font-sans my-3  w-56 mx-auto text-semibold">
                    <h1 className="mx-auto">
                        {trade.buyingQuantity + ' ' + offer?.cryptoCode}
                        ⟶ {trade.buyingQuantity * (offer? offer.unitPrice: 1)} ARS
                    </h1>
                </div>

                <div className="flex flex-col my-2">
                    <h1 className="font-bold font-roboto text-polar mx-auto text-center">Buyer:</h1>
                    <div className="flex mx-auto">
                        <h1 className=" text-lg font-sans font-semibold text-center">
                            {buyer?.username}
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="font-sans font-semibold text-center text-gray-400">
                            {buyer?.email}
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        <h1 className="text-xs text-gray-400 font-sans font-semibold text-center">
                            {buyer?.phoneNumber}
                        </h1>
                    </div>
                    <RatingStars rating={buyer? buyer.rating: 0}/>
                </div>

                {tradeStatus === 'sold' &&
                    <a className="mx-auto bg-gray-200  font-bold cursor-pointer text-polard hover:border-polard hover: border-2 p-3 h-12 justify-center rounded-md font-sans text-center w-40"
                       href="/support">
                        Help
                    </a>
                }
                {tradeStatus === 'pending' &&
                    <div className="flex flex-row">
                        <form method="post" className="flex justify-center mx-auto my-3">
                            <button type="submit"
                                    className="font-bold bg-red-400 text-white p-3  rounded-lg font-sans mr-4" onClick={()=>changeStatus('rejected', trade.tradeId)}>
                                Reject
                            </button>
                        </form>

                        <form className="flex justify-center mx-auto my-3">
                            <button type="submit"
                                    className="font-bold bg-ngreen text-white p-3 rounded-lg font-sans " onClick={()=>changeStatus('accepted', trade.tradeId)}>
                                Accept
                            </button>
                        </form>
                    </div>
                }

                {
                    tradeStatus === 'accepted' &&


                    <form className="flex justify-center mx-auto my-3">
                        <button type="submit"
                                className="font-bold w-fit bg-gray-500 text-white p-3 rounded-lg font-sans mx-auto" onClick={()=>changeStatus('sold', trade.tradeId)}>
                            Mark as sold
                        </button>
                    </form>
                }
                {
                    tradeStatus !== 'accepted' && tradeStatus !== 'pending' &&
                    <div className="flex h-2/5 my-2"/>
                }
                {
                    chat && <ChatButton tradeId={trade.tradeId} />
                }
            </div>
        </div>

    );
};

export default OfferInformationForSeller;