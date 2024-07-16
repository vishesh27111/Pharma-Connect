package com.pharmaconnect.pharma.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pharmaconnect.pharma.dao.DrugsDao;
import com.pharmaconnect.pharma.dao.StoreDao;
import com.pharmaconnect.pharma.dao.StoreDrugPriceDao;
import com.pharmaconnect.pharma.entity.Drugs;
import com.pharmaconnect.pharma.entity.Store;
import com.pharmaconnect.pharma.entity.StoreDrugPrice;
import com.pharmaconnect.pharma.model.DrugDetailsForSpecificStore;
import com.pharmaconnect.pharma.model.StoreDetailsForSpecificDrug;

@Service
public class StoreInfoService {

    protected Logger log = LoggerFactory.getLogger(StoreInfoService.class);

    @Autowired
    StoreDao storeDao;

    @Autowired
    StoreDrugPriceDao storeDrugPriceDao;

    @Autowired
    DrugsDao drugsDao;

    public Store findByStoreId(Long storeId) {
        log.info("------findByStoreId called------");
        try {
            return storeDao.findByStoreId(storeId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<DrugDetailsForSpecificStore> findDrugDetailsForSpecificStore(Long storeId) {
        log.info("-------findDrugDetailsForSpecificStore called-----");
        List<DrugDetailsForSpecificStore> drugDetailsForSpecificStore = new ArrayList<DrugDetailsForSpecificStore>();
        List<StoreDrugPrice> storeDrugPrices = storeDrugPriceDao.findAll();

        for (StoreDrugPrice storeDrugPrice : storeDrugPrices) {
            if (storeDrugPrice.getStoreDrugPriceId().getStoreId().longValue() == storeId) {
                // Access the StoreDrug entity and perform operations
                Drugs drug = new Drugs();
                Integer drugId = storeDrugPrice.getStoreDrugPriceId().getDrugId();
                Integer quantity = storeDrugPrice.getQuantity();
                drug = drugsDao.findByDrugId(drugId);
                String drugName=drug.getDrug_name();
                String code=drug.getCode();
                Float unitPrice=storeDrugPrice.getUnit_price();
                String companyName=drug.getCompany_name();
                Date produceDate=drug.getProduction_date();
                Date expireDate=drug.getExpiration_date();
                boolean prescription=drug.getPrescription();
                String description=drug.getDescription();
                DrugDetailsForSpecificStore details=new DrugDetailsForSpecificStore();
                details.setCode(code);
                details.setCompany_name(companyName);
                details.setDescription(description);
                details.setDrugId(drugId);
                details.setDrug_name(drugName);
                details.setExpiration_date(expireDate);
                details.setPrescription(prescription);
                details.setProduction_date(produceDate);
                details.setQuantity(quantity);
                details.setUnit_price(unitPrice);
                drugDetailsForSpecificStore.add(details);
            }
        }

        return drugDetailsForSpecificStore;
    }

    public List<StoreDetailsForSpecificDrug> findStoreDetailsForSpecificDrug(Long drugId) {

        log.info("---------findStoreDetailsForSpecificDrug-------");
        List<StoreDrugPrice> storeDrugPrices = storeDrugPriceDao.findAll();
        List<StoreDetailsForSpecificDrug> storeDetailsForSpecificDrug = new ArrayList<>();
        for (StoreDrugPrice storeDrugPrice : storeDrugPrices) {
            if (storeDrugPrice.getStoreDrugPriceId().getDrugId().longValue() == drugId) {
                Store store = new Store();
                // Access the StoreDrug entity and perform operations
                Integer storeId = storeDrugPrice.getStoreDrugPriceId().getStoreId();
                Float unit_price = storeDrugPrice.getUnit_price();
                Integer quantity = storeDrugPrice.getQuantity();
                store = storeDao.findByStoreId(storeId.longValue());
                String storeName=store.getStore_name();
                String email=store.getEmail();
                String regNumber=store.getRegistration_number();
                String managerName=store.getManager_name();
                String phone=store.getPhone();
                String address=store.getAddress();
                String zipCode=store.getZipcode();
                StoreDetailsForSpecificDrug details=new StoreDetailsForSpecificDrug();
                details.setAddress(address);
                details.setDrugId(drugId);
                details.setEmail(email);
                details.setManager_name(managerName);
                details.setPhone(phone);
                details.setPhone(phone);
                details.setQuantity(quantity);
                details.setRegistration_number(regNumber);
                details.setStoreId(storeId);
                details.setStore_name(storeName);
                details.setUnit_price(unit_price);
                details.setZipcode(zipCode);
                storeDetailsForSpecificDrug.add(details);

            }
        }
        return storeDetailsForSpecificDrug;
    }

}