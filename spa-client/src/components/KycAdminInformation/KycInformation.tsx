import React, {useState} from 'react';
import {KycInformationModel} from "../../types/KycInformationModel";
import useKycService from "../../hooks/useKycService";
import Popup from "reactjs-popup";
import RejectKycForm from "../RejectKycForm/RejectKycForm";
import {useNavigate} from "react-router-dom";
import i18n from "../../i18n";

import {toast} from "react-toastify";
import {AxiosError} from "axios";

type KycCardProp = {
    kyc:KycInformationModel,
    username: string
}
export type SolveKycForm = {
    comments: string,
    status:string
}


const KycInformation = ({kyc,username}:KycCardProp) => {
    const kycService = useKycService();
    const navigate = useNavigate();

    async function acceptKyc() {
        try {
            await kycService.solveKyc(
                {status:"APR",comments:i18n.t('welcome')}
                ,username);
            toast.success(i18n.t('kycSuccessfulAttended'))
            navigate("/admin/kyc")
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error(i18n.t('connectionError'));
        }
    }

    return (
        <div className="flex flex-col w-1/3 mx-auto mt-10">
            <div
                className="flex flex-col shadow-xl rounded-lg py-10 bg-[#FAFCFF] px-5 flex justify-center mx-auto w-3/4">
                <div className="flex flex-col">
                    <h3 className="font-sans font-bold text-lg mb-10 text-xl">{i18n.t('userInformation')}</h3>
                    <div className="flex flex-row">
                        <h4 className="font-sans font-bold mr-2">{i18n.t('names')}</h4>
                        <h3>{kyc.givenNames} </h3>
                    </div>
                    <div className="flex flex-row ">
                        <h4 className="font-sans font-bold mr-2">{i18n.t('surnames')}</h4>
                        <h3>{kyc.surnames}</h3>
                    </div>
                    <div className="flex flex-row ">
                        <h4 className="font-sans font-bold mr-2">{i18n.t('countryOfEmission')}</h4>
                        <h3>{kyc.emissionCountry}</h3>
                    </div>
                    <div className="flex flex-row">
                        <h4 className="font-sans font-bold mr-2"> {i18n.t('idNumber')} </h4>
                        <h3>{kyc.idCode}</h3>
                    </div>
                    <div className="flex flex-row">
                        <h4 className="font-sans font-bold mr-2">{i18n.t('idType')} </h4>
                        <h3 className="mr-2">{kyc.idType}</h3>
                    </div>

                </div>
            </div>
            <div className="flex flex-row mx-auto mt-10">
                <div>
                    <button className="bg-ngreen rounded-lg text-white p-3 mr-10"
                            onClick={() => acceptKyc()}>{i18n.t('approve')}</button>
                </div>
                <button className="bg-frostdr rounded-lg text-white p-3 mr-10" onClick={()=>navigate(-1)}>{i18n.t('back')}</button>
                <Popup contentStyle={{borderRadius: "0.5rem", padding: "1rem"}}
                       trigger={<button className="bg-nred rounded-lg text-white p-3">
                           <p>{i18n.t('reject')}</p>
                       </button>} position="center center" modal>
                    <RejectKycForm username={username}/>
                </Popup>
         </div>
        </div>
    );
};

export default KycInformation;