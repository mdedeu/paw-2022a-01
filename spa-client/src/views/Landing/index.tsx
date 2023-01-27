import {useEffect, useState} from "react";
import './styles.css';
import CryptoCard from "../../components/CryptoCard";
import OfferModel from "../../types/OfferModel";
import useOfferService from "../../hooks/useOfferService";
import CryptoFilters, {CryptoFormValues} from "../../components/CryptoFilters/index";
import Paginator from "../../components/Paginator";
import {toast} from "react-toastify";
import Loader from "../../components/Loader";
import useUserService from "../../hooks/useUserService";
import {OFFER_STATUS} from "../../common/constants";
import {PaginatorPropsValues} from "../../types/PaginatedResults";
import {useAuth} from "../../contexts/AuthContext";
import {attendError} from "../../common/utils/utils";

const Landing = () => {

    const [offers, setOffers] = useState<OfferModel[]|null>();
    const offerService = useOfferService();
    const [isLoading, setIsLoading] = useState<boolean>(true);
    const [offerParams, setOfferParams] = useState<URLSearchParams>(new URLSearchParams());
    const {user} = useAuth();
    const userService = useUserService();

    const [paginatorProps, setPaginatorProps] = useState<PaginatorPropsValues>({
        actualPage: 0,
        totalPages: 0,
        nextUri:'',
        prevUri:''
        }
    );


    async function getOffers(){
        try{

            setIsLoading(true);

            const params =  new URLSearchParams();
            if(paginatorProps){
                params.append('page', paginatorProps.actualPage.toString());
            }
            params.append('status', OFFER_STATUS.Pending);
            params.append('exclude_user', userService.getLoggedInUser()!);

            const apiCall = await offerService?.getOffers(params);

            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setIsLoading(false);

        }catch (e){
            attendError("Connection error. Failed to fetch offers",e)
        }
    }
    //Callback from Paginator component
    async function getPaginatedOffers(uri:string){
        try{
            const params = offerService.getSearchParamsFromURI(uri);

            setIsLoading(true);

            const apiCall = await offerService?.getOffers(params);

            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setIsLoading(false);

        }catch (e){
            attendError("Connection error. Failed to fetch paginated offers",e)
        }
    }

    async function orderOffers(order_by:string){
        try{
            setIsLoading(true);

            offerParams.append('order_by', order_by);
            offerParams.append("exclude_user", userService.getLoggedInUser()!);

            const apiCall = await offerService?.getOffers(offerParams);

            setIsLoading(false);
            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);

        }catch (e) {
            attendError("Connection error. Failed to fetch ordered offers",e)
        }
    }

    async function getOffersWithFilters(data:CryptoFormValues){
        try{
            setIsLoading(true);

            const params =  new URLSearchParams();
            params.append('status', OFFER_STATUS.Pending);
            params.append('exclude_user', userService.getLoggedInUser()!);

            data.cryptos?.forEach((crypto) => {
                params.append('crypto_code', crypto);
            });

            data.locations?.forEach((location) => {
                params.append('location', location);
            });

           if(data.amount && data.amountCurrency){
               params.append('amount', data.amount.toString());
               params.append('amountCurrency', data.amountCurrency);
           }

            const apiCall = await offerService?.getOffers(params);

            setOffers(apiCall.items);
            setPaginatorProps(apiCall.paginatorProps!);
            setOfferParams(params);
            setIsLoading(false);

        }catch (e){
            attendError("Connection error. Failed to fetch offers",e)
        }
    }

    useEffect(() => {
        getOffers();
    }, [user]);

    return (<>
            <div className="flex flex-wrap w-full h-full justify-between">
                <div className="flex w-1/3">
                    <CryptoFilters callback={getOffersWithFilters}/>
                </div>
                        <>
                            <div className="flex flex-col w-2/3 mt-10">
                                <div className="flex flex-row mx-10 justify-between">
                                    <div className="flex flex-row">
                                        <h3 className="font-bold mx-2 my-auto">Order by</h3>
                                        <select className="p-2 rounded-lg" onChange={(e) => {
                                            orderOffers(e.target.value)
                                        }}>
                                            <option value={"PRICE_LOWER"}>Lowest Price</option>
                                            <option value={"DATE"}>Most recent</option>
                                            <option value={"RATE"}>Best Rated user</option>
                                            <option value={"PRICE_UPPER"}>Higher price</option>
                                            <option value={"LAST_LOGIN"}>Seller Last login</option>
                                        </select>
                                    </div>
                                    {!isLoading &&
                                        <div>
                                            <h3 className="text-gray-400">You got {offers ? offers.length : 0} results</h3>
                                        </div>
                                    }
                                </div>
                                {!isLoading && offers && offers.map((offer => <CryptoCard offer={offer}
                                                                            key={offer.offerId}></CryptoCard>))}
                                { !isLoading &&  offers && offers.length > 0 &&
                                    <Paginator paginatorProps={paginatorProps} callback={getPaginatedOffers}/>}
                                {!isLoading &&  (!offers || offers.length === 0) &&
                                    <h1 className={"text-xl font-bold text-polar mx-auto my-auto"}> No offers available
                                        at this moment</h1>}
                                {isLoading &&
                                    <div className="flex flex-col w-2/3 mt-10 mx-auto">
                                    <Loader/>
                                </div>
                                }
                            </div>
                        </>
            </div>
    </>


    )

}; 

export default Landing; 