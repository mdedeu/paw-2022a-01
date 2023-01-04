import React, {useEffect, useState} from 'react';
import OfferCardProfile from "../../components/OfferCardProfile";
import OfferModel from "../../types/OfferModel";
import Paginator from "../../components/Paginator";
import TransactionModel from "../../types/TransactionModel";
import OfferInformationForSeller from "../../components/OfferInformationForSeller";

const SellerOfferDashboard = () => {

    const [selectedStatus, setSelectedStatus] = useState<string>("all");
    const [trades, setTrades] = useState<TransactionModel[]>([
        {
            status:'rejected',
            buyer:{
                accessToken: "",
                refreshToken: "string",
                admin: false,
                email:"mdedeu@itba.edu.ar",
                phoneNumber:"1245311",
                username:"mdedeu",
                lastLogin:"online",
                trades_completed:1,
                rating:1.3,
                image_url:"/"
            },
            offer: {
                cryptoCode:"BTC",
                date:new Date(),
                location:"Balvanera",
                maxInCrypto:2,
                minInCrypto:0.001,
                offerId:1,
                offerStatus:"PENDING",
                unitPrice:1000000,
                seller: {
                    accessToken: "",
                    refreshToken: "string",
                    admin: false,
                    email:"mdedeu@itba.edu.ar",
                    phoneNumber:"1245311",
                    username:"mdedeu",
                    lastLogin:"online",
                    trades_completed:1,
                    rating:1.3,
                    image_url:"/"
                }
            },
            amount: 1000,
            id:1,
            date: new Date()
        }

    ]);
    const [offer, setOffer] = useState<OfferModel>({
        cryptoCode:"BTC",
        date:new Date(),
        location:"Balvanera",
        maxInCrypto:2,
        minInCrypto:0.001,
        offerId:1,
        offerStatus:"PENDING",
        unitPrice:1000000,
        seller: {
            accessToken: "",
            refreshToken: "string",
            admin: false,
            email:"mdedeu@itba.edu.ar",
            phoneNumber:"1245311",
            username:"mdedeu",
            lastLogin:"online",
            trades_completed:1,
            rating:1.3,
            image_url:"/"
        }
    });

    useEffect(()=>{
        //fetch trade. If no results, fetch offer. Else, setOffer trade[0].offer
    }, [trades, offer])


    function changeStatus(status:string){
        //fetch to our service that asks for trades in specific state
        setSelectedStatus(status);
    }


    return (<>
            <div className="flex flex-row h-full w-full px-20 my-10">
                <div className="flex flex-col h-3/5 w-1/5 pr-2">
                    <div className="flex flex-col w-full py-3 rounded-lg px-5 pt-4 rounded-lg bg-[#FAFCFF]">
                        <h1 className="font-sans w-full mx-auto text-center text-2xl font-bold">
                            Trade proposals received
                        </h1>
                    </div>
                    <div className="flex mx-auto">
                        {offer && <OfferCardProfile offer={offer}/>}
                    </div>

                    <a href="/"
                       className=" font-bold rounded-lg bg-frost py-3 px-5 text-l font-sans text-center text-white cursor-pointer shadow-lg">
                        Back
                    </a>
                </div>
                <div className="flex flex-col w-4/5">
                    <div className="flex flex-row  rounded-lg px-5 rounded-lg  justify-between">
                        <div
                            className="flex mr-5 bg-white rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button  className="my-auto mx-auto w-full " onClick={()=>changeStatus('all')}>
                                <p className={`py-2 px-4 font-bold text-polar text-center ${selectedStatus === 'all' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    All
                                </p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-nyellow rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>{changeStatus('pending')}} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === 'pending' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Pending</p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-ngreen rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>{changeStatus('accepted')}} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === 'accepted' ? 'decoration-frostdr underline underline-offset-8' : 'text-l'}`}>
                                    Ongoing</p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-nred rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>changeStatus('rejected')} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === 'rejected' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Rejected by you</p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-gray-200 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>changeStatus('sold')} className="my-auto mx-auto">
                                <p className={`py-2 px-4 font-bold text-polar  text-center ${selectedStatus === 'sold' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Closed </p>
                            </button>
                        </div>
                        <div
                            className=" flex mr-5 bg-blue-400 rounded-lg shadow-md py-1 w-full hover:-translate-y-1 hover:scale-110 duration-200 h-16">
                            <button onClick={()=>{changeStatus('deleted')}} className="my-auto mx-auto">
                                <p className={`py-2  px-4 font-bold text-polar text-center ${selectedStatus === 'deleted' ? 'decoration-frostdr underline underline-offset-8' : 'text-l '}`}>
                                    Deleted by buyer</p>
                            </button>
                        </div>
                    </div>
                    <div className="flex flex-col">
                        <div className="flex flex-wrap pl-3 mt-10 mx-auto">
                            {trades && trades.map((trade)=>{
                                return(
                                    <OfferInformationForSeller trade={trade} chat={true} key={trade.id}/>
                                );
                            })}
                        </div>

                        <h1 className="mx-auto text-center">
                            {trades.length>0 ?  <Paginator totalPages={10} actualPage={1} callback={() => console.log("a")}/>: <h1 className="text-polar font-bold"> No trade proposals related to this offer</h1>}

                        </h1>
                    </div>
                </div>
            </div>
        </>
    );
};

export default SellerOfferDashboard;