package com.majorAssignment.User;

import com.majorAssignment.User.Service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import com.majorAssignment.User.Dto.MetroCardDto;
import com.majorAssignment.User.Entity.MetroCardEntity;
import com.majorAssignment.User.Entity.UserEntity;
import com.majorAssignment.User.Exceptions.MetroCardAlreadyExistsException;
import com.majorAssignment.User.Exceptions.MetroCardNotFoundException;
import com.majorAssignment.User.Exceptions.UserNotFoundException;
import com.majorAssignment.User.Repository.MetroCardRepository;
import com.majorAssignment.User.Repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest



@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private MetroCardRepository metroCardRepository;

	@InjectMocks
	private UserService userService;

	private UserEntity user;
	private MetroCardEntity metroCard;
	private MetroCardDto metroCardDto;

	@BeforeEach
	void setUp() {
		user = new UserEntity();
		user.setId(1L);
		user.setName("John Doe");
		user.setEmail("john.doe@example.com");

		metroCard = new MetroCardEntity();
		metroCard.setCardNumber("123456789");
		metroCard.setBalance(100.0);
		metroCard.setUser(user);

		metroCardDto = new MetroCardDto();
		metroCardDto.setUserId(1L);
		metroCardDto.setCardNumber("123456789");
		metroCardDto.setInitialBalance(100.0);
	}

	// ✅ Test case: Buy Metro Card (Success)
	@Test
	void buyMetroCard_Success() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(metroCardRepository.save(any(MetroCardEntity.class))).thenReturn(metroCard);

		MetroCardEntity result = userService.buyMetroCard(metroCardDto);

		assertNotNull(result);
		assertEquals("123456789", result.getCardNumber());
		assertEquals(100.0, result.getBalance());

		verify(userRepository, times(1)).findById(1L);
		verify(metroCardRepository, times(1)).save(any(MetroCardEntity.class));
	}

	// ❌ Test case: Buy Metro Card (User Not Found)
	@Test
	void buyMetroCard_UserNotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.buyMetroCard(metroCardDto));

		verify(userRepository, times(1)).findById(1L);
		verify(metroCardRepository, never()).save(any(MetroCardEntity.class));
	}

	// ❌ Test case: Buy Metro Card (Already Exists)
	@Test
	void buyMetroCard_AlreadyExists() {
		user.setMetroCard(metroCard);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		assertThrows(MetroCardAlreadyExistsException.class, () -> userService.buyMetroCard(metroCardDto));

		verify(userRepository, times(1)).findById(1L);
		verify(metroCardRepository, never()).save(any(MetroCardEntity.class));
	}

	// ✅ Test case: Cancel Metro Pass (Success)
	@Test
	void cancelMetroPass_Success() {
		user.setMetroCard(metroCard);
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		String result = userService.cancelMetroPass(1L);

		assertEquals("Metro pass canceled successfully.", result);
		verify(userRepository, times(1)).findById(1L);
		verify(userRepository, times(1)).save(user);
		verify(metroCardRepository, times(1)).delete(metroCard);
	}

	// ❌ Test case: Cancel Metro Pass (User Not Found)
	@Test
	void cancelMetroPass_UserNotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.cancelMetroPass(1L));

		verify(userRepository, times(1)).findById(1L);
		verify(metroCardRepository, never()).delete(any());
	}

	// ❌ Test case: Cancel Metro Pass (No Metro Card)
	@Test
	void cancelMetroPass_NoMetroCard() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		assertThrows(MetroCardNotFoundException.class, () -> userService.cancelMetroPass(1L));

		verify(userRepository, times(1)).findById(1L);
		verify(metroCardRepository, never()).delete(any());
	}

	// ✅ Test case: Get User Profile (Success)
	@Test
	void getUserProfile_Success() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		Optional<UserEntity> result = userService.getUserProfile(1L);

		assertTrue(result.isPresent());
		assertEquals("John Doe", result.get().getName());
		assertEquals("john.doe@example.com", result.get().getEmail());

		verify(userRepository, times(1)).findById(1L);
	}

	// ❌ Test case: Get User Profile (User Not Found)
	@Test
	void getUserProfile_UserNotFound() {
		when(userRepository.findById(1L)).thenReturn(Optional.empty());

		assertThrows(UserNotFoundException.class, () -> userService.getUserProfile(1L));

		verify(userRepository, times(1)).findById(1L);
	}
}

