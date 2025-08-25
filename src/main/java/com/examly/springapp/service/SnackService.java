package com.examly.springapp.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.Snack;
import com.examly.springapp.repository.SnackRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SnackService {

    private final SnackRepository snackRepository;

    public Snack addSnack(Snack snack) {
        Integer numOfProducts = snack.getCategory().getNoOfProducts();
        snack.getCategory().setNoOfProducts(numOfProducts+1);
        return snackRepository.save(snack);
    }

    public Page<Snack> getAllSnacks(Pageable pageable) {
        return snackRepository.findAll(pageable);
    }

    public Page<Snack> getAllSnacksByCategory(Pageable pageable, String name) {
        return snackRepository.findAllSnacksByCategory(pageable, name);
    }

    public Snack updateSnack(Long id, Snack updatedSnack) {
        Snack snack = snackRepository.findById(id).orElseThrow();
        snack.setName(updatedSnack.getName());
        snack.setPrice(updatedSnack.getPrice());
        snack.setQuantity(updatedSnack.getQuantity());
        snack.setExpiryInMonths(updatedSnack.getExpiryInMonths());
        snack.setDescription(updatedSnack.getDescription());
        snack.setCategory(updatedSnack.getCategory());
        snack.setVendor(updatedSnack.getVendor());
        return snackRepository.save(snack);
    }

    public void deleteSnack(Long id) {
        Optional<Snack> optionalSnack = snackRepository.findById(id);

        if (optionalSnack.isPresent()) {
            Snack snack = optionalSnack.get();
            Integer numOfProducts = snack.getCategory().getNoOfProducts();
            snack.getCategory().setNoOfProducts(numOfProducts - 1);

            snackRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("Snack not found with id: " + id);
        }
    }

}
