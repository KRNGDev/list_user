package listadousuarios.listadousuario.controller;


import listadousuarios.listadousuario.modelo.UserModel;
import listadousuarios.listadousuario.service.UserService;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {

    private final String UPLOAD_DIR = "img/";

    @Autowired
    private UserService userService;


    @GetMapping
    public ArrayList<UserModel> getUsers(){
        return  this.userService.getUser();
    }


    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Map<String, String>> saveUser(
            @RequestPart("user") UserModel userModel,
            @RequestPart("file") MultipartFile file){
        try {
            // Delegar la lógica al servicio
            Map<String, String> response = userService.saveUser(userModel, file);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Error al procesar la solicitud"));
        }
    }

    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserById(@PathVariable("id") Long id){
        return  this.userService.getById(id);
    }

    @PutMapping(path = "/{id}")
    public UserModel updateUserBayId(@RequestBody UserModel request,@PathVariable("id") Long id){
        return this.userService.updateById(request,id);

    }
    @DeleteMapping(path = "/{id}")
    public  Map<String, String> deleteById(@PathVariable("id") Long id){
        boolean ok = this.userService.deleteUser(id);
        Map<String, String> response = new HashMap<>();
        if(ok){
            response.put("message", "User con id " + id + " eliminado");
        }else {
            response.put("error", "Error en la eliminación");
        }
        return response;
    }
}
