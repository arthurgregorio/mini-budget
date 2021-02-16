package br.eti.arthurgregorio.minibudget.application;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import static org.springframework.http.HttpStatus.PARTIAL_CONTENT;
import static org.springframework.http.ResponseEntity.noContent;
import static org.springframework.http.ResponseEntity.status;

public class ControllerUtilities {

    public static <T> ResponseEntity<Page<T>> checkIfOkOrNoContent(Page<T> pages) {
        return pages.isEmpty() ? noContent().build() : status(PARTIAL_CONTENT).body(pages);
    }
}
