package br.eti.arthurgregorio.minibudget.model.entities;

import java.io.Serializable;

public interface IPersistentEntity<T extends Serializable> {

    T getId();

    boolean isSaved();
}