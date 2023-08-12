import { Component} from '@angular/core';
import { AbstractControl, FormBuilder, FormGroup, NgForm, ValidatorFn, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/models/User/user';
import { Validator } from 'src/app/security/validators/validator';
import { UserService } from 'src/app/services/userService/user.service';
import { StrengthMeterComponent } from 'src/app/strength-meter/strength-meter.component';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent {
  form: FormGroup = this.formBuilder.group({
    userName: ['', Validators.compose([this.usernameValidator()])],
    firstName: ['', Validators.compose([this.firstNameAndLastNameValidator()])],
    lastName: ['', Validators.compose([this.firstNameAndLastNameValidator()])],
    emailAddress: ['', Validators.compose([this.emailValidator()])],
    password: ['', Validators.compose([this.passwordValidator()])]
  });
  isPasswordVisible: boolean = false;
  isCreateAccountButtonEnable: boolean = false; 
  isUsernameCorrect: boolean = false;
  isFirstnameCorrect: boolean = false;
  isLastnameCorrect: boolean = false;
  isEmailCorrect: boolean = false;
  isPasswordCorrect: boolean = false;
  isUsernameFocused: boolean = false;
  isFirstnameFocused: boolean = false;
  isLastnameFocused: boolean = false;
  isEmailFocused: boolean = false;
  isPasswordFocused: boolean = false;
  iconTypeForUsername: IconType = IconType.ARROW;
  iconTypeForFirstname: IconType = IconType.ARROW;
  iconTypeForLastname: IconType = IconType.ARROW;
  iconTypeForEmail: IconType = IconType.ARROW;
  iconTypeForPassword: IconType = IconType.ARROW;
  username: string = '';
  firstname: string = '';
  lastname: string = '';
  email: string = '';
  password: string = '';

  constructor(private formBuilder: FormBuilder, private userService: UserService, private router: Router) { }

  onInit() {
  }
  
  togglePasswordVisibilty(){
    this.isPasswordVisible =! this.isPasswordVisible;
  }

  onInputChange(inputId: string, inputValue: string) {
    switch(inputId) {
      case 'userName':
        {
          if(this.isFieldNotEmpty(inputValue)){
            var userExists = false;
            this.userService.findUserByUserName(inputValue).subscribe((user: User) => {
              user !== null ? userExists = true : userExists = false;
              Validator.isUsernameValid(inputValue) && !userExists
              ? (this.iconTypeForUsername = IconType.CHECK, this.isUsernameCorrect = true) : (this.iconTypeForUsername = IconType.X, this.isUsernameCorrect = false);
            });
          }else{
            this.isUsernameCorrect = false;
            this.iconTypeForUsername = IconType.ARROW;
          }
        }
        break;
      case 'firstName':
        {
          if(this.isFieldNotEmpty(inputValue)){
            Validator.isFirstOrLastNameValid(inputValue) ? (this.iconTypeForFirstname = IconType.CHECK, this.isFirstnameCorrect = true) : (this.iconTypeForFirstname = IconType.X, this.isFirstnameCorrect = false);
          }else{
            this.isFirstnameCorrect = false;
            this.iconTypeForFirstname = IconType.ARROW;
          }
        }
        break;
      case 'lastName':
        {
          if(this.isFieldNotEmpty(inputValue)){
            Validator.isFirstOrLastNameValid(inputValue) ? (this.iconTypeForLastname = IconType.CHECK, this.isLastnameCorrect = true) : (this.iconTypeForLastname = IconType.X, this.isLastnameCorrect = false);
          }else{
            this.isLastnameCorrect = false;
            this.iconTypeForLastname = IconType.ARROW;
          }
        }
        break;
      case 'emailAddress':
        {
          if(this.isFieldNotEmpty(inputValue)){
            var emailExists = false;
            this.userService.findUserByEmailAddress(inputValue).subscribe((user: User) => {
              user !== null ? emailExists = true : emailExists = false;
              Validator.isEmailValid(inputValue) && !emailExists
              ? (this.iconTypeForEmail = IconType.CHECK, this.isEmailCorrect = true) : (this.iconTypeForEmail = IconType.X, this.isEmailCorrect = false);
            });
          }else{
            this.isEmailCorrect = false;
            this.iconTypeForEmail = IconType.ARROW;
          }
        }
        break;
      case 'password':
        {
          var passwordStrengthDiv = document.getElementById('password-strength-section');

          if(this.isFieldNotEmpty(inputValue)){
            StrengthMeterComponent.calculateStrength(inputValue);
            Validator.calculateEntropy(inputValue);
            passwordStrengthDiv!.style.display = 'block';
            Validator.isPasswordValid(inputValue) ? (this.iconTypeForPassword = IconType.CHECK, this.isPasswordCorrect = true) : (this.iconTypeForPassword = IconType.X, this.isPasswordCorrect = false);
          }else{
            passwordStrengthDiv!.style.display = 'none';
            this.isPasswordCorrect = false;
            this.iconTypeForPassword = IconType.ARROW;
          }
        }
        break;
    }

    this.isCreateAccountButtonEnable = this.isUsernameCorrect && this.isFirstnameCorrect && this.isLastnameCorrect && this.isEmailCorrect && this.isPasswordCorrect;
  }

  registerUser(registrationForm: NgForm){
    this.userService.addUser(registrationForm.value).subscribe((user: User) => {
      this.router.navigate(["/register-succes"]);
    });
  }

  private isFieldNotEmpty(field: string): boolean {
    return field.trim() !== '';
  }


usernameValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    return Validator.isUsernameValid(control.value) ? null : { invalidUsername: { value: control.value } };
  };
}

firstNameAndLastNameValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    return Validator.isFirstOrLastNameValid(control.value)? null : { invalidFirstOrLastName: { value: control.value } };
  };
}

emailValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    return Validator.isEmailValid(control.value)? null : { invalidEmail: { value: control.value } };
  };
}

passwordValidator(): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    return Validator.isPasswordValid(control.value)? null : { invalidPassword: { value: control.value } };
  };
}

}

enum IconType {
  ARROW, X, CHECK
}