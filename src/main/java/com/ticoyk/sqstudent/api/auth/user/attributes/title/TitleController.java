package com.ticoyk.sqstudent.api.auth.user.attributes.title;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/titles")
@PreAuthorize("hasAuthority('admin')")
public class TitleController {

    private final TitleService titleService;

    public TitleController(TitleService titleService) {
        this.titleService = titleService;
    }

    @GetMapping
    public ResponseEntity<List<Title>> getTitles() {
        return ResponseEntity.ok().body(titleService.getTitles());
    }

    @PostMapping
    public ResponseEntity<Title> createTitle(@RequestBody String name) {
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/titles").toUriString());
        return ResponseEntity.created(uri).body(titleService.createTitle(name));
    }

    @PutMapping("/{identifier}")
    ResponseEntity<Title> changeTitleName(@PathVariable String identifier, @RequestBody String name) {
        return ResponseEntity.ok(titleService.updateTitleName(identifier, name));
    }

}
