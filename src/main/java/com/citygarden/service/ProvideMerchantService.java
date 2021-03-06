package com.citygarden.service;

import com.citygarden.domain.ProvideMerchant;
import com.citygarden.repository.ProvideMerchantRepository;
import com.citygarden.web.rest.dto.DishDTO;
import com.citygarden.web.rest.dto.ProvideMerchantDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * Service Implementation for managing ProvideMerchant.
 */
@Service
public class ProvideMerchantService {

    private final Logger log = LoggerFactory.getLogger(ProvideMerchantService.class);

    @Inject
    private ProvideMerchantRepository provideMerchantRepository;

    @Inject
    private DishPhotoUtilService dishPhotoUtilService;

    @Inject
    private ProvideMerchantPhotoService provideMerchantPhotoService;
    /**
     * Save a provideMerchant.
     * @return the persisted entity
     */
    public ProvideMerchantDTO save(ProvideMerchantDTO provideMerchantDTO) {
        log.debug("Request to save ProvideMerchant : {}", provideMerchantDTO);
       // ProvideMerchant provideMerchant = provideMerchantMapper.provideMerchantDTOToProvideMerchant(provideMerchantDTO);
       //   provideMerchant = provideMerchantRepository.save(provideMerchant);
       //  ProvideMerchantDTO result = provideMerchantMapper.provideMerchantToProvideMerchantDTO(provideMerchant);
        return null;
    }

    /**
     *  get all the provideMerchants.
     *  @return the list of entities
     */
    public List<ProvideMerchantDTO> findAll(){
        log.debug("Request to get all ProvideMerchants");
        List<ProvideMerchant> provideMerchants =provideMerchantRepository.findAllByOrderByLastModifiedDateDesc();
        List<ProvideMerchantDTO> provideMerchantDTOs = new ArrayList<>(0);
        provideMerchants.forEach(x -> {
            ProvideMerchantDTO provideMerchantDTO = new ProvideMerchantDTO();
            provideMerchantDTO.setId(x.getId());
            provideMerchantDTO.setName(x.getName());
            provideMerchantDTO.setChineseName(x.getChineseName());
            provideMerchantDTO.setDescription(x.getDescription());
            List<DishDTO> dishDTOs = new ArrayList<>();
            x.getDishs().forEach(y -> {
                DishDTO z = new DishDTO();
                z.setId(y.getId());
                z.setName(y.getName());
                z.setChineseName(y.getChineseName());
                z.setOriginalPrice(y.getOriginalPrice());
                z.setDiscountPrice(y.getDiscountPrice());
                z.setDescription(y.getDescription());
                try {
                    z.setDishPhoto(dishPhotoUtilService.getDishPhoto(y.getName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                dishDTOs.add(z);
            });
            provideMerchantDTO.setDishs(dishDTOs);
            try {
                provideMerchantDTO.setProvidePhoto(provideMerchantPhotoService.getProvidePhoto(x.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            provideMerchantDTOs.add(provideMerchantDTO);
        });

        return provideMerchantDTOs;
    }

    /**
     *  get one provideMerchant by id.
     *  @return the entity
     */
    public ProvideMerchantDTO findOne(String id) {
        log.debug("Request to get ProvideMerchant : {}", id);
        ProvideMerchant x = provideMerchantRepository.findOne(id);
        ProvideMerchantDTO provideMerchantDTO = new ProvideMerchantDTO();
        provideMerchantDTO.setId(x.getId());
        provideMerchantDTO.setName(x.getName());
        provideMerchantDTO.setChineseName(x.getChineseName());
        provideMerchantDTO.setDescription(x.getDescription());
        List<DishDTO> dishDTOs = new ArrayList<>();
        x.getDishs().forEach(y -> {
            DishDTO z = new DishDTO();
            z.setId(y.getId());
            z.setName(y.getName());
            z.setChineseName(y.getChineseName());
            z.setOriginalPrice(y.getOriginalPrice());
            z.setDiscountPrice(y.getDiscountPrice());
            z.setDescription(y.getDescription());
            try {
                z.setDishPhoto(dishPhotoUtilService.getDishPhoto(y.getName()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            dishDTOs.add(z);
        });
        provideMerchantDTO.setDishs(dishDTOs);
        try {
            provideMerchantDTO.setProvidePhoto(provideMerchantPhotoService.getProvidePhoto(x.getName()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return provideMerchantDTO;
    }

    /**
     *  delete the  provideMerchant by id.
     */
    public void delete(String id) {
        log.debug("Request to delete ProvideMerchant : {}", id);
        provideMerchantRepository.delete(id);
    }
}
