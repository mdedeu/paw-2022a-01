import React from 'react';
import i18n from "../../i18n";

type RatingStarsProps = {
    rating:number
}

const RatingStars : React.FC<RatingStarsProps> = ({rating}) => {
    return (
        <div className="flex items-center flex-wrap ">
            <div className=" flex flex-row">
            { [...Array(Math.trunc(rating))].map((element, key)=>{
                return (
                    <div key={key}>
                        <svg aria-hidden="true" className="w-5 h-5 text-yellow-400" fill="currentColor"
                             viewBox="0 0 20 20"
                             xmlns="http://www.w3.org/2000/svg">
                            <title>Star</title>
                            <path
                                d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-1.175 0l-2.8 2.034c-.784.57-1.838-.197-1.539-1.118l1.07-3.292a1 1 0 00-.364-1.118L2.98 8.72c-.783-.57-.38-1.81.588-1.81h3.461a1 1 0 00.951-.69l1.07-3.292z"></path>
                        </svg>
                    </div>
                );})
            }
            </div>

            <p className="ml-2 text-xs font-medium text-gray-400 text-center">{rating} {i18n.t('outOf')} 5</p>
        </div>
    );
};

export default RatingStars;