import {SetStateAction, useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import { useLocation, useNavigate } from "react-router-dom";
import CryptoFilters from "../../components/CryptoFilters/index";
import Paginator from "../../components/Paginator";
import toast from "react-hot-toast";
import Loader from "../../components/Loader";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]|null>(null);
    // [{
    //     cryptoCode:"BTC",
    //     date:new Date(),
    //     location:"Balvanera",
    //     maxInCrypto:2,
    //     minInCrypto:0.001,
    //     offerId:1,
    //     offerStatus:"PENDING",
    //     unitPrice:1000000,
    //     url:"/offer/1",
    //     seller:"marquitos",
    //     trades:"1"
    // },
    //     {
    //         cryptoCode:"BTC",
    //         date:new Date(),
    //         location:"Balvanera",
    //         maxInCrypto:2,
    //         minInCrypto:0.001,
    //         offerId:1,
    //         offerStatus:"PENDING",
    //         unitPrice:1000000,
    //         url:"/offer/1",
    //         seller:"marquitos",
    //         trades:"1"
    //     },
    //     {
    //         cryptoCode:"BTC",
    //         date:new Date(),
    //         location:"Balvanera",
    //         maxInCrypto:2,
    //         minInCrypto:0.001,
    //         offerId:1,
    //         offerStatus:"PENDING",
    //         unitPrice:1000000,
    //         url:"/offer/1",
    //         seller:"marquitos",
    //         trades:"1"
    //     },
    //     {
    //         cryptoCode:"BTC",
    //         date:new Date(),
    //         location:"Balvanera",
    //         maxInCrypto:2,
    //         minInCrypto:0.001,
    //         offerId:1,
    //         offerStatus:"PENDING",
    //         unitPrice:1000000,
    //         url:"/offer/1",
    //         seller:"marquitos",
    //         trades:"1"
    //     },{
    //     cryptoCode:"BTC",
    //     date:new Date(),
    //     location:"Balvanera",
    //     maxInCrypto:2,
    //     minInCrypto:0.001,
    //     offerId:1,
    //     offerStatus:"PENDING",
    //     unitPrice:1000000,
    //     url:"/offer/1",
    //     seller:"marquitos",
    //     trades:"1"
    // }
    //     ,{
    //     cryptoCode:"BTC",
    //     date:new Date(),
    //     location:"Balvanera",
    //     maxInCrypto:2,
    //     minInCrypto:0.001,
    //     offerId:1,
    //     offerStatus:"PENDING",
    //     unitPrice:1000000,
    //     url:"/offer/1",
    //     seller:"marquitos",
    //     trades:"1"
    // },{
    //     cryptoCode:"BTC",
    //     date:new Date(),
    //     location:"Balvanera",
    //     maxInCrypto:2,
    //     minInCrypto:0.001,
    //     offerId:1,
    //     offerStatus:"PENDING",
    //     unitPrice:1000000,
    //     url:"/offer/1",
    //     seller:"marquitos",
    //     trades:"1"
    // }
    //     ,{
    //     cryptoCode:"BTC",
    //     date:new Date(),
    //     location:"Balvanera",
    //     maxInCrypto:2,
    //     minInCrypto:0.001,
    //     offerId:1,
    //     offerStatus:"PENDING",
    //     unitPrice:1000000,
    //     url:"/offer/1",
    //     seller:"marquitos",
    //     trades:"1"
    // }]
    const offerService = useOfferService();
    
    const navigate = useNavigate(); 
    const location = useLocation();

    async function getOffers(page:number, pageSize:number){
        const apiCall = await offerService?.getOffers(page, pageSize);

        if(apiCall.statusCode === 200){
            setOffers(apiCall.getData());
        }else{
            toast.error("Something went wrong, check your connectivity");
        }
    }

    useEffect(  ()=>{
         const fetchOffers = async () =>  {
             await getOffers(5,5);
         }

         fetchOffers();

    }, [offerService, location, navigate])

    return (<>
            <div className="flex flex-wrap w-full h-full justify-between">
                <div className="flex w-1/3">
                    {/*TODO this callback should update offers*/}
                    <CryptoFilters callback={()=>console.log("messi")}/>
                </div>

                <div className="flex flex-col w-2/3 mt-10">
                    <div className="flex flex-row mx-10 justify-between">
                        <div className="flex flex-row">
                            <h3 className="font-bold mx-2 my-auto">Order by</h3>
                            <form>
                                <select className="p-2 rounded-lg">
                                    <option>Lowest Price</option>
                                    <option>Most recent</option>
                                    <option>Best Rated user</option>
                                    <option>Higher price</option>
                                    <option>Seller Last login</option>
                                </select>
                            </form>
                        </div>
                        <div>
                            <h3 className="text-gray-400">You got 18 results</h3>
                        </div>

                    </div>
                    {offers ?
                        <>
                            {offers.map((offer => <CryptoCard offer={offer} key={offer.offerId}></CryptoCard>))}
                            <Paginator totalPages={10} actualPage={1} callback={() => console.log("called")}/>
                        </>
                        :
                        <Loader/>
                    }
                </div>
            </div>
    </>


    )

}; 

export default Landing; 