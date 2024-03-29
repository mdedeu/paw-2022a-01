package ar.edu.itba.paw.cryptuki.mapper.NotFound;

import ar.edu.itba.paw.exception.NoSuchOfferException;

import javax.ws.rs.ext.Provider;

@Provider
public class NoSuchOfferExceptionMapper extends NotFoundMapper<NoSuchOfferException> {

    private static final String MESSAGE = "Offer with id:'%d' does not exist";

    @Override
    public String getMessage() {
        return String.format(MESSAGE,this.exception.getOfferId());
    }
}
