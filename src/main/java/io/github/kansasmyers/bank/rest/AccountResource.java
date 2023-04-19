package io.github.kansasmyers.bank.rest;

import io.github.kansasmyers.bank.domain.model.Account;
import io.github.kansasmyers.bank.domain.repository.AccountRepository;
import io.github.kansasmyers.bank.domain.repository.ResumeRepository;
import io.github.kansasmyers.bank.rest.dto.CreateAccountRequest;
import io.github.kansasmyers.bank.rest.dto.ResponseError;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {

    public static final int INITIAL_AGENCY = 1;

    public static final double INITIAL_BALANCE = 0.0;

    @Inject
    AccountRepository accountRepository;

    @Inject
    ResumeRepository resumeRepository;

    @Inject
    Validator validator;

    @POST
    @Transactional
    public Response createAccount(CreateAccountRequest accountRequest) {
        var violations = validator.validate(accountRequest);
        if (!violations.isEmpty()) {
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        Account account = new Account();
        account.setName(accountRequest.getName());
        account.setIdAgency(INITIAL_AGENCY);
        account.setBalance(INITIAL_BALANCE);

        accountRepository.persist(account);

        return Response.status(Response.Status.CREATED).build();
    }

    @GET
    public Response listAllAccounts() {
        PanacheQuery<Account> query = accountRepository.findAll();
        return Response.ok(query.list()).build();
    }

    @GET
    @Path("{id}")
    public Response queryAccount(@PathParam("id") Long id) {
        Account account = accountRepository.findById(id);

        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(account).build();
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response deleteAccount(@PathParam("id") Long id) {
        Account account = accountRepository.findById(id);

        if (account != null) {
            resumeRepository.delete("account", account);
            accountRepository.delete(account);
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Response updateAccount(@PathParam("id") Long id, CreateAccountRequest accountRequest) {
        var violations = validator.validate(accountRequest);
        if (!violations.isEmpty()) {
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        Account account = accountRepository.findById(id);

        if (account != null) {
            account.setName(accountRequest.getName());
            return Response.noContent().build();
        }

        return Response.status(Response.Status.NOT_FOUND).build();
    }


}

