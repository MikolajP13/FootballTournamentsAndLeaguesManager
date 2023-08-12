import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-registration-success',
  templateUrl: './registration-success.component.html',
  styleUrls: ['./registration-success.component.css']
})
export class RegistrationSuccessComponent {
  timer: number = 3;
  intervalId: any;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.intervalId = setInterval(() => {
      this.updateNumber();
    }, 1000);
  }

  updateNumber() {
    this.timer > 0 ? this.timer-- : (clearInterval(this.intervalId), this.router.navigate(['']));
  }
}
