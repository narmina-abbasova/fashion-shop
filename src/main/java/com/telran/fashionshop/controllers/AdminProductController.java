package com.telran.fashionshop.controllers;

import com.telran.fashionshop.domain.entities.Product;
import com.telran.fashionshop.domain.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminProductController {

    private final ProductRepository productRepository;

    @Value("${upload-dir}")
    private String uploadDir;

    @GetMapping("/add-product")
    public String showAddProductForm() {
        return "product-creation";
    }

    @PostMapping("/add-product")
    public String addProduct(
            @RequestParam("name") final String name,
            @RequestParam("description") final String description,
            @RequestParam("size") final String size,
            @RequestParam("price") final BigDecimal price,
            @RequestParam("image") final MultipartFile imageFile) throws IOException {

        final String productId = UUID.randomUUID().toString();
        saveProduct(productId, name, description, size, price, imageFile);

        return "redirect:/";
    }

    @GetMapping("/edit-product/{productId}")
    public String showEditProductForm(@PathVariable final String productId, final Model model) {
        Product product = productRepository.findById(productId).orElseThrow();
        model.addAttribute("productId", productId);
        model.addAttribute("name", product.getName());
        model.addAttribute("description", product.getDescription());
        model.addAttribute("size", product.getSize());
        model.addAttribute("price", product.getPrice());
        return "product-modification";
    }

    @PostMapping("/edit-product/{productId}")
    public String editProduct(
            @PathVariable String productId,
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("size") String size,
            @RequestParam("price") BigDecimal price,
            @RequestParam("image") MultipartFile imageFile) throws IOException {

        final Product oldProduct = productRepository.findById(productId).orElseThrow();

        saveProduct(productId, name, description, size, price, imageFile);

        Files.deleteIfExists(Path.of(uploadDir, oldProduct.getImageUrl()));

        return "redirect:/";
    }

    private void saveProduct(final String productId,
                             final String name,
                             final String description,
                             final String size,
                             final BigDecimal price,
                             final MultipartFile imageFile) throws IOException {
        final String fileName = Path.of("images", productId + "-" + imageFile.getOriginalFilename()).toString();
        FileCopyUtils.copy(imageFile.getBytes(), new File(Path.of(uploadDir, fileName).toString()));

        final Product product = Product.builder()
                .id(productId)
                .name(name)
                .description(description)
                .size(size)
                .price(price)
                .imageUrl(fileName)
                .build();

        productRepository.save(product);
    }

    @PostMapping("remove-product")
    public String removeProduct(@RequestParam("productId") final String productId) throws IOException {
        final Product product = productRepository.findById(productId).orElseThrow();
        productRepository.deleteById(productId);
        Files.deleteIfExists(Path.of(uploadDir, product.getImageUrl()));
        return "redirect:/";
    }

}
