package io.github.kansasmyers.bank.rest;

import io.github.kansasmyers.bank.domain.model.Account;
import io.github.kansasmyers.bank.domain.model.Resume;
import io.github.kansasmyers.bank.domain.model.OperationType;
import io.github.kansasmyers.bank.domain.repository.AccountRepository;
import io.github.kansasmyers.bank.domain.repository.ResumeRepository;
import io.github.kansasmyers.bank.rest.dto.CreateOperationRequest;
import io.github.kansasmyers.bank.rest.dto.ResponseError;
import io.github.kansasmyers.bank.rest.dto.ReturnResumeRequest;
import io.quarkus.hibernate.orm.panache.PanacheQuery;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Validator;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/operations")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class OperationResource {

    @Inject
    AccountRepository accountRepository;

    @Inject
    ResumeRepository resumeRepository;

    @Inject
    Validator validator;

    @GET
    @Path("resume/{account_id}")
    public Response getResume(@PathParam("account_id") Long accountId) {
        Account account = accountRepository.findById(accountId);

        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        PanacheQuery<ReturnResumeRequest> resume = resumeRepository.find("account", account).project(ReturnResumeRequest.class);
        return Response.ok(resume.list()).build();
    }

    @POST
    @Path("{account_id}")
    @Transactional
    public Response executeOperation(@PathParam("account_id") Long accountId, CreateOperationRequest operationRequest) {
        var violations = validator.validate(operationRequest);
        if (!violations.isEmpty()) {
            return ResponseError.createFromValidation(violations).withStatusCode(ResponseError.UNPROCESSABLE_ENTITY_STATUS);
        }

        Account account = accountRepository.findById(accountId);

        if (account == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        var operationType = OperationType.valueOf(operationRequest.getType());
        var actualBalance = account.getBalance();

        switch (operationType) {
            case DEPOSIT:
                account.setBalance(actualBalance + operationRequest.getAmount());
                break;
            case WITHDRAW:
                if (actualBalance < operationRequest.getAmount()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ResponseError("Insuficient founds.", null)).build();
                }

                account.setBalance(actualBalance - operationRequest.getAmount());
                break;
        }

        Resume resume = new Resume();
        resume.setBalance(actualBalance);
        resume.setAmount(operationRequest.getAmount());
        resume.setAccount(account);
        resume.setOperationType(operationType);

        resumeRepository.persist(resume);

        return Response.noContent().build();
    }


}

