package com.ramon.backend_tgid.controller;

import com.ramon.backend_tgid.controllers.EmpresaController;
import com.ramon.backend_tgid.dtos.EmpresaDTO;
import com.ramon.backend_tgid.exceptions.RegraNegocioException;
import com.ramon.backend_tgid.models.Empresa;
import com.ramon.backend_tgid.services.EmpresaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmpresaControllerTest {

    @Mock(lenient = true)
    private EmpresaService empresaService;

    @Mock(lenient = true)
    private ModelMapper modelMapper;

    @InjectMocks
    private EmpresaController empresaController;

    @Test
    public void testCriarEmpresa() throws RegraNegocioException {

        EmpresaDTO dto = new EmpresaDTO();
        dto.setNome("Empresa Teste");
        dto.setCnpj("39.690.959/0001-31");
        dto.setTaxa(0.1);

        Empresa empresa = new Empresa();
        empresa.setNome(dto.getNome());
        empresa.setCnpj(dto.getCnpj());
        empresa.setTaxa(dto.getTaxa());

        when(modelMapper.map(dto, Empresa.class)).thenReturn(empresa);
        when(empresaService.criarEmpresa(empresa)).thenReturn(empresa);

        ResponseEntity responseEntity = empresaController.criarEmpresa(dto);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(empresa, responseEntity.getBody());
    }

    @Test
    public void testObterEmpresaPorId() {
        Long empresaId = 1L;
        Empresa empresa = new Empresa();
        empresa.setId(empresaId);
        empresa.setNome("Empresa Teste");
        when(empresaService.procuraEmpresa(empresaId)).thenReturn(empresa);

        ResponseEntity<?> responseEntity = empresaController.obterEmpresaPorId(empresaId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(EmpresaDTO.fromEntityEmpresa(empresa), responseEntity.getBody());
    }

    @Test
    public void testAtualizarEmpresa() throws RegraNegocioException {

        Long empresaId = 1L;
        EmpresaDTO dto = new EmpresaDTO();
        dto.setNome("Nova Empresa");

        Empresa empresaExistente = new Empresa();
        empresaExistente.setId(empresaId);
        empresaExistente.setNome("Empresa Antiga");

        Empresa empresaAtualizada = new Empresa();
        empresaAtualizada.setId(empresaId);
        empresaAtualizada.setNome(dto.getNome());

        when(empresaService.procuraEmpresa(empresaId)).thenReturn(empresaExistente);
        when(modelMapper.map(dto, Empresa.class)).thenReturn(empresaAtualizada);

        ResponseEntity<?> responseEntity = empresaController.atualizarEmpresa(empresaId, dto);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Empresa atualizada com sucesso.", responseEntity.getBody());

    }

    @Test
    public void testDeletarEmpresa() throws RegraNegocioException {
        Long empresaId = 1L;

        ResponseEntity<?> responseEntity = empresaController.deletarEmpresa(empresaId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Empresa excluída com sucesso.", responseEntity.getBody());

        verify(empresaService, times(1)).deletarEmpresa(empresaId);
    }


}
