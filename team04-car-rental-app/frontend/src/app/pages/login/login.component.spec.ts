import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ReactiveFormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { MatDialog } from '@angular/material/dialog';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';
import { NO_ERRORS_SCHEMA } from '@angular/core';
import { LoginComponent } from './login.component';
import { UserService } from '../../core/auth/services/user.service';
import { CommonModule } from '@angular/common';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { SuccessDialogComponent } from '../../shared/components/success-dialog/success-dialog.component';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let userService: jasmine.SpyObj<UserService>;
  let router: jasmine.SpyObj<Router>;
  let dialog: jasmine.SpyObj<MatDialog>;
  let dialogRefSpy: jasmine.SpyObj<any>;

  beforeEach(async () => {
    const userServiceSpy = jasmine.createSpyObj('UserService', ['getUserByEmail']);
    const routerSpy = jasmine.createSpyObj('Router', ['navigate', 'navigateByUrl']);
    const dialogSpy = jasmine.createSpyObj('MatDialog', ['open']);
    dialogRefSpy = jasmine.createSpyObj('MatDialogRef', ['afterClosed']);
    dialogRefSpy.afterClosed.and.returnValue(of({}));
    dialogSpy.open.and.returnValue(dialogRefSpy);
    
    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule, 
        CommonModule,
        HttpClientTestingModule,
        LoginComponent // Include if standalone
      ],
      // declarations: [LoginComponent], // Include if NOT standalone
      providers: [
        { provide: UserService, useValue: userServiceSpy },
        { provide: Router, useValue: routerSpy },
        { provide: MatDialog, useValue: dialogSpy },
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              queryParamMap: {
                get: jasmine.createSpy('get').and.returnValue(null)
              }
            }
          }
        }
      ],
      schemas: [NO_ERRORS_SCHEMA]
    }).compileComponents();

    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserService) as jasmine.SpyObj<UserService>;
    router = TestBed.inject(Router) as jasmine.SpyObj<Router>;
    dialog = TestBed.inject(MatDialog) as jasmine.SpyObj<MatDialog>;
    
    // Initialize the form before running tests
    fixture.detectChanges();
  });

  // Basic Component Tests
  describe('Basic Component Tests', () => {
    it('should create', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize the login form with empty email and password fields', () => {
      expect(component.loginForm.get('email')?.value).toBe('');
      expect(component.loginForm.get('password')?.value).toBe('');
    });

    it('should navigate to register page when goToRegister is called', () => {
      component.goToRegister();
      expect(router.navigate).toHaveBeenCalledWith(['/register']);
    });

    it('should have form controls marked as required', () => {
      const emailControl = component.loginForm.get('email');
      const passwordControl = component.loginForm.get('password');
      
      emailControl?.setValue('');
      passwordControl?.setValue('');
      
      expect(emailControl?.valid).toBeFalsy();
      expect(passwordControl?.valid).toBeFalsy();
      expect(emailControl?.errors?.['required']).toBeTruthy();
      expect(passwordControl?.errors?.['required']).toBeTruthy();
    });

    it('should validate email format', () => {
      const emailControl = component.loginForm.get('email');
      
      emailControl?.setValue('invalid-email');
      expect(emailControl?.valid).toBeFalsy();
      expect(emailControl?.errors?.['email']).toBeTruthy();
      
      emailControl?.setValue('valid@email.com');
      expect(emailControl?.valid).toBeTruthy();
    });
  });

  // UI Element Tests
  describe('UI Element Tests', () => {
    it('should display the login form with all required elements', () => {
      const compiled = fixture.nativeElement;
      
      expect(compiled.querySelector('h1')?.textContent).toContain('Log in');
      expect(compiled.querySelector('p')?.textContent).toContain('Glad to see you again');
      
      expect(compiled.querySelector('input[type="email"]')).toBeTruthy();
      expect(compiled.querySelector('input[type="password"]')).toBeTruthy();
      expect(compiled.querySelector('button[type="submit"]')).toBeTruthy();
      
      expect(compiled.querySelector('button')?.textContent?.trim()).toBe('Login');
    });

    it('should display error message when loginError is set', () => {
      component.loginError = 'Test error message';
      fixture.detectChanges();
      
      const compiled = fixture.nativeElement;
      const errorElement = compiled.querySelector('div.text-red-600');
      expect(errorElement?.textContent?.trim()).toBe('Test error message');
    });

    it('should not display error message when loginError is empty', () => {
      component.loginError = '';
      fixture.detectChanges();
      
      const compiled = fixture.nativeElement;
      const errorElement = compiled.querySelector('div.text-red-600');
      expect(errorElement).toBeFalsy();
    });
  });

  // Form Submission Tests
  describe('Form Submission Tests', () => {
    it('should call onLogin method when form is submitted', () => {
      spyOn(component, 'onLogin');
      
      const compiled = fixture.nativeElement;
      const form = compiled.querySelector('form');
      form.dispatchEvent(new Event('submit'));
      
      expect(component.onLogin).toHaveBeenCalled();
    });

    it('should not call getUserByEmail if form is invalid', () => {
      component.loginForm.setValue({
        email: 'invalid-email',
        password: ''
      });
      
      component.onLogin();
      expect(userService.getUserByEmail).not.toHaveBeenCalled();
    });
  });

  // Edge Cases
  describe('Edge Cases', () => {
    it('should handle empty form submission', () => {
      component.onLogin();
      expect(userService.getUserByEmail).not.toHaveBeenCalled();
    });

    it('should handle network errors during login', () => {
      userService.getUserByEmail.and.throwError('Network error');
      
      component.loginForm.setValue({
        email: 'test@example.com',
        password: 'Password123'
      });
      
      expect(() => component.onLogin()).toThrow();
    });
  });
});