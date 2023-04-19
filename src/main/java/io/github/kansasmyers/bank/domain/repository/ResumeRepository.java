package io.github.kansasmyers.bank.domain.repository;

import io.github.kansasmyers.bank.domain.model.Resume;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ResumeRepository implements PanacheRepository<Resume> {
}
