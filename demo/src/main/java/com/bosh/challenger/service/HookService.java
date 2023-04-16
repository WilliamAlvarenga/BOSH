package com.bosh.challenger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bosh.challenger.DTO.HookCreateDto;
import com.bosh.challenger.DTO.HookUpdateDto;
import com.bosh.challenger.entity.Hook;
import com.bosh.challenger.entity.enumeration.Status;
import com.bosh.challenger.exceptions.InvalidValueException;
import com.bosh.challenger.repository.HookRepository;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class HookService {

	@Autowired
	private HookRepository hookRepository;

	public Mono<Hook> saveHook(HookCreateDto hook) {

		Hook hook2 = Hook.builder().id(null).description(hook.getDescription()).name(hook.getName())
				.status(Status.ACTIVE).build();

		return hookRepository.save(hook2);
	}

	public Flux<Hook> getAllHook() {
		return hookRepository.findAll();
	}

	public Mono<Hook> getHookById(Long id) {
		return hookRepository.findById(id);		
	}

	public Mono<Hook> updateHook(Long id, HookUpdateDto hookToUpdate) {

		try {

			Mono<Hook> hook = getHookById(id);		

			return hook.flatMap((existingHook) -> {

				if (StringUtils.isNotEmpty(hookToUpdate.getDescription())) {
					existingHook.setDescription(hookToUpdate.getDescription());
				}

				if (StringUtils.isNotEmpty(hookToUpdate.getName())) {
					existingHook.setName(hookToUpdate.getName());
				}

				if (hookToUpdate.getStatus() != null) {

					Status status = Status.findStatus(hookToUpdate.getStatus())
							.orElseThrow(() -> new InvalidValueException("Cannot find a Status !"));
					existingHook.setStatus(status);
				}

				return hookRepository.save(existingHook);

			});

		} catch (Exception e) {
			log.error(e.getMessage());

		}
		return Mono.empty();

	}

	public Mono<Hook> updateAllHook(Long id, HookUpdateDto hookToUpdate) {

		Mono<Hook> hook = getHookById(id);

		return hook.flatMap((existingHook) -> {

			existingHook.setDescription(hookToUpdate.getDescription());
			existingHook.setName(hookToUpdate.getName());

			Status status = Status.findStatus(hookToUpdate.getStatus())
					.orElseThrow(() -> new RuntimeException("Cannot find a Status !"));
			existingHook.setStatus(status);

			return hookRepository.save(existingHook);

		});

	}

	public Mono<Void> deleteHook(Long id) {
		return hookRepository.deleteById(id);
	}

}
