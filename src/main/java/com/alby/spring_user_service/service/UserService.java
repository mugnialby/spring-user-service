package com.alby.spring_user_service.service;

import com.alby.spring_user_service.dto.request.users.*;
import com.alby.spring_user_service.dto.response.UserResponse;
import com.alby.spring_user_service.dto.response.WebResponse;

import java.util.List;

public interface UserService {

    WebResponse<List<UserResponse>> getAll(UserPagingRequest request);

    WebResponse<UserResponse> get(UserGetRequest request);

    UserResponse findByCredential(String username, String password);

    WebResponse<UserResponse> add(UserAddRequest request);

    WebResponse<UserResponse> update(UserUpdateRequest request);

    WebResponse<String> delete(UserDeleteRequest request);
}
