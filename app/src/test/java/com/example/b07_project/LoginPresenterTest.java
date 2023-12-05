package com.example.b07_project;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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

    private LoginPresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        presenter = new LoginPresenter(mockModel, mockView);
    }

    @Test
    public void testAuthenticateUserSuccess() {
        String validEmail = "valid@example.com";
        String validPassword = "password";

        doAnswer(invocation -> {
            // Simulate the onLoginSuccess callback
            ((LoginModel.OnLoginFinishedListener) invocation.getArgument(2)).onLoginSuccess();
            return null;
        }).when(mockModel).authenticateUser(any(String.class), any(String.class), any(LoginModel.OnLoginFinishedListener.class));

        presenter.AuthenticateUser(validEmail, validPassword);
        verify(mockView).showLoginSuccess();
    }

    @Test
    public void testAuthenticateUserErrorIncorrectCredentials() {
        String invalidEmail = "invalid@example.com";
        String invalidPassword = "invalidPassword";

        doAnswer(invocation -> {
            ((LoginModel.OnLoginFinishedListener) invocation.getArgument(2)).onLoginError();
            return null;
        }).when(mockModel).authenticateUser(any(String.class), any(String.class), any(LoginModel.OnLoginFinishedListener.class));

        presenter.AuthenticateUser(invalidEmail, invalidPassword);
        verify(mockView).showLoginError(invalidEmail, invalidPassword);
    }

    @Test
    public void testAuthenticateUserErrorIncompleteFields() {
        String emptyEmail = "";
        String emptyPassword = "";

        doAnswer(invocation -> {
            ((LoginModel.OnLoginFinishedListener) invocation.getArgument(2)).onLoginError();
            return null;
        }).when(mockModel).authenticateUser(any(String.class), any(String.class), any(LoginModel.OnLoginFinishedListener.class));

        presenter.AuthenticateUser(emptyEmail, emptyPassword);
        verify(mockView).showLoginError(emptyEmail, emptyPassword);
    }
}
