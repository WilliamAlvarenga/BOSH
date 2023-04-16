package com.bosh.test;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.bosh.challenger.HookManagerApplication;
import com.bosh.challenger.DTO.HookCreateDto;
import com.bosh.challenger.DTO.HookUpdateDto;
import com.bosh.challenger.entity.Hook;
import com.bosh.challenger.entity.enumeration.Status;
import com.bosh.challenger.repository.HookRepository;

import reactor.core.publisher.Mono;

@SpringBootTest(classes = HookManagerApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

	

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private HookRepository hookRepository;

	@Test
	public void testSave() {

		HookCreateDto hookDto = new HookCreateDto("name", "Description");
		
		webTestClient.post().uri("/subscriptions")
		.contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .body(Mono.just(hookDto), HookCreateDto.class)
        .exchange()
        .expectStatus().isCreated()
        .expectBody()
        .consumeWith(System.out::println)
        .jsonPath("$.name").isEqualTo(hookDto.getName())
        .jsonPath("$.description").isEqualTo(hookDto.getDescription());
        
		
	}

	@Test
	public void testGetAll() {			
		
		hookRepository.save(Hook.builder().id(null)
				.description("description")
				.name("name")
				.status(Status.ACTIVE)
				.build()).block();
		
		hookRepository.save(Hook.builder().id(null)
				.description("description2")
				.name("name2")
				.status(Status.ACTIVE)
				.build()).block();
		
		 webTestClient.get().uri("/subscriptions")
         .accept(MediaType.APPLICATION_JSON)
         .exchange()
         .expectStatus().isOk()
         .expectBodyList(Hook.class)
         .consumeWith(System.out::println);
		
		
	}

	@Test
	public void testgetById() {

		
		Hook savedHook = hookRepository.save(Hook.builder().id(null)
				.description("description")
				.name("name")
				.status(Status.ACTIVE)
				.build()).block();
		
		
		hookRepository.save(Hook.builder().id(null)
				.description("description2")
				.name("name2")
				.status(Status.ACTIVE)
				.build()).block();
		
		 webTestClient.get().uri("/subscriptions/{id}", Collections.singletonMap("id", savedHook.getId()) )
         .accept(MediaType.APPLICATION_JSON)
         .exchange()
         .expectStatus().isOk()
         .expectBody()
         .consumeWith(System.out::println)
		 .jsonPath("$.name").isEqualTo(savedHook.getName())
	     .jsonPath("$.description").isEqualTo(savedHook.getDescription());
	}

	@Test
	public void testUpdate() {
		
		HookUpdateDto hookNewDto = new HookUpdateDto("newName", "NewDescription", "OFFLINE");
		
		
	Hook savedHook = hookRepository.save(Hook.builder().id(null)
				.description("description")
				.name("name")
				.status(Status.ACTIVE)
				.build()).block();
		
	  webTestClient.put().uri("/subscriptions/{id}", Collections.singletonMap("id", savedHook.getId()))
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON)
      .body(Mono.just(hookNewDto), HookCreateDto.class)
      .exchange()
      .expectStatus().isOk()
      .expectBody()
      .consumeWith(System.out::println)
      .jsonPath("$.name").isEqualTo(hookNewDto.getName())
      .jsonPath("$.description").isEqualTo(hookNewDto.getDescription())
	  .jsonPath("$.status").isEqualTo(hookNewDto.getStatus());
    
		

	}

	@Test
	public void testDelete() {

		
		Hook savedHook =	hookRepository.save(Hook.builder().id(null)
								.description("description")
								.name("name")
								.status(Status.ACTIVE)
								.build()).block();
		
		  webTestClient.delete().uri("/subscriptions/{id}", Collections.singletonMap("id", savedHook.getId()))
          .exchange()
          .expectStatus().isNoContent()
          .expectBody()
          .consumeWith(System.out::println);
		
		
	}

	
	
}
