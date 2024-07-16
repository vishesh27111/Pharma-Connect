package com.pharmaconnect.pharma.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.pharmaconnect.pharma.dao.DrugsDao;
import com.pharmaconnect.pharma.dao.StoreDrugPriceDao;
import com.pharmaconnect.pharma.entity.Drugs;
import com.pharmaconnect.pharma.entity.StoreDrug;
import com.pharmaconnect.pharma.entity.StoreDrugPrice;

@Service
public class DrugServcie {

    protected Logger log = LoggerFactory.getLogger(DrugServcie.class);

    @Autowired
    DrugsDao drugsDao;

    @Autowired
    StoreDrugPriceDao storeDrugPriceDao;

    @Autowired
    StoreDrug storeDrug;

    public ResponseEntity<String> addNewDrug(Map<String, String> requestMap) {
        log.info("-------addNewDrug called--------");
        try {
            if (validateNewDrug(requestMap)) {
                storeDrugPriceDao.save(getStoreDrugPriceFromMap(requestMap));
                return ResponseEntity.ok("{\"message\":\"Drug Details Added\"}");
            } else {
                return ResponseEntity.badRequest().body("{\"message\":\"Invalid Data.\"}");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        String message = "{\"message\":\"Something went wrong in Drug Service Implementation.\"}";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }

    public boolean validateNewDrug(Map<String, String> requestMap) {
        log.info("-------validateNewDrug called--------");
        boolean hasCode = requestMap.containsKey("code");
        boolean hasStoreId = requestMap.containsKey("store_id");
        boolean hasUnitPrice = requestMap.containsKey("unit_price");
        boolean hasDrugId = requestMap.containsKey("drug_id");
        boolean hasQuantity = requestMap.containsKey("quantity");
        return hasCode && hasStoreId && hasUnitPrice && hasDrugId && hasQuantity;
    }

    StoreDrugPrice getStoreDrugPriceFromMap(Map<String, String> requestMap) {
        log.info("-------getStoreDrugPriceFromMap called--------");
        Integer drugId = Integer.parseInt(requestMap.get("drug_id"));
        Integer storeId = Integer.parseInt(requestMap.get("store_id"));
        StoreDrugPrice storeDrugPrice = new StoreDrugPrice();
        StoreDrug storeDrug = new StoreDrug(drugId, storeId);
        storeDrugPrice.setStoreDrugPriceId(storeDrug);
        storeDrugPrice.setUnit_price(Float.parseFloat(requestMap.get("unit_price")));
        storeDrugPrice.setCode(requestMap.get("code"));
        storeDrugPrice.setQuantity(Integer.parseInt(requestMap.get("quantity")));
        return storeDrugPrice;
    }

    public List<Drugs> getAllDrugs() {
        log.info("-------getStoreDrugPriceFromMap called--------");

        return drugsDao.findAll();
    }

    public ResponseEntity<String> updateStoreDrugPrice(Map<String, String> requestMap) {
        log.info("-------updateStoreDrugPrice called--------");

        if (validateNewDrug(requestMap)) {

            Integer drugId = Integer.parseInt(requestMap.get("drug_id"));
            Integer storeId = Integer.parseInt(requestMap.get("store_id"));
            StoreDrug storeDrug = new StoreDrug(drugId, storeId);
            Optional<StoreDrugPrice> optional = storeDrugPriceDao.findByStoreDrugPriceId(storeDrug);
            if (optional.isPresent()) {
                storeDrugPriceDao.save(getStoreDrugPriceFromMap(requestMap));
                return ResponseEntity.ok("{\"message\":\"Store Drug Price Details Updated\"}");
            } else {
                String message = "{\"message\":\"Not able to found the drug in the database.\"}";
                return ResponseEntity.badRequest()
                        .body(message);
            }
        } else {
            return ResponseEntity.badRequest().body("{\"message\":\"Invalid Data.\"}");
        }
    }

    public ResponseEntity<String> deleteStoreDrugPriceId(int drugId, int storeId) {
        log.info("-------deleteStoreDrugPriceId called--------");
        try {
            if (drugId >= 0 && storeId >= 0) {
                storeDrugPriceDao.deleteById(new StoreDrug(drugId, storeId));
                return ResponseEntity.ok("{\"message\":\"Store Drug Price Details Deleted\"}");
            } else {
                return ResponseEntity.badRequest()
                        .body("{\"message\":\"Invalid Data store or drug ID\"}");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        String message = "{\"message\":\"Something went wrong in Drug Service Implementation.\"}";
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(message);
    }

}
