import { Component, Input } from '@angular/core';
import { Validator } from '../../security/validators/validator';

@Component({
  selector: 'app-strength-meter',
  templateUrl: './strength-meter.component.html',
  styleUrls: ['./strength-meter.component.css']
})

export class StrengthMeterComponent {
  @Input() password: string = '';
  private static passwordStrength: number = 0;

  static calculateStrength(password: string): void {
    var bar1 = document.getElementById('bar1');
    var bar2 = document.getElementById('bar2');
    var bar3 = document.getElementById('bar3');
    var entropyScore = Validator.calculateEntropy(password);
    var numberOfUniqueCharacters = Validator.countOccurrencesInPassword(password);

    if(entropyScore <= 24){
      this.passwordStrength += 0;
    }else if(entropyScore >= 25 && entropyScore <= 49){
      this.passwordStrength += 1;
    }else if(entropyScore >= 50 && entropyScore <= 74){
      this.passwordStrength += 2;
    }else{
      this.passwordStrength += 3;
    }

    if(Validator.isStrongPassword(password)){
      this.passwordStrength += 2;
    }else if(Validator.isMediumStrengthPassword(password)){
      this.passwordStrength += 1;
    }else{
      this.passwordStrength += 0;
    }

    if(numberOfUniqueCharacters == 1){
      this.passwordStrength -= 2;
    }else if(numberOfUniqueCharacters > 2 && numberOfUniqueCharacters < 4){
      this.passwordStrength += 0;
    }
    else if(numberOfUniqueCharacters >= 4 && numberOfUniqueCharacters <= 6){
      this.passwordStrength += 1;
    }else{
      this.passwordStrength += 2;
    }

    switch(this.passwordStrength){
      case 0:
      case 1:
      case 2:
      case 3:
      default:
        {
          bar1!.style.backgroundColor ='red';
          bar2!.style.backgroundColor ='rgb(223, 223, 223)';
          bar3!.style.backgroundColor ='rgb(223, 223, 223)';
        }
        break;
      case 4:
      case 5:

        {
          bar1!.style.backgroundColor = 'orange';
          bar2!.style.backgroundColor = 'orange';
          bar3!.style.backgroundColor = 'rgb(223, 223, 223)';
        }
        break;
      case 6:
      case 7:
        {
          bar1!.style.backgroundColor = 'green';
          bar2!.style.backgroundColor = 'green';
          bar3!.style.backgroundColor = 'green';
        }
        break;
    }
  StrengthMeterComponent.passwordStrength = 0;
  }
  
}
