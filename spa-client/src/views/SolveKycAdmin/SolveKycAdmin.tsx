import React, {useEffect, useState} from 'react';
import useKycService from "../../hooks/useKycService";
import {KycInformationModel} from "../../types/KycInformationModel";
import {useNavigate, useParams} from "react-router-dom";
import KycInformation from "../../components/KycAdminInformation/KycInformation";
import i18n from "../../i18n";
import {AxiosError} from "axios";
import {toast} from "react-toastify";

const SolveKycAdmin = () => {
    const kycService = useKycService();
    const [kyc,setKyc] = useState<KycInformationModel>();
    const params = useParams();
    const [idPhoto, setIdPhoto] = useState<boolean|null>(true);
    const [activeIdPhoto, setActiveIdPhoto ] = useState<string>("idPhoto");
    const [idPhotoBase64,setIdPhotoBase64] = useState<string>();
    const [validationPhotoBase64,setValidationPhotoBase64] = useState<string>();
    const navigate = useNavigate();


    useEffect(()=>{
        if(params.username)
            getKycInformation(params.username)
    },[])


    useEffect(()=>{
        if(kyc !== undefined){
            getValidationPhoto(kyc) ;
            getIdPhoto(kyc);
        }
    },[kyc])

    async function getValidationPhoto(kyc:KycInformationModel){
        try{
            setValidationPhotoBase64(
                await kycService?.getPicturesByUrl(kyc.validationPhoto)) ;
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


    async function getIdPhoto(kyc:KycInformationModel){
        try{
            setIdPhotoBase64(await kycService?.getPicturesByUrl(kyc.idPhoto)) ;
        }catch (e){
            if( e instanceof AxiosError && (e.response !== undefined || e.message !== undefined))
            {
                const errorMsg =  e.response !== undefined ? e.response.data.message : e.message;
                toast.error(errorMsg);
                navigate('/error/'+errorMsg);

            }
            else toast.error(i18n.t('connectionError') + i18n.t('failedToFetch')+  i18n.t('profilePicture'));
        }
    }

    async function getKycInformation(username:string){
        try{
            const apiCall = await kycService?.getKycInformation(username);
            setKyc(apiCall);
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
        <div className="ml-72 flex  flex-wrap z-10">
            <div className="flex flex-col w-3/5 mt-10">
                <div className="flex flex-row w-full h-[60px]">
                    <div className="w-1/2 h-full mx-2 shadow-l rounded-lg bg-[#FAFCFF] hover:bg-gray-300 cursor-pointer"
                         onClick={()=>{setIdPhoto(true); }}>
                        <h2 id="idphotoText" onClick={()=>setActiveIdPhoto("idPhoto")}
                            className={`underline-offset-2 font-sans text-xl font-bold text-center mt-4 ${activeIdPhoto === "idPhoto" ? " underline ":""}`} >
                            {i18n.t('pictureOfId') }</h2>
                    </div>
                    <div className="underline-offset-2 w-1/2 h-full mx-2 shadow-l rounded-lg bg-[#FAFCFF] hover:bg-gray-300 cursor-pointer"
                         onClick={()=>{setIdPhoto(false)}}>
                        <h2 id="validationphotoText" onClick={()=>setActiveIdPhoto("validationPhoto")}
                            className={`underline-offset-2 font-sans text-xl font-bold text-center mt-4 ${activeIdPhoto === "validationPhoto" ? " underline ":""}`}>
                            {i18n.t('pictureWithFace')}
                        </h2>
                    </div>
                </div>
                {kyc && <div className="w-full h-4/5 mt-10">
                    {idPhoto && <div id="idPhoto" className="border-2 border-gray-400">
                        <img  src={idPhotoBase64} alt={"idPhoto"} className=" w-[500px] mx-auto"/>
                    </div> }
                    {!idPhoto && <div id="validationPhoto" className="border-2 border-gray-400">
                        <img src={validationPhotoBase64} alt={"validationPhoto"} className="w-[500px] mx-auto"/>
                    </div>}
                </div> }
            </div>
            {kyc && params.username && <KycInformation kyc={kyc} username={params.username}/>}
        </div>

    );
};

export default SolveKycAdmin;