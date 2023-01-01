import React, {useEffect, useState} from 'react';
import {CryptocurrencyModel} from "../../types/Cryptocurrency";
import useCryptocurrencyService from "../../hooks/useCryptocurrencyService";
import Result from "../../types/Result";
import {NEIGHBORHOODS} from "../../common/constants";
import toast from "react-hot-toast";
import {useForm} from "react-hook-form";

type UploadFormValues = {
    minInCrypto:number,
    maxInCrypto:number,
    location:string,
    unitPrice:number,
    cryptoCode:string,
    automaticResponse?:string
}
//TODO- Errors and pattern validations

const UploadForm = () => {

    //State

    const [cryptocurrencies, setCryptoCurrencies] = useState<CryptocurrencyModel[]>([]);
    const cryptocurrencyService = useCryptocurrencyService();

     function fetchCryptocurrencies(){
        const apiCall:Result<CryptocurrencyModel[]> = cryptocurrencyService.getCryptocurrencies();

        if(apiCall.statusCode === 200){
            setCryptoCurrencies(apiCall.getData());
        } else{
            toast.error("Something went wrong.");
        }
    }
    //TODO: This feels like a bad practice. Maybe useEffect on selected value?
    function changeSuggestedPrice(){
        const selectCryptos:HTMLSelectElement = document.getElementById("cryptoSelected")! as HTMLSelectElement;
        const cryptoModel:CryptocurrencyModel = cryptocurrencies.find(cryptocurrency=> cryptocurrency.code ===  selectCryptos.value)!;
        const price = document.getElementById("priceCrypto") as HTMLElement;
        price.innerHTML = cryptoModel?.price ? cryptoModel?.price.toString() + ' ARS': 'No price detected' ;
    }

    useEffect(()=>{
        fetchCryptocurrencies();
    },[])

    //Form
    const { register, handleSubmit, formState: { errors } } = useForm<UploadFormValues>();

    function onSubmit(data:UploadFormValues) {
        console.log(data);
    }

    return (
        <div className="flex flex-row mx-auto">
            <form className="flex flex-col min-w-[50%]" onSubmit={handleSubmit(onSubmit)}>
                <div className="flex flex-row divide-x">
                    <div className="flex flex-col mx-5 w-1/3">
                        <h1 className="font-sans text-polar font-bold text-xl text-center">1. Price
                            Settings </h1>
                        <div className="flex flex-col justify-center">
                            <label
                                className="text-lg font-sans text-polard  mb-3 mt-2 text-center">Cryptocurrency*</label>
                            <div className="flex flex-row justify-center mx-auto">
                                <select className="rounded-lg p-3" onSelect={changeSuggestedPrice} id="cryptoSelected" {...register("cryptoCode",{required:true})} defaultValue={"DEFAULT"}>
                                    <option disabled value="DEFAULT">Choose an option</option>
                                    {
                                        cryptocurrencies.map((cryptocurrency)=>{
                                           return (
                                               <option value={cryptocurrency.code} key={cryptocurrency.code}>
                                                   {cryptocurrency.name}
                                               </option>
                                           );
                                        })
                                    }
                                </select>
                            </div>
                            <h1 className="flex flex-row mx-auto mt-4">
                                <p className="text-sm text-gray-400 mr-2">*Suggested Price </p>
                                <p className="text-sm text-gray-400" id="priceCrypto">Select a coin </p>
                            </h1>
                        </div>
                        <div className="flex flex-col mt-4">
                            <label className="text-lg font-sans text-polard  mb-3 text-center ">Price per unit in
                                ARS*</label>
                            <div className="flex flex-col justify-center ">
                                <input type="number" className="h-10 justify-center rounded-lg p-3 mx-auto "
                                       step=".01"
                                       {...register("unitPrice", {required:true})}
                                />
                            </div>
                        </div>
                        <div className="flex flex-col justify-center mt-4">
                            <h2 className="text-lg font-sans text-polard mb-3 text-center flex flex-row justify-center ">Limits*</h2>
                            <div className="flex flex-row justify-center">
                                <div>
                                    <label
                                        className="text-sm font-sans text-polard mb-3 text-center flex flex-row justify-center ">
                                       Min in
                                        <p id="minCoin" className="mx-2">BTC</p></label>
                                    <div className="flex flex-row justify-center mx-auto">
                                        <input type="number" className="h-10 justify-center rounded-lg p-3 mx-5 w-20"
                                               step=".00000001"
                                               {...register("minInCrypto", {required:true})}/>
                                    </div>
                                </div>
                                <div className="my-5">
                                    -
                                </div>
                                <div>
                                    <label
                                        className="text-sm font-sans text-polard mb-3 text-center flex flex-row justify-center">
                                        Max in
                                        <p id="maxCoin" className="mx-2">BTC</p></label>
                                    <div className="flex flex-row justify-center mx-auto">
                                        <input type="number" className="h-10 justify-center rounded-lg p-3 mx-5 w-20"
                                               step=".00000001"
                                               {...register("maxInCrypto", {required:true})}/>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                    <div className="flex flex-col mx-auto w-1/3 ">
                        <label
                            className="text-xl font-sans text-polar font-bold mb-3 text-center ">2.
                            Location</label>
                        <div className="flex flex-col justify-center px-5">
                            <h2 className="text-lg font-sans text-polard text-center flex flex-row justify-center my-3">Neighborhood*</h2>
                            <select className="font-sans text-polard mb-3 text-center rounded-lg p-2 " {...register("location",{required:true})} defaultValue={"DEFAULT"}>
                                <option disabled value="DEFAULT">Choose option</option>
                                { NEIGHBORHOODS.map((neighborhood)=>{
                                    return (
                                        <option value={neighborhood} key={neighborhood}>
                                            {neighborhood}
                                        </option>
                                    );
                                })}
                            </select>
                        </div>
                    </div>
                    <div className="flex flex-col px-10 w-1/3">
                        <label className="text-xl font-sans text-polar font-bold mb-3 text-center ">3. Automatic
                            Response</label>
                        <h2 className="text-justify">You can set an automatic response every time someone makes you a
                            trade proposal </h2>
                        <div className="flex flex-row justify-center w-80 mx-auto mt-2">
                            <textarea className="w-full h-36 rounded-lg mx-auto p-5"
                                      {...register("automaticResponse")}/>
                        </div>
                    </div>
                </div>
                <div className="flex flex-row p-5 mx-auto">
                    <a className=" cursor-pointer bg-polarlr/[0.6] text-white text-center mt-4 p-3 rounded-md font-sans mx-5 w-32"
                       href="/">Cancel
                    </a>
                    <button type="submit"
                            className="bg-frostdr text-white  mt-4 p-3 rounded-md font-sans  w-32 mx-5 active:cursor-progress">
                        Submit
                    </button>
                </div>
            </form>
        </div>
    );
};

export default UploadForm;