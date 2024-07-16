package com.pharmaconnect.pharma.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.pharmaconnect.pharma.entity.Drugs;
import com.pharmaconnect.pharma.service.DrugServcie;
import com.pharmaconnect.pharma.service.StoreInfoService;
import com.pharmaconnect.pharma.service.UserService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping(path = "/store/drug")
@CrossOrigin(origins = "*")
public class DrugsController {

    @Autowired
    DrugServcie drugService;

    @Autowired
    StoreInfoService storeInfoService;

    @Autowired
    UserService userService;

    @PostMapping(path="/addNewStoreDrugPrice")
    public ResponseEntity<String> addNewDrug(@RequestBody(required = true) Map<String, String> requestMap){
        try {
            return drugService.addNewDrug(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Something went wrong" + "\"}",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping(path="/getAllDrugs")
    public List<Drugs> getAllDrugs(){
        return drugService.getAllDrugs();
    }

    @PostMapping(path="/updateStoreDrugPrice")
    public ResponseEntity<String> updateStoreDrugPrice(@RequestBody(required = true) Map<String, String> requestMap){
        
        try {
            return drugService.updateStoreDrugPrice(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<String>("{\"message\":\"" + "Something went wrong" + "\"}",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @DeleteMapping(path= "/deleteDrug/{drugId}/{storeId}")
    public ResponseEntity<String> deleteStoreDrugPriceId(@PathVariable int drugId, @PathVariable int storeId ){
        try{

            return drugService.deleteStoreDrugPriceId( drugId,  storeId);

       }catch(Exception ex){
               ex.printStackTrace();
       }
        return new ResponseEntity<String>("{\"message\":\"" + "Something went wrong" + "\"}",
               HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
