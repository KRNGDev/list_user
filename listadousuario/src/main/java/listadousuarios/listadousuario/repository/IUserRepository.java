package listadousuarios.listadousuario.repository;


import listadousuarios.listadousuario.modelo.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<UserModel,Long> {
}
