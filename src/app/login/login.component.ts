import { Component, OnInit } from '@angular/core';
import { AccountService } from '../Controllers/AccountService';
import { Router } from '@angular/router';

function getParameterByName(name, url) {
  if (!url) url = window.location.href;
  name = name.replace(/[\[\]]/g, '\\$&');
  var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
      results = regex.exec(url);
  if (!results) return null;
  if (!results[2]) return '';
  return decodeURIComponent(results[2].replace(/\+/g, ' '));
}


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  username:string;
  password:string;
  constructor(private userService:AccountService, private router: Router) { 
    if(userService.isLogged()){
      console.log(getParameterByName("redirectUrl", window.location.href));
      this.router.navigate([getParameterByName("redirectUrl", window.location.href) || "/dashboard"])
    }
  }
  handleSubmit(){
    if(!this.username || !this.password) return;
    this.userService.GetAccountsByEmailPassword(this.username, this.password).subscribe(data=>{
      if(!data || data[0].accountId == 0) return;
      this.userService.saveLogin( {name:"Sam Smith", ...data[0]});
      this.router.navigate([getParameterByName("redirectUrl", window.location.href) || "/dashboard"])
    })
  }
  ngOnInit() {
  }
}
