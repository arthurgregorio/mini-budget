package br.eti.arthurgregorio.minibudget.model.services;

import br.eti.arthurgregorio.minibudget.model.entities.Classification;
import br.eti.arthurgregorio.minibudget.model.repositories.ClassificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ClassificationRegistrationService {

    private final ClassificationRepository classificationRepository;

    public ClassificationRegistrationService(ClassificationRepository classificationRepository) {
        this.classificationRepository = classificationRepository;
    }

    @Transactional
    public Classification create(Classification Classification) {
        return this.classificationRepository.save(Classification);
    }

    @Transactional
    public Classification update(Classification Classification) {
        return this.classificationRepository.save(Classification);
    }

    @Transactional
    public void delete(Classification Classification) {
        this.classificationRepository.delete(Classification);
    }
}
