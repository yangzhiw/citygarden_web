package com.citygarden.service;

import com.citygarden.domain.DeliveryAddress;
import com.citygarden.repository.DeliveryAddressRepository;
import com.citygarden.security.SecurityUtils;
import com.citygarden.web.rest.dto.DeliveryAddressDTO;
import com.citygarden.web.rest.util.CloudxEnums;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by Administrator on 2016/2/24 0024.
 */

@Service
public class DeliveryAddressService {

    @Inject
    private DeliveryAddressRepository deliveryAddressRepository;

    public DeliveryAddress save(DeliveryAddressDTO deliveryAddress) {
        System.err.println(deliveryAddress.getIsDefault());
        String defaults = CloudxEnums.DeliveryAddressEnum.DEFAULTADDRESS;
        DeliveryAddress deliveryAddress_new = new DeliveryAddress();
        if(!StringUtils.isBlank(deliveryAddress.getAddress())){
            deliveryAddress_new.setAddress(deliveryAddress.getAddress());
        }
        String username = SecurityUtils.getCurrentUserLogin();
        deliveryAddress_new.setUsername(username);
        if(StringUtils.isBlank(deliveryAddress.getIsDefault()) || deliveryAddress.getIsDefault().equals("NO")){
            deliveryAddress_new.setIsDefault(CloudxEnums.DeliveryAddressEnum.UNDEFAULTADDRESS);
        }
        if(!StringUtils.isBlank(deliveryAddress.getIsDefault()) && deliveryAddress.getIsDefault().equals("YES")){
            DeliveryAddress deliveryAddress1 = null;
            deliveryAddress1 = deliveryAddressRepository.findByIsDefaultAndUsername(defaults,username);

            //判断之前有没有添加默认地址
            if(deliveryAddress1 == null){
                deliveryAddress_new.setIsDefault(CloudxEnums.DeliveryAddressEnum.DEFAULTADDRESS);
            }else{
                deliveryAddress1.setIsDefault(CloudxEnums.DeliveryAddressEnum.UNDEFAULTADDRESS);
                deliveryAddressRepository.save(deliveryAddress1);
                deliveryAddress_new.setIsDefault(CloudxEnums.DeliveryAddressEnum.DEFAULTADDRESS);
            }
        }

        return  deliveryAddressRepository.save(deliveryAddress_new);

    }
}
