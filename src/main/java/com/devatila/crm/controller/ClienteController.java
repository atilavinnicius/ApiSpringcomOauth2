package com.devatila.crm.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.devatila.crm.model.Cliente;
import com.devatila.crm.repository.ClienteRepository;

@RestController
@RequestMapping("/")
public class ClienteController {
	
	@Autowired
	private ClienteRepository cRepository;
	
	@GetMapping
	public String index() {
		return "Página Inicial";
	}
	@GetMapping("/clientes")
	public List<Cliente> listar() {
		return cRepository.findAll();
	}
	
	@PostMapping("/clientes")
	@ResponseStatus(code = HttpStatus.CREATED)
	public Cliente adicionar(@RequestBody Cliente c) {
		return cRepository.save(c);
	}
	
	@GetMapping(path = {"/clientes/{id}"})
	public Cliente findById(@PathVariable long id){
	   return cRepository.findById(id)
			   .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
	}
	
    @DeleteMapping("/clientes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar( @PathVariable Long id){
            cRepository
                    .findById(id)
                    .map( cliente -> {
                        cRepository.delete(cliente);
                        return Void.TYPE;
                    })
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

}
