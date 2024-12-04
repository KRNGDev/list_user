package listadousuarios.listadousuario.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import listadousuarios.listadousuario.modelo.UserModel;
import listadousuarios.listadousuario.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class UserService {

    private final String UPLOAD_DIR = "img/";

    @Autowired
    IUserRepository userRepository;

    public ArrayList<UserModel> getUser(){
        return (ArrayList<UserModel>) userRepository.findAll();
    }

    public Map<String, String> saveUser(UserModel user, MultipartFile file) throws IOException {
        Map<String, String> response = new HashMap<>();

        // Comprobar si la carpeta de imágenes existe, si no, crearla
        File directory = new File(UPLOAD_DIR);
        if (!directory.exists()) {
            directory.mkdir();
        }

        // Generar un nombre de archivo único de 7 dígitos
        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

        // Guardar el archivo en la carpeta
        Path path = Paths.get(UPLOAD_DIR + uniqueFileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

        // Asignar la URL de la imagen al usuario (o el nombre del archivo)
        user.setImagenurl(uniqueFileName);

        // Guardar el usuario en la base de datos
        userRepository.save(user);

        response.put("message", "Usuario y archivo guardados con éxito");
        response.put("imagenurl", uniqueFileName);
        return response;
    }


    public Optional<UserModel> getById(Long id){
        return userRepository.findById(id);
    }

    public UserModel updateById(UserModel request, Long id){
        UserModel user = userRepository.findById(id).get();

        user.setNombre(request.getNombre());
        user.setApellido(request.getApellido());
        user.setEmail(request.getEmail());
        user.setTelefono(request.getTelefono());
        user.setImagenurl(request.getImagenurl());
        user.setDireccion(request.getDireccion());
        user.setDisciplina(request.getDisciplina());


        return user;
    }

    public Boolean deleteUser(Long id){
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    private String generateUniqueFileName(String originalFilename) {
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        Random random = new Random();
        String uniqueFileName;
        do {
            uniqueFileName = String.valueOf(random.nextInt(9000000) + 1000000) + extension;
        } while (new File(UPLOAD_DIR + uniqueFileName).exists());  // Comprobar si el archivo ya existe

        return uniqueFileName;
    }

}
