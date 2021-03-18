package br.eti.arthurgregorio.minibudget.model.services;

import br.eti.arthurgregorio.minibudget.model.entities.Contact;
import br.eti.arthurgregorio.minibudget.model.repositories.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ContactRegistrationService {

    private final ContactRepository contactRepository;

    public ContactRegistrationService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Transactional
    public Contact save(Contact Contact) {
        return this.contactRepository.save(Contact);
    }

    @Transactional
    public Contact update(Contact Contact) {
        return this.contactRepository.save(Contact);
    }

    @Transactional
    public void delete(Contact Contact) {
        this.contactRepository.delete(Contact);
    }
}
