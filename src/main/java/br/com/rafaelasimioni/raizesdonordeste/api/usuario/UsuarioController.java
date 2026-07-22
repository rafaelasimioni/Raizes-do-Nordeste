package br.com.rafaelasimioni.raizesdonordeste.api.usuario;

import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioRequestDTO;
import br.com.rafaelasimioni.raizesdonordeste.api.usuario.dto.UsuarioResponseDTO;
import br.com.rafaelasimioni.raizesdonordeste.application.usuario.UsuarioService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;


    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody UsuarioRequestDTO request){

        UsuarioResponseDTO usuario = usuarioService.cadastrar(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
    }


    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> listarTodos(){
        return ResponseEntity.ok(usuarioService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> buscarId(@PathVariable Long id){
        return ResponseEntity.ok(usuarioService.buscarId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> atualizar(
            @PathVariable Long id,
            @RequestBody UsuarioRequestDTO request
    ){
        return ResponseEntity.ok(usuarioService.atualizar(id,request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}