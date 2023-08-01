package tr.com.minesoft.minetrack.service;

import tr.com.minesoft.minetrack.db.DAO;
import tr.com.minesoft.minetrack.db.DAOHelper;
import tr.com.minesoft.minetrack.model.SignalModel;

public class SignalModelService {
    public static SignalModel save(SignalModel signalModel){
        DAO<SignalModel, String> signalRepo = DAOHelper.getSignalRepo();
        boolean inserted = false;
        if (signalRepo != null){
            inserted = signalRepo.insert(signalModel);
        }

        return inserted ? signalModel : null;
    }
}
