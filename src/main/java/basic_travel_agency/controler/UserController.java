package basic_travel_agency.controler;

import basic_travel_agency.domain.User;
import basic_travel_agency.domain.dto.UserDto;
import basic_travel_agency.domain.dto.UserListDto;
import basic_travel_agency.domain.dto.UserRegistrationDto;
import basic_travel_agency.mapper.UserMapper;
import basic_travel_agency.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

@RestController
@RequestMapping
@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @PostMapping("users")
    public void registerUser(@RequestBody UserRegistrationDto dto) {
        userService.addUser(userMapper.mapRegistrationDtoToUser(dto));
    }

    @GetMapping("users/{id}")
    public UserDto getUser(@PathVariable("id") Long id) {
        return userMapper.mapToDto(userService.getUserById(id));
    }

    @GetMapping("users")
    public UserListDto getUsers() {
        return new UserListDto(userMapper.mapToUserDtoList(userService.getAllUsers()));
    }

    @PutMapping("users")
    @Transactional
    public UserDto updateUser(@RequestBody UserDto updatingDto) {
        User user = userService.getUserById(updatingDto.getId());

        if (! user.getName().equals(updatingDto.getName())) {
            user.setName(updatingDto.getName());
        }
        if (! user.getSurname().equals(updatingDto.getSurname())) {
            user.setSurname(updatingDto.getSurname());
        }
        if (! user.getEmail().equals(updatingDto.getEmail())) {
            user.setEmail(updatingDto.getEmail());
        }
        if (! user.getSecurePassword().equals(updatingDto.getSecurePassword())) {
            user.setSecurePassword(updatingDto.getSecurePassword());
        }

        return userMapper.mapToDto(user);
    }

    @DeleteMapping("users/{id}")
    @Transactional
    public void deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
    }
}
