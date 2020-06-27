package br.com.ibyte.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.ibyte.api.domain.model.EmployerMockApi;
import br.com.ibyte.api.domain.service.EmployerMockApiService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "Employees")
@RestController
@RequestMapping(path = "/api/employees")
public class MockApiController {
	
	@Autowired
	private EmployerMockApiService employerMockApiService;
	
	@ApiOperation("Listar os dados direto da MockApi")
	@GetMapping
	public List<EmployerMockApi> listar() {
		return employerMockApiService.listar();
	}
	
	@ApiOperation("Importar dados da MockApi")
	@PostMapping
	public void importar() {
		employerMockApiService.importar();
	}
	
}
