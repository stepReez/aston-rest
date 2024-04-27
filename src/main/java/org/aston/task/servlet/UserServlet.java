package org.aston.task.servlet;

import org.aston.task.model.UserEntity;
import org.aston.task.service.UserService;
import org.aston.task.servlet.dto.UserIncomingDto;
import org.aston.task.servlet.dto.UserOutcomingDto;
import org.aston.task.servlet.mapper.UserDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/user")
public class UserServlet {

    private UserService userService;

    private UserDtoMapper userDtoMapper;

    @Autowired
    public UserServlet(UserService userService, UserDtoMapper userDtoMapper) {
        this.userService = userService;
        this.userDtoMapper = userDtoMapper;
    }

    @GetMapping
    public List<UserOutcomingDto> getAll() {
        List<UserEntity> userEntities = userService.findAll();
        return userEntities.stream().map(userDtoMapper::outComingUserMap).toList();
    }

    @GetMapping("/{id}")
    public UserOutcomingDto getById(@PathVariable("id") UUID id) {
        UserEntity userEntity = userService.findUserById(id);
        return userDtoMapper.outComingUserMap(userEntity);
    }

    @PostMapping
    public UserOutcomingDto createUser(@RequestBody UserIncomingDto userIncomingDto) {
        UserEntity userEntity = userDtoMapper.incomingUserMap(userIncomingDto);
        UserEntity outComingUser = userService.createUser(userEntity);
        System.out.println(outComingUser.getId());
        return userDtoMapper.outComingUserMap(outComingUser);
    }

    @PutMapping("/{id}")
    public UserOutcomingDto updateUser(@PathVariable UUID id,
                                     @RequestBody UserIncomingDto userIncomingDto) {
        UserEntity userEntity = userService.updateUser(userDtoMapper.incomingUserMap(userIncomingDto), id);
        return userDtoMapper.outComingUserMap(userEntity);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") UUID id) {
            userService.deleteUser(id);
    }
}
