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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/credentials")
public class CredentialController {
    private final CredentialService credentialService;

    @PostMapping
    public String create(Authentication authentication,
                         @ModelAttribute("credential") Credential credential,
                         RedirectAttributes redir) {

        var userId = (int) authentication.getPrincipal();
        credentialService.create(userId, credential);
        redir.addFlashAttribute("signupSuccess", true);
        return "redirect:/result";
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
                       @PathVariable int credentialId, RedirectAttributes redir) {
        var userId = (int) authentication.getPrincipal();
        credential.setCredentialid(credentialId);
        credential.setUserid(userId);
        credentialService.update(credential);
        redir.addFlashAttribute("signupSuccess", true);
        return "redirect:/result";
    }

    @DeleteMapping("{credentialId}")
    public String delete(Authentication authentication,
                         @PathVariable int credentialId,
                         RedirectAttributes redir) {

        var userId = (int) authentication.getPrincipal();
        credentialService.delete(userId, credentialId);

        redir.addFlashAttribute("signupSuccess", true);
        return "redirect:/result";
    }
}
