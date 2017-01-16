package org.kiluyaqing.choice.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kiluyaqing.choice.service.InvestigationService;
import org.kiluyaqing.choice.web.rest.util.HeaderUtil;
import org.kiluyaqing.choice.web.rest.util.PaginationUtil;
import org.kiluyaqing.choice.service.dto.InvestigationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing Investigation.
 */
@RestController
@RequestMapping("/api")
public class InvestigationResource {

    private final Logger log = LoggerFactory.getLogger(InvestigationResource.class);
        
    @Inject
    private InvestigationService investigationService;

    /**
     * POST  /investigations : Create a new investigation.
     *
     * @param investigationDTO the investigationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new investigationDTO, or with status 400 (Bad Request) if the investigation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/investigations")
    @Timed
    public ResponseEntity<InvestigationDTO> createInvestigation(@Valid @RequestBody InvestigationDTO investigationDTO) throws URISyntaxException {
        log.debug("REST request to save Investigation : {}", investigationDTO);
        if (investigationDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("investigation", "idexists", "A new investigation cannot already have an ID")).body(null);
        }
        InvestigationDTO result = investigationService.save(investigationDTO);
        return ResponseEntity.created(new URI("/api/investigations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("investigation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /investigations : Updates an existing investigation.
     *
     * @param investigationDTO the investigationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated investigationDTO,
     * or with status 400 (Bad Request) if the investigationDTO is not valid,
     * or with status 500 (Internal Server Error) if the investigationDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/investigations")
    @Timed
    public ResponseEntity<InvestigationDTO> updateInvestigation(@Valid @RequestBody InvestigationDTO investigationDTO) throws URISyntaxException {
        log.debug("REST request to update Investigation : {}", investigationDTO);
        if (investigationDTO.getId() == null) {
            return createInvestigation(investigationDTO);
        }
        InvestigationDTO result = investigationService.save(investigationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("investigation", investigationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /investigations : get all the investigations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of investigations in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/investigations")
    @Timed
    public ResponseEntity<List<InvestigationDTO>> getAllInvestigations(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Investigations");
        Page<InvestigationDTO> page = investigationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/investigations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /investigations/:id : get the "id" investigation.
     *
     * @param id the id of the investigationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the investigationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/investigations/{id}")
    @Timed
    public ResponseEntity<InvestigationDTO> getInvestigation(@PathVariable Long id) {
        log.debug("REST request to get Investigation : {}", id);
        InvestigationDTO investigationDTO = investigationService.findOne(id);
        return Optional.ofNullable(investigationDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /investigations/:id : delete the "id" investigation.
     *
     * @param id the id of the investigationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/investigations/{id}")
    @Timed
    public ResponseEntity<Void> deleteInvestigation(@PathVariable Long id) {
        log.debug("REST request to delete Investigation : {}", id);
        investigationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("investigation", id.toString())).build();
    }

}
