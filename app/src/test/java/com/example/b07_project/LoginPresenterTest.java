package com.example.b07_project;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

import com.example.b07_project.model.LoginModel;
import com.example.b07_project.model.LoginModelImpl;
import com.example.b07_project.presenter.LoginPresenter;
import com.example.b07_project.view.LoginView;

public class LoginPresenterTest {

    @Mock
    private LoginModelImpl mockModel;

    @Mock
    private LoginView mockView;

    @InjectMocks
    private LoginPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new LoginPresenter(mockModel, mockView);
    }

    @Test
    public void testAuthenticateUserSuccess() {
        // Arrange
        String validEmail = "valid@example.com";
        String validPassword = "password";
        boolean isAdmin = false;

        // Simulate a successful login
        doAnswer((Answer<Void>) invocation -> {
            // Simulate the onLoginSuccess callback
            ((LoginModel.OnLoginFinishedListener) invocation.getArgument(2)).onLoginSuccess(isAdmin);
            return null;
        }).when(mockModel).authenticateUser(any(String.class), any(String.class), any(LoginModel.OnLoginFinishedListener.class));

        // Act
        presenter.AuthenticateUser(validEmail, validPassword, isAdmin);

        // Assert
        verify(mockView).showLoginSuccess(isAdmin);
    }

    @Test
    public void testAuthenticateUserError() {
        // Arrange
        String invalidEmail = "invalid@example.com";
        String invalidPassword = "wrongpassword";
        boolean isAdmin = true;

        // Simulate a login error
        doAnswer((Answer<Void>) invocation -> {
            // Simulate the onLoginError callback
            ((LoginModel.OnLoginFinishedListener) invocation.getArgument(2)).onLoginError();
            return null;
        }).when(mockModel).authenticateUser(any(String.class), any(String.class), any(LoginModel.OnLoginFinishedListener.class));

        // Act
        presenter.AuthenticateUser(invalidEmail, invalidPassword, isAdmin);

        // Assert
        verify(mockView).showLoginError();
    }
}