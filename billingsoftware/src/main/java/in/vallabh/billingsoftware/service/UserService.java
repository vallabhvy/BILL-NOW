package in.vallabh.billingsoftware.service;

import in.vallabh.billingsoftware.io.UserRequest;
import in.vallabh.billingsoftware.io.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest request);

    String getUserRole(String email);

    List<UserResponse> readUsers();

    void deleteUser(String id);
}
