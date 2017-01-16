package org.kiluyaqing.choice.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.kiluyaqing.choice.service.ChoiceUserService;
import org.kiluyaqing.choice.web.rest.util.HeaderUtil;
import org.kiluyaqing.choice.web.rest.util.PaginationUtil;
import org.kiluyaqing.choice.service.dto.ChoiceUserDTO;
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
 * REST controller for managing ChoiceUser.
 */
@RestController
@RequestMapping("/api")
public class ChoiceUserResource {

    private final Logger log = LoggerFactory.getLogger(ChoiceUserResource.class);
        
    @Inject
    private ChoiceUserService choiceUserService;

    /**
     * POST  /choice-users : Create a new choiceUser.
     *
     * @param choiceUserDTO the choiceUserDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new choiceUserDTO, or with status 400 (Bad Request) if the choiceUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/choice-users")
    @Timed
    public ResponseEntity<ChoiceUserDTO> createChoiceUser(@Valid @RequestBody ChoiceUserDTO choiceUserDTO) throws URISyntaxException {
        log.debug("REST request to save ChoiceUser : {}", choiceUserDTO);
        if (choiceUserDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("choiceUser", "idexists", "A new choiceUser cannot already have an ID")).body(null);
        }
        ChoiceUserDTO result = choiceUserService.save(choiceUserDTO);
        return ResponseEntity.created(new URI("/api/choice-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("choiceUser", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /choice-users : Updates an existing choiceUser.
     *
     * @param choiceUserDTO the choiceUserDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated choiceUserDTO,
     * or with status 400 (Bad Request) if the choiceUserDTO is not valid,
     * or with status 500 (Internal Server Error) if the choiceUserDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/choice-users")
    @Timed
    public ResponseEntity<ChoiceUserDTO> updateChoiceUser(@Valid @RequestBody ChoiceUserDTO choiceUserDTO) throws URISyntaxException {
        log.debug("REST request to update ChoiceUser : {}", choiceUserDTO);
        if (choiceUserDTO.getId() == null) {
            return createChoiceUser(choiceUserDTO);
        }
        ChoiceUserDTO result = choiceUserService.save(choiceUserDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("choiceUser", choiceUserDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /choice-users : get all the choiceUsers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of choiceUsers in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/choice-users")
    @Timed
    public ResponseEntity<List<ChoiceUserDTO>> getAllChoiceUsers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of ChoiceUsers");
        Page<ChoiceUserDTO> page = choiceUserService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/choice-users");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /choice-users/:id : get the "id" choiceUser.
     *
     * @param id the id of the choiceUserDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the choiceUserDTO, or with status 404 (Not Found)
     */
    @GetMapping("/choice-users/{id}")
    @Timed
    public ResponseEntity<ChoiceUserDTO> getChoiceUser(@PathVariable Long id) {
        log.debug("REST request to get ChoiceUser : {}", id);
        ChoiceUserDTO choiceUserDTO = choiceUserService.findOne(id);
        return Optional.ofNullable(choiceUserDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /choice-users/:id : delete the "id" choiceUser.
     *
     * @param id the id of the choiceUserDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/choice-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteChoiceUser(@PathVariable Long id) {
        log.debug("REST request to delete ChoiceUser : {}", id);
        choiceUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("choiceUser", id.toString())).build();
    }

}
