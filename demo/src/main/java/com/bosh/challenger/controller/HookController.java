package com.bosh.challenger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bosh.challenger.DTO.HookCreateDto;
import com.bosh.challenger.DTO.HookUpdateDto;
import com.bosh.challenger.entity.Hook;
import com.bosh.challenger.service.HookService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("subscriptions")
public class HookController {

	@Autowired
	private HookService hookService;

	@GetMapping
	public Flux<Hook> getAllHook() {
		return hookService.getAllHook();
	}
	
	@GetMapping("{id}")
	public Mono<Hook> getHookById(@PathVariable("id") Long id) {
		return hookService.getHookById(id);
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Mono<Hook> saveHook(@RequestBody HookCreateDto hook) {

		return hookService.saveHook(hook);
	}

	@PatchMapping("{id}")
	public Mono<Hook> updateHook(@PathVariable("id") Long id, @RequestBody HookUpdateDto hook) {
		try {
			return hookService.updateHook(id, hook);
			
		} catch (Exception e) {
			return Mono.error(e);
		}
		
	}

	@PutMapping("{id}")
	public Mono<Hook> UpsateHookAll(@PathVariable("id") Long id, @RequestBody HookUpdateDto hook) {
		return hookService.updateAllHook(id, hook);
	}

	@DeleteMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public Mono<Void> delete(@PathVariable("id") Long id) {
		return hookService.deleteHook(id);
	}
}
