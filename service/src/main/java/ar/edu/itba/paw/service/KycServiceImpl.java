package ar.edu.itba.paw.service;

import ar.edu.itba.paw.model.KycInformation;
import ar.edu.itba.paw.model.KycStatus;
import ar.edu.itba.paw.parameterObject.KycInformationPO;
import ar.edu.itba.paw.persistence.KycDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;

@Service
public class KycServiceImpl implements KycService {

    private final KycDao kycDao;

    @Autowired
    public KycServiceImpl(KycDao kycDao) {
        this.kycDao = kycDao;
    }

    @Override
    @Transactional
    public void newKycRequest(KycInformationPO kycInformationPO) {
        kycDao.newKycRequest(kycInformationPO);
    }

    @Override
    @Transactional
    public Optional<KycInformation> getPendingKycRequest(String username) {
        return kycDao.getKycRequestsByStatus(username, KycStatus.PEN).stream().findFirst();
    }

    @Override
    @Transactional
    public Collection<KycInformation> getPendingKycRequests(int page, int pageSize) {
        return kycDao.getKycRequestsByStatus(KycStatus.PEN, page, pageSize);
    }


    @Override
    @Transactional
    public void validateKycRequest(int kycId) {
        // TODO: enviar mail con confirmacion
        kycDao.setKycRequestStatus(KycStatus.APR, kycId);
    }

    @Override
    @Transactional
    public void rejectKycRequest(int kycId, String reason) {
        // TODO: enviar mail con la razon
        kycDao.setKycRequestStatus(KycStatus.REJ, kycId);
    }

}
