package com.alby.spring_user_service.serviceimpl;

import com.alby.spring_user_service.dto.request.users.*;
import com.alby.spring_user_service.dto.response.UserResponse;
import com.alby.spring_user_service.dto.response.WebResponse;
import com.alby.spring_user_service.entity.UsersEntity;
import com.alby.spring_user_service.repository.UserRepository;
import com.alby.spring_user_service.security.BCrypt;
import com.alby.spring_user_service.service.UserService;
import com.alby.spring_user_service.util.UserUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public WebResponse<List<UserResponse>> getAll(UserPagingRequest request) {
        Page<UsersEntity> users = userRepository.findAll(
            PageRequest.of(
                request.getPage(),
                request.getPageSize(),
                Sort.by("firstName")
                    .ascending()
            ));

        List<UserResponse> userResponses = users
            .stream()
            .map(UserUtil::mapUserToUserResponse)
            .collect(Collectors.toList());

        return WebResponse.<List<UserResponse>> builder()
            .message("OK")
            .data(userResponses)
            .build();
    }

    @Override
    public WebResponse<UserResponse> get(UserGetRequest request) {
        return WebResponse.<UserResponse> builder()
            .message("OK")
            .data(userRepository.findById(request.getUserId())
                .map(UserUtil::mapUserToUserResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND)))
            .build();
    }

    @Override
    public UserResponse findByCredential(
            String username,
            String password
    ) {
        return userRepository.findByUsernameAndPassword(username, password)
                .map(UserUtil::mapUserToUserResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public WebResponse<UserResponse> add(UserAddRequest request) {
        if (userRepository.existsByUsername(request.getUsername()))
            throw new ResponseStatusException(HttpStatus.CONFLICT);

        UsersEntity user = UserUtil.mapAddRequestToUsers(request);
        user.setSalt(BCrypt.gensalt());
        userRepository.save(user);

        return WebResponse.<UserResponse> builder()
                .message("OK")
                .data(UserUtil.mapUserToUserResponse(user))
                .build();
    }

    @Override
    @Transactional
    public WebResponse<UserResponse> update(UserUpdateRequest request) {
        UsersEntity userFromDb = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            
        if (Objects.nonNull(request.getUsername())) {
            if (!request.getUsername().equalsIgnoreCase(userFromDb.getUsername())
                && userRepository.existsByUsername(request.getUsername())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT);
            }

            userFromDb.setUsername(request.getUsername());
        }
    
        if (Objects.nonNull(request.getPassword())) {
            userFromDb.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }
    
        if (Objects.nonNull(request.getFirstName())) {
            userFromDb.setFirstName(request.getFirstName());
        }
    
        if (Objects.nonNull(request.getLastName())) {
            userFromDb.setLastName(request.getLastName());
        }
    
        if (Objects.nonNull(request.getStatus())) {
            userFromDb.setStatus(request.getStatus());
        }

        if (Objects.nonNull(request.getManagerId())) {
            userFromDb.setManagerId(request.getManagerId());
        }
    
        userRepository.save(userFromDb);

        return WebResponse.<UserResponse> builder()
            .message("OK")
            .data(UserUtil.mapUserToUserResponse(userFromDb))
            .build();
    }

    @Override
    @Transactional
    public WebResponse<String> delete(UserDeleteRequest request) {
        UsersEntity user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        user.setStatus("N");
        user.setModifiedBy(request.getModifiedBy());
        userRepository.save(user);

        return WebResponse.<String> builder()
                .message("OK")
                .build();
    }
}