package de.seuhd.campuscoffee.tests.system;

import de.seuhd.campuscoffee.api.dtos.UserDto;
import de.seuhd.campuscoffee.domain.model.User;
import de.seuhd.campuscoffee.domain.tests.TestFixtures;
import org.junit.jupiter.api.Test;

import java.util.List;

import static de.seuhd.campuscoffee.tests.SystemTestUtils.Requests.userRequests;
import static org.assertj.core.api.Assertions.assertThat;

public class UsersSystemTests extends AbstractSysTest {

    @Test
    void createUser() {
        User userToCreate = TestFixtures.getUserListForInsertion().get(0);
        UserDto createdDto = userRequests.create(List.of(userDtoMapper.fromDomain(userToCreate))).get(0);
        User createdUser = userDtoMapper.toDomain(createdDto);

        assertEqualsIgnoringIdAndTimestamps(createdUser, userToCreate);
    }

    @Test
    void listUsers() {
        // create fixtures directly via service
        List<User> created = TestFixtures.createUsers(userService);

        List<UserDto> fetched = userRequests.retrieveAll();

        List<User> fetchedDomain = fetched.stream().map(userDtoMapper::toDomain).toList();
        assertEqualsIgnoringTimestamps(fetchedDomain, created);
    }

    @Test
    void deleteUser() {
        List<User> created = TestFixtures.createUsers(userService);
        User toDelete = created.get(0);
        userRequests.deleteAndReturnStatusCodes(List.of(toDelete.id()));

        // after deletion, fetching all should not contain the deleted user
        List<UserDto> remaining = userRequests.retrieveAll();
        List<User> remainingDomain = remaining.stream().map(userDtoMapper::toDomain).toList();

        assertThat(remainingDomain).noneMatch(u -> u.loginName().equals(toDelete.loginName()));
    }
}