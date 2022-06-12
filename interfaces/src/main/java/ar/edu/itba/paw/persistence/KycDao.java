package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.KycStatus;

import java.util.Collection;


public interface KycDao {

    void newKycRequest(KycInformation.KycInformationBuilder builder);
    void setKycRequestStatus(KycStatus status, int kycId);
    Collection<KycInformation> getKycRequestsByStatus(String username, KycStatus status);

    Collection<KycInformation> getKycRequestsByStatus(KycStatus status, int page, int pageSize);
    long countKycRequestsByStatus(String username, KycStatus status);
}