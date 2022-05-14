package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.contract.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;

    @PostMapping
    public String create(Authentication authentication, @ModelAttribute("credential") Credential credential) {
        var userId = (int) authentication.getPrincipal();
        credentialService.create(userId, credential);
        return "redirect:/home";
    }

    @GetMapping("{credentialId}")
    public String getById(Model model, Authentication authentication,
                          @PathVariable int credentialId) {
        var userId = (int) authentication.getPrincipal();
        model.addAttribute("credential", credentialService.getByIds(userId, credentialId));
        return "edit/credential";
    }

    @PutMapping("{credentialId}")
    public String edit(@ModelAttribute("credential") Credential credential, Authentication authentication,
                       @PathVariable int credentialId) {
        var userId = (int) authentication.getPrincipal();
        credential.setCredentialid(credentialId);
        credential.setUserid(userId);
        credentialService.update(credential);
        return "redirect:/home";
    }

    @DeleteMapping("{credentialId}")
    public String delete(Authentication authentication, @PathVariable int credentialId) {
        var userId = (int) authentication.getPrincipal();
        credentialService.delete(userId, credentialId);
        return "redirect:/home";
    }
}
