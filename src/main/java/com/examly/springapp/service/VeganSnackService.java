package com.examly.springapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.examly.springapp.model.VeganSnack;
import com.examly.springapp.repository.VeganSnackRepo;

@Service
public class VeganSnackService {
    @Autowired
    private VeganSnackRepo veganSnackRepo;

    public VeganSnack createSnack(VeganSnack veganSnack){
        return veganSnackRepo.save(veganSnack);
    }

    public List<VeganSnack> getAllSnacks() {
        return veganSnackRepo.findAll();
    }
}
