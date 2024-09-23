package com.comestic.shop.controller;

import com.comestic.shop.model.Branch;
import com.comestic.shop.service.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/branches")
public class BranchController {

    @Autowired
    private BranchService branchService;

    // Display list of branches
    @GetMapping
    public String listBranches(Model model) {
        List<Branch> branches = branchService.getAllBranches();
        model.addAttribute("branches", branches);
        return "branch/list"; // Thymeleaf template: list.html
    }

    // Show form to create a new branch
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("branch", new Branch());
        return "branch/create"; // Thymeleaf template: create.html
    }

    // Handle the form submission to save a new branch
    @PostMapping("/save")
    public String saveBranch(@Valid @ModelAttribute("branch") Branch branch, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "branch/create";
        }
        branchService.addBranch(branch);
        return "redirect:/branches";
    }

    // Show form to update an existing branch
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        System.out.println();
        Branch branch = branchService.getBranchById(id).orElse(null);
        if (branch == null) {
            return "redirect:/branches";
        }
        model.addAttribute("branch", branch);
        return "branch/edit"; // Thymeleaf template: edit.html
    }

    // Handle the form submission to update a branch
    @PostMapping("/update/{id}")
    public String updateBranch(@PathVariable("id") Long id, @Valid @ModelAttribute("branch") Branch branch, BindingResult result, Model model) {

        if (result.hasErrors()) {
            return "branch/edit";
        }
        branchService.updateBranch(id, branch);
        return "redirect:/branches";
    }

    // Delete a branch
    @GetMapping("/delete/{id}")
    public String deleteBranch(@PathVariable("id") Long id) {
        branchService.deleteBranch(id);
        return "redirect:/branches";
    }
}