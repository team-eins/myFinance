import {Component} from 'angular2/core';
import {NgForm} from 'angular2/common';
import {Router} from 'angular2/router';
import {Credentials} from "../model/credentials";
import {UserService} from "../services/user.service";

@Component({
    selector: 'login',
    templateUrl: 'views/login.component.html',
    directives: []
})
export class LoginComponent {

    constructor(
        private router:Router,
        private userService:UserService){}

    model:Credentials = new Credentials('', '');

    submitted = false;

    onSubmit() {
        console.log("Submitted " + this.model.username + ' ' + this.model.password);
        this.submitted = true;

        this.userService.login(this.model).catch((error) => console.log(error));
    }

    onRegister(){
        this.router.navigate(['Register']);
    }
}