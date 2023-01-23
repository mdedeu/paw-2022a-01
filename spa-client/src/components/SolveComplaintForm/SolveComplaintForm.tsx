import React from 'react';
import {useForm} from "react-hook-form";
import { XCircleIcon, InformationCircleIcon} from "@heroicons/react/24/outline";
import useComplainService from "../../hooks/useComplainService";
import {useNavigate} from "react-router-dom"

export interface SolveComplaintFormModel {
    comments:string,
    resolution: string
    complainId: number
}

type props = {
    other:string,
    resolution:string,
    complainId: number
}


const SolveComplaintForm = ({other,resolution,complainId}:props) => {
    const complainService = useComplainService();
    const navigate = useNavigate();
    const { register, handleSubmit, formState: { errors } } = useForm<SolveComplaintFormModel>();


    function onSubmit(data:SolveComplaintFormModel) {
        //TODO: ver el codigo de retorno del post y ver si se posteo o no.
        complainService.createComplainResolution(data).then(r => navigate("/admin") );
    }

    return (
        <div className="w-full flex flex-col mt-5">
            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="flex flex-row bg-white shadow rounded-lg p-3 font-sans font-bold">
                    <InformationCircleIcon className="w-5 h-5 mr-4 my-auto "/>
                    <p>
                        {resolution==="DISMISS" && ` Estás por desestimar la denuncia de ${other}. `}
                        {resolution==="KICK" && ` Estás por banear a ${other}. ` }
                        Recuerda que el veredicto no es reversible, debes estar seguro de la decisión.  </p>
                </div>
                <input type="string" className="min-w-full h-32 rounded-lg mx-auto p-5 mt-5"
                       step=".01"
                       {...register("comments", {required:"You must comment your resolution"})}
                />
                {errors && errors.comments && <p className="text-red-600 mx-auto mt-2">{errors.comments.message}</p> }
                <input type="hidden"  value={resolution} {...register("resolution")} />
                <input type="hidden"  value={complainId} {...register("complainId")} />
                <button type="submit" className="mt-3 w-1/5 mx-auto bg-frost rounded-lg text-white p-3">
                    Enviar
                </button>
            </form>
        </div>
    );
};

export default SolveComplaintForm;