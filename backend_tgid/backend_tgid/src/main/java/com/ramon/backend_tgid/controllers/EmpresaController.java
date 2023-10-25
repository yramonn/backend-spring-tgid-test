package com.ramon.backend_tgid.controllers;

import com.ramon.backend_tgid.dtos.EmpresaDTO;
import com.ramon.backend_tgid.exceptions.RegraNegocioException;
import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.services.EmpresaService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;
    private final ModelMapper modelMapper;


    public EmpresaController(EmpresaService empresaService, ModelMapper modelMapper) {
        this.empresaService = empresaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping
    public ResponseEntity criarEmpresa(@RequestBody EmpresaDTO dto ) {

        Empresa empresa = Empresa.builder()
                                    .nome(dto.getNome())
                                    .cnpj(dto.getCnpj())
                                    .taxa(dto.getTaxa())
                                    .build();

        try {
            Empresa novaEmpresa = empresaService.criarEmpresa(empresa);
            return new ResponseEntity(novaEmpresa, HttpStatus.CREATED);

        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obterEmpresaPorId(@PathVariable Long id) {
        try {
            Empresa empresa = empresaService.procuraEmpresa(id);

            if (empresa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empresa não encontrada para o ID: " + id);
            }

            return ResponseEntity.ok(EmpresaDTO.fromEntityEmpresa(empresa));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Empresa não encontrada para o ID");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizarEmpresa(@PathVariable Long id, @RequestBody EmpresaDTO dto) {
        try {
            Empresa empresaExistente = empresaService.procuraEmpresa(id);
            if (empresaExistente == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Empresa não encontrada para o ID: " + id);
            }

            modelMapper.map(dto, empresaExistente);


            empresaService.atualizarEmpresa(empresaExistente);
            return ResponseEntity.ok("Empresa atualizada com sucesso.");
        } catch (RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarEmpresa(@PathVariable Long id) {
        try {
            empresaService.deletarEmpresa(id);
            return ResponseEntity.ok("Empresa excluída com sucesso.");
        } catch (RegraNegocioException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Empresa não encontrada para o ID: " + id);
        }
    }


}
