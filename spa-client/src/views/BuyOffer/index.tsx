import React from 'react';
import UserInfo from "../../components/UserInfo/index";
import Message from "../../components/Message";
import TradeStatusAlert from "../../components/TradeStatusAlert";

const BuyOffer = () => {
    return (
        <>
            <UserInfo username={"mdedeu"}
                      email={"mdedeu@itba.edu.ar"}
                      phone_number={"1234141"}
                      last_login={"ayer"}
                      trades_completed={3}
                      rating={4.9}
            />
            <Message left={false} content={"messi"}/>
            <TradeStatusAlert icon={"fa-info"} color={"#EBCB8B"} accentColor={"#816327"} title={"Your trade proposal was sent to the seller!"} subtitle={"Wait for the seller to accept your trade proposal"}/>
        </>
    );
};

export default BuyOffer;