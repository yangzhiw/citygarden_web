package com.citygarden.service;

import com.citygarden.domain.ProvideMerchant;
import com.citygarden.repository.ProvideMerchantRepository;
import com.citygarden.web.rest.dto.ProvideMerchantDTO;
import com.citygarden.web.rest.mapper.ProvideMerchantMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing ProvideMerchant.
 */
@Service
public class ProvideMerchantService {

    private final Logger log = LoggerFactory.getLogger(ProvideMerchantService.class);

    @Inject
    private ProvideMerchantRepository provideMerchantRepository;


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
    public List<ProvideMerchantDTO> findAll() {
        log.debug("Request to get all ProvideMerchants");
//        List<ProvideMerchantDTO> result = provideMerchantRepository.findAll().stream()
//            .map(provideMerchantMapper::provideMerchantToProvideMerchantDTO)
//            .collect(Collectors.toCollection(LinkedList::new));
        return null;
    }

    /**
     *  get one provideMerchant by id.
     *  @return the entity
     */
    public ProvideMerchantDTO findOne(String id) {
        log.debug("Request to get ProvideMerchant : {}", id);
        ProvideMerchant provideMerchant = provideMerchantRepository.findOne(id);
    //    ProvideMerchantDTO provideMerchantDTO = provideMerchantMapper.provideMerchantToProvideMerchantDTO(provideMerchant);
        return null;
    }

    /**
     *  delete the  provideMerchant by id.
     */
    public void delete(String id) {
        log.debug("Request to delete ProvideMerchant : {}", id);
        provideMerchantRepository.delete(id);
    }
}
